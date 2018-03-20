package com.exhibition.service.impl;

import com.exhibition.constants.OrderConstants;
import com.exhibition.dao.*;
import com.exhibition.dto.ListDataDto;
import com.exhibition.enums.ExceptionEnums;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.*;
import com.exhibition.service.OrderService;
import com.exhibition.utils.FieldTransfer;
import com.exhibition.utils.ShipmentAmountCounter;
import com.exhibition.vo.OrderForExhibitorVo;
import com.exhibition.vo.OrderInfoForUser;
import com.exhibition.vo.OrderList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;


@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OrderServiceImpl implements OrderService{

    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    private ExhibitsDao exhibitsDao;

    @Autowired
    private OrderCanceledItemDao orderCanceledItemDao;

    @Autowired
    private OrderAddressDao orderAddressDao;

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ShipmentAmountCounter shipmentAmountCounter;//计算快递费

    /*
    1、用户查看展品时，添加展品到购物车（需要设置上限）
    2、在购物车中可以继续添加或删除
    3、点击生成订单后，根据购物车中所选择的展品生成订单（未提交到数据库，放置在session中）
        a、
        b、计算所有展品的支付金额
        c、计算快递费（可以使用策略模式，由管理员选择不同的支付策略）
        d、相加后得到总的支付金额
        e、计算总的商品数
        f、添加订单到数据库
        g、订单过期功能：
            A、将返回的order实例设置到session中->用户长时间未操作或退出后，自动取消订单
            B、数据库中设置计时器
    4、选择收货方式，快递配送（快递费）或自取
        a、自取：不需要填写收货地址信息，只需要填写用户名和手机号然后到展商处自行收货
        b、快递配送：需要填写收货地址信息、用户名、手机号
    5、选择支付方式：在线OR线下支付
    6、确定OR取消订单
    7、确定订单后，根据支付方式跳转页面
        a、在线支付：支付页面，并完成支付后，更新Order付款状态
        b、线下支付：用户到展商处完成支付，由展商确定是否已经付款
    8、将选择的购物车中的商品信息添加到order_item表中，并且删除购物车中的原有商品，并添加order记录
     */


    /**
     * 将用户购物车中的展品添加至订单中，并清空购物车
     * @param userId
     * @param shoppingCartIdList
     * @param orderAddressId
     * @param payType   支付方式
     * @param shipmentType     配送方式
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Order addOrder(Integer userId, Integer[] shoppingCartIdList, Integer orderAddressId
            ,String payType,String shipmentType) {
        /*
         * 1、创建Order
         * 2、填充属性并保存订单
         * 3、将购物车中的item添加到order_item表中
         * 4、删除购物车所选项
         */

        /*
        验证收货地址是否存在
         */
        OrderAddress orderAddress = orderAddressDao.getOrderAddress(orderAddressId);
        if (orderAddress == null || StringUtils.isEmpty(orderAddress.getUserAdress())) {
            logger.debug("生成订单失败--用户地址信息不全:" + orderAddress);
            throw new ServiceException(ExceptionEnums.LackInfo);
        }

        //查询用户勾选的购物车列表
        List<ShoppingCart> shoppingCartList = shoppingCartDao.getShoppingCartByIds(shoppingCartIdList);
        //如果购物车列表为空，则抛出异常
        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new ServiceException(ExceptionEnums.EmptyShoppingCart);
        }

        /*
        获取订单属性
         */
        //计算购物数量
        int buyNum = 0;
        for (ShoppingCart shoppingCart : shoppingCartList) {
            buyNum += shoppingCart.getBuyNumber();
        }
        //如果不是自取，则需要计算快递费
        int shipmentAmount = 0;
        if (!OrderConstants.SHIPMENT_NONE.equals(shipmentType)) {
            shipmentAmount = shipmentAmountCounter.getShipmentAmount(shoppingCartList);
            if (shipmentAmount == 0) {
                //如果快递费为0，则标记为免运费
                shipmentType = OrderConstants.SHIPMENT_FREE;
            } else if (shipmentAmount < 0) {
                //计算快递费出错
                StringBuilder stringBuilder = new StringBuilder("计算快递费出错，购物车清单:\n");
                for (ShoppingCart shoppingCart : shoppingCartList) {
                    stringBuilder.append("\t").append(shoppingCart.toString());
                }
                logger.error(stringBuilder.toString());
                throw new ServiceException(ExceptionEnums.Error);
            } else {
                shipmentType = OrderConstants.SHIPMENT_CHARGE;
            }
        }
        //计算订单金额
        int orderAmount = 0;
        for (ShoppingCart shoppingCart : shoppingCartList) {
            orderAmount += shoppingCart.getProductAmount();
        }

        //计算支付金额=快递费+订单金额
//        int payAmount = shipmentAmount + orderAmount;

        /*
        填充属性后保存订单，获取自增主键
         */
        Order order = new Order();
        order.setOrderAddressId(orderAddressId);
        order.setUserId(userId);
        order.setPayType(payType);
        order.setShipmentType(shipmentType);
        order.setShipmentAmount(shipmentAmount);
        order.setOrderStatus(OrderConstants.ORDER_UNCOMPLETED);//设置为未完成状态
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setUpdateTime(order.getCreateTime());       //更新时间默认为创建时间
        order.setOrderAmount(orderAmount);
        order.setPayAmount(0);  //设置已支付金额=0
        order.setBuyNumber(buyNum);
        order.setPayment(OrderConstants.PAYMENT_NO);//设置为未评论状态
        order.setIsDelivered(OrderConstants.IS_DELIVERED_NO);//设置为未发货状态
        orderDao.insert(order);

        /*
        保存order_item
         */
        ArrayList<OrderItem> orderItems = new ArrayList<>(shoppingCartList.size());
        for (ShoppingCart shoppingCart : shoppingCartList) {
            OrderItem orderItem = new OrderItem();
            //复制buyNumber/productAmount/exhibitsId/exhibitsName/picImg
            FieldTransfer.fieldTrans(shoppingCart, orderItem);
            orderItem.setPrice(orderItem.getProductAmount() / orderItem.getBuyNumber());//计算购买数量
            orderItem.setOrderId(order.getOrderId());
            orderItem.setCommentStatus(OrderConstants.COMMENT_NONE);//设置未评论

            orderItems.add(orderItem);
        }
        orderItemDao.insertByBatch(orderItems);//保存

        /*
        删除所选的购物车item
         */
        shoppingCartDao.deletByBatch(Arrays.asList(shoppingCartIdList));

        if (logger.isInfoEnabled()) {
            logger.info("生成订单：" + order);
        }

        return order;
    }

    /**
     * 取消订单
     *
     * @param orderId
     */
    @Override
    public void cancelOrder(Integer orderId) {
        /*
        1、将将要删除order_item保存到order_canceled_item表中
        2、删除订单包含的order_item项
        3、还原库存
        4、将订单状态修改为已取消
         */

        if (orderId == null) {
            throw new ServiceException(ExceptionEnums.WrongId);
        }
        Order order = orderDao.getOrder(orderId);
        if (order == null) {
            throw new ServiceException(ExceptionEnums.WrongId);
        }
        List<OrderItem> orderItemList = orderItemDao.getOrderItemList(orderId);
        if (orderItemList == null || orderItemList.size() == 0) {
            return;
        }
        List<OrderCanceledItem> orderCanceledItemList = new ArrayList<>(orderItemList.size());//保存需要删除的信息
        List<Integer> orderItemIdList = new ArrayList<>(orderItemList.size());//保存orderItem的id
        for (OrderItem orderItem : orderItemList) {
            OrderCanceledItem orderCanceledItem = new OrderCanceledItem();
            FieldTransfer.fieldTrans(orderItem, orderCanceledItem);
            orderCanceledItem.setOrderItemId(orderItem.getOrderItemId());

            Integer exhibitsId = orderItem.getExhibitsId();
            Exhibits exhibits = exhibitsDao.getExhibitsById(exhibitsId);

            orderCanceledItem.setExhibitorId(exhibits.getExhibitorId());
//            orderCanceledItem.setOrderId(orderId);
            orderCanceledItem.setUserId(order.getUserId());
            orderCanceledItem.setCancelTime(new Timestamp(System.currentTimeMillis()));
            orderCanceledItemList.add(orderCanceledItem);
            orderItemIdList.add(orderItem.getOrderItemId());
        }
        /*
        保存order_canceled_item
         */
        orderCanceledItemDao.insertByBatch(orderCanceledItemList);
        /*
        删除orderItem
         */
        orderItemDao.deleteByBatch(orderItemIdList);
        /*
        还原库存
         */
        for (OrderCanceledItem canceledItem : orderCanceledItemList) {
            Integer exhibitsId = canceledItem.getExhibitsId();
            Integer buyNumber = canceledItem.getBuyNumber();
            exhibitsDao.addExhibitsNumber(exhibitsId,buyNumber);
        }
        /*
        将订单状态修改为已取消
         */
        orderDao.updateOrderStatus(orderId,OrderConstants.ORDER_CANCELED,OrderConstants.PAYMENT_NO);
        if (logger.isInfoEnabled()) {
            logger.info("取消订单:" + order);
        }
    }


    /**
     * 获取展商的订单列表
     *
     * @param exhibitorId
     * @param canceled    订单是否取消
     * @param page
     * @param size
     * @return
     */
    @Override
    public ListDataDto<OrderForExhibitorVo> getOrdersForExhibitor(int exhibitorId, boolean canceled, int page, int size) {
        List<OrderForExhibitorVo> orderList = null;
        int count = 0;
        size = (size <= 0) ? 20 : size;
        int start = (Math.max(1, page) - 1) * size;
        if (canceled) {
            //取消的订单列表
            orderList = orderDao.getCanceledOrdersForExhibitor(exhibitorId, start, size);
            count = orderDao.getCanceledCountForExhibitor(exhibitorId);
        } else {
            orderList = orderDao.getOrdersForExhibitor(exhibitorId, start, size);
            /*
            为每个展商单独查询出订单中属于自己部分的商品是否已经支付、已经发货
             */
            for (OrderForExhibitorVo vo : orderList) {
                boolean ifSetShipmentId = true;
                Integer orderId = vo.getOrderId();
                List<OrderItem> orderItems = orderItemDao.getOrderItemGroupByExhibitorId(orderId, exhibitorId);

                for (OrderItem orderItem : orderItems) {
                    if (orderItem.getShipmentId() == null) {
                        ifSetShipmentId = false;
                        break;
                    }
                }
                if (ifSetShipmentId) {
                    vo.setIsDelivered(OrderConstants.IS_DELIVERED_YES);
                } else {
                    vo.setIsDelivered(OrderConstants.IS_DELIVERED_NO);
                }

                boolean isAllPayOff = true;
                for (OrderItem orderItem : orderItems) {
                    String isPayOff = orderItem.getIsPayOff();
                    if (!OrderConstants.IS_DELIVERED_YES.equals(isPayOff)) {
                        isAllPayOff = false;
                        break;
                    }
                }
                if (isAllPayOff) {
                    vo.setPayment(OrderConstants.PAYMENT_YES);
                } else {
                    vo.setPayment(OrderConstants.PAYMENT_NO);
                }
            }
            count = orderDao.getCountForExhibitor(exhibitorId);
        }
        /*
        设置订单的用户名、设置总金额
         */
        orderList.forEach(orderForExhibitorVo -> {
            String userName = orderAddressDao.getUserName(orderForExhibitorVo.getOrderAddressId());
            orderForExhibitorVo.setUsername(userName);

//            int totalPriceForExhibitor = orderItemDao.getTotalPriceForExhibitor(orderForExhibitorVo.getOrderId(), exhibitorId);
//            orderForExhibitorVo.setTotalPrice(totalPriceForExhibitor);
        });

        return new ListDataDto<>(orderList,count);
    }

    /**
     * 将订单中属于指定展商的物品设置为已付款
     *
     * @param exhibitorId
     * @param orderId
     */
    @Override
    public void updateIsPayOffByExhibitor(int exhibitorId, int orderId) {
        /*
        更新order表支付状态
         */
        List<OrderItem> orderItemList = orderItemDao.getOrderItemGroupByExhibitorId(orderId, exhibitorId);
        int totalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            if (OrderConstants.ORDERITEM_IS_PAYOFF.equals(orderItem.getIsPayOff())) {
                continue;
            } else if (OrderConstants.ORDERITEM_NOT_PAYOFF.equals(orderItem.getIsPayOff())) {
                totalPrice += orderItem.getProductAmount();
            } else {
                logger.error("订单详情项-支付状态is_pay_off出现异常：存在规定以外的数据");
                throw new ServiceException(ExceptionEnums.Error);
            }
        }
        if (totalPrice == 0) {
            //全部订单详细项都已近付款
            if (logger.isInfoEnabled()) {
                logger.info("全部订单详细项都已经付款");
            }
            return;
        }
        Order order = orderDao.getOrder(orderId);
        Integer payAmount = order.getPayAmount();
        payAmount += totalPrice;//当前订单已付款
        /*
        更新订单支付金额
         */
        orderDao.updatePayAmount(orderId,payAmount);
        /*
        更新order_item表支付状态
         */
        orderItemDao.updateIsPayOffByExhibitor(exhibitorId,orderId);
        /*
        如果用户已全部付款，则更新订单状态为已完成
         */
        Integer orderAmount = order.getOrderAmount();//订单商品总金额
        if (orderAmount.equals(payAmount) ) {
            //已经全部付款,订单设置为已完成、已付款
            orderDao.updateOrderStatus(orderId, OrderConstants.ORDER_COMPLETED,OrderConstants.PAYMENT_YES);
        }

    }

    /**
     * 将订单的详细项都设置为已付款
     *
     * @param orderId
     */
    @Override
    public void updateIsPayOffByOrder(int orderId) {
        /*
        1、将所有order_item设置为已付款
        2、订单设置为已完成
         */
        orderItemDao.updateIsPayOffByOrder(orderId);
        orderDao.updateOrderStatus(orderId, OrderConstants.ORDER_COMPLETED,OrderConstants.PAYMENT_YES);
    }

    /**
     * 获取订单中展商的商品列表
     *
     * @param orderId
     * @param exhibitorId
     * @return
     */
    @Override
    public List<OrderItem> getOrderItemsForExhibitor(int orderId, int exhibitorId) {
        List<OrderItem> orderItemList = orderItemDao.getOrderItemGroupByExhibitorId(orderId, exhibitorId);
        return orderItemList;
    }

    /**
     * 获取已经取消的订单中展商的商品列表
     *
     * @param orderId
     * @param exhibitorId
     * @return
     */
    @Override
    public List<OrderCanceledItem> getCanceledOrderItemsForExhibitor(int orderId, int exhibitorId) {
        List<OrderCanceledItem> canceledItemList = orderCanceledItemDao.getOrderCancaledItemForExhibitor(orderId, exhibitorId);
        return canceledItemList;
    }

    /**
     * 获取用户的订单信息（普通订单和已取消订单）
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<OrderInfoForUser> getOrderInfoForUser(int userId, int page, int size) {
        if (size > OrderConstants.MAX_SELECT_ORDER_SIZE) {
            if (logger.isInfoEnabled()) {
                logger.info("查询订单单页数量过大");
            }
            throw new ServiceException(ExceptionEnums.SurpassMaxCount);
        }
        /*
        获取前size个订单order信息
         */
        size = (size <= 0) ? 5 : size;      //默认查询数量要小于OrderConstants.MAX_SELECT_ORDER_SIZE
        int start = (Math.max(page, 1) - 1) * size;
        //获取订单信息
        List<Order> orderList = orderDao.getOrderListByUserId(userId, start, size);
        //获取订单id列表
        List<Integer> ids = new ArrayList<>(orderList.size());
        orderList.forEach(order -> {
            ids.add(order.getOrderId());
        });

        //如果id列表为null，则返回空列表
        if (ids == null || ids.size() == 0) {
            return Collections.emptyList();
        }
        /*
        根据orderId信息到order_item和order_canceled_item中查询
         */
        List<OrderItem> orderItemList = orderItemDao.getOrderItemListByOrderIds(ids);
        List<OrderCanceledItem> orderCanceledItems = orderCanceledItemDao.getOrderItemListByOrderIds(ids);
        /*
        包装查询结果到VO列表中
         */
        ArrayList<OrderInfoForUser> results = new ArrayList<>(orderList.size());
        for (Order order : orderList) {
            OrderInfoForUser orderInfoForUser = new OrderInfoForUser();
            orderInfoForUser.setOrder(order);

            /*
            将查询出来的结果根据orderId分组
             */
            LinkedList<OrderItem> orderItems = new LinkedList<>();
            LinkedList<OrderCanceledItem> canceledItems = new LinkedList<>();
            Integer orderId = order.getOrderId();
            for (OrderItem orderItem : orderItemList) {
                if (orderItem.getOrderId().equals(orderId)) {
                    orderItems.add(orderItem);
                }
            }
            for (OrderCanceledItem canceledItem : canceledItems) {
                if (canceledItem.getOrderId().equals(orderId)) {
                    canceledItems.add(canceledItem);
                }
            }
            orderInfoForUser.setOrderItems(orderItems);
            orderInfoForUser.setCanceledItems(canceledItems);
            results.add(orderInfoForUser);
        }

        /*
        返回结果
         */
        return results;
    }


}
