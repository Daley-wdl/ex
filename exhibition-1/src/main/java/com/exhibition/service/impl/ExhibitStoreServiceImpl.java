package com.exhibition.service.impl;

import com.exhibition.dao.ExhibitstoreDao;
import com.exhibition.dao.UserDao;
import com.exhibition.enums.ExceptionEnums;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Exhibits;
import com.exhibition.po.Exhibitstore;
import com.exhibition.po.User;
import com.exhibition.service.ExhibitStoreService;
import com.exhibition.utils.CheckFieldUtils;
import com.exhibition.utils.NameValidUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by final on 17-7-30.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ExhibitStoreServiceImpl implements ExhibitStoreService {

    private Logger logger = Logger.getLogger(ExhibitStoreService.class);

    @Autowired
    private ExhibitstoreDao exhibitstoreDao;

    @Autowired
    private UserDao userDao;

    private final int default_size = 20;//默认的请求条数size大小

    /**
     * 保存
     *
     * @param exhibitstore
     * @return
     */
    @Override
    public boolean save(Exhibitstore exhibitstore) throws ServiceException {
        //验证展品名和展品种类
        if (!NameValidUtils.validStr(exhibitstore.getExhibitsName()) || !NameValidUtils.validStr(exhibitstore.getCategory())) {
            if (logger.isInfoEnabled()) {
                logger.info("输入不合法");
            }
            throw new ServiceException(ExceptionEnums.StringNotValid);
        }

        Integer number = exhibitstore.getNumber();
        Integer price = exhibitstore.getPrice();
        Integer shipmentAmount = exhibitstore.getShipmentAmount();//快递费
        if (number == null || number < 0 || price == null || price < 0 || shipmentAmount==null||shipmentAmount<0) {
            if (logger.isInfoEnabled()) {
                logger.info("-------展商保存展品失败：价格或数量错误\n\t展品名：" + exhibitstore.getExhibitsName());
            }
            throw new ServiceException(ExceptionEnums.LackInfo);
        }

        Integer exhibitorId = exhibitstore.getExhibitorId();
        if (exhibitstore.getExhibitsName()==null) {
            //只有id属性可以为空
            if (logger.isDebugEnabled()) {
                logger.debug("-------展商保存展品失败：展品信息不全\n------展商id:" + exhibitorId );
            }
            return false;
        }

        String exhibitsName = exhibitstore.getExhibitsName();
        Exhibitstore exhibitstoreInDB = exhibitstoreDao.getExhibitstoresByName(exhibitsName);
        if (exhibitstoreInDB != null) {
            //该展品名已经存在
            if (logger.isDebugEnabled()) {
                logger.debug("-------展商保存展品失败：展品名已存在\n------展商id:" + exhibitorId + "\t展品名：" + exhibitsName);
            }
            throw new ServiceException(ExceptionEnums.ExhibitsNameExists);
        }

        exhibitstore.setCreatTime(new Timestamp(System.currentTimeMillis()));
        boolean result = (exhibitstoreDao.addExhibits(exhibitstore) == 1);
        return result;
    }

    /**
     * 更新仓库信息，需要指定id
     *
     * @param exhibitstore
     * @return
     */
    @Override
    public void update(Exhibitstore exhibitstore) throws ServiceException {
        if (!CheckFieldUtils.leastField(exhibitstore, 3)) {
            //待更新的对象至少id、创建时间（该类构造函数有默认值）和一项待更新的属性不为空
            throw new ServiceException(ExceptionEnums.LackInfo);
        }
        Integer id = exhibitstore.getId();
        if (id == null) {
            //再次确认id不为空
            throw new ServiceException(ExceptionEnums.LackId);
        }
        exhibitstoreDao.updateExhibits(exhibitstore);
    }

    /**
     * @param status 展品审核状态
     * @param page   起始页数，可传入null（默认为1）
     * @param size   每页显示数量，传入null（默认为20）
     * @return List<Exhibitstore>
     * @Description 根据status获取展品仓库中的展品
     */
    @Override
    public List<Exhibitstore> getALLExhibitstores(String status, Integer page, Integer size) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = default_size;
        }
        Integer start = (page.intValue() - 1) * size.intValue();

        return exhibitstoreDao.getAllExhibitstore(status,start,size);
    }

    /**
     * @param exhibitorName 展商名
     * @param page
     * @param size          @return List<Exhibitstore>
     * @Description 根据展商名字获取该展商仓库中的展品
     */
    @Override
    public List<Exhibitstore> getExhibitstoresByExhibitorName(String exhibitorName, Integer page, Integer size) throws ServiceException {
        if (StringUtils.isEmpty(exhibitorName)) {
            //搜索的展商姓名不能为空
            throw new ServiceException(ExceptionEnums.LackInfo);
        }

        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = default_size;
        }
        Integer start = (page.intValue() - 1) * size.intValue();

        //userId和exhibitorId相同
        Integer userId = userDao.getId(exhibitorName);

        return exhibitstoreDao.getExhibitstoreByExhibitorId(userId, start, size);
    }


    /**
     * @param exhibitorName
     * @param page
     * @param size
     * @return List<Exhibitstore>
     * @Description 根据展商姓名(模糊查询)查找展品列表
     */
//    @Override
//    public List<Exhibitstore> searchExhibitstoresByExhibitorName(String exhibitorName, Integer page, Integer size) {
//        return null;
//    }

    /**
     * @param exhibitsName 展品名字
     * @param page
     * @param size         @return List<Exhibitstore>
     * @Description 根据展品名字(匹配)从仓库中获取展品列表
     */
    @Override
    public List<Exhibitstore> searchExhibitstoresByName(String exhibitsName, Integer page, Integer size) {
        if (StringUtils.isEmpty(exhibitsName)) {
            //搜索的展商姓名不能为空
            throw new ServiceException(ExceptionEnums.LackInfo);
        }

        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = default_size;
        }
        Integer start = (page.intValue() - 1) * size.intValue();

        return exhibitstoreDao.searchExhibitstoresByName(exhibitsName, start, size);
    }

    /**
     * 根据展品id查询展品
     *
     * @param id 展品id
     * @return
     */
    @Override
    public Exhibitstore getExhibitById(Integer id) {
        return exhibitstoreDao.getExhibitById(id);
    }

    /**
     * @param id     展品id
     * @param status 审核状态
     * @return Exhibitstore    设置失败时返回null
     * @Description 设置仓库中展品的审核状态
     * <p>status选取在-1,0,1之间，否则不会生效，也不会抛出异常</p>
     */
    @Override
    public void setExhibitstoreStatus(Integer id, String status) {
        int statusVal = Integer.parseInt(status);
        if (statusVal >= -1 && statusVal <= 1 && id != null) {
            //status在-1~1的范围内
            Exhibitstore exhibitstore = new Exhibitstore();
            exhibitstore.setStatus(status);
            exhibitstore.setId(id);
            exhibitstoreDao.updateExhibits(exhibitstore);
        }
    }

    /**
     * 获取展品记录总数
     *
     * @return
     */
    @Override
    public int getCount() {
        return exhibitstoreDao.getCount();
    }

    /**
     * 获取相应status的记录总数
     *
     * @param status
     * @return
     */
    @Override
    public int getCountByStatus(String status) throws ServiceException {
        int intStatus = 0;
        try {
            intStatus = Integer.parseInt(status);
        } catch (Exception e) {
            //status无法转为int，证明不在-1，0，1之间
            throw new ServiceException(ExceptionEnums.WrongStatus);
        }

        if (intStatus < -1 || intStatus > 1 ) {
            //不在-1，0，1之间,或者status的长度超过1位
            throw new ServiceException(ExceptionEnums.WrongStatus);
        }
        return exhibitstoreDao.getCountByStatus(Integer.toString(intStatus));
    }

    /**
     * 根据展商id获取改展商所上架的展品数量
     *
     * @param userId
     * @return
     */
    @Override
    public int getCountByExhibitorId(int userId) {
        return exhibitstoreDao.getCountByExhibitorId(userId);
    }

    /**
     * 删除指定id的展品
     *
     * @param exhibitorId
     */
    @Override
    public void deleteExhibitsById(Integer exhibitorId) {
        exhibitstoreDao.deleteExhibits(exhibitorId);
    }

    /**
     * 根据展商的userid删除该展商的所有展品
     *
     * @param userId
     */
    @Override
    public void deleteExhibitsByUserId(Integer userId) {
        exhibitstoreDao.deleteExhibitsByUserId(userId);
    }
}
