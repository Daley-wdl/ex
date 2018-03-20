package com.exhibition.service;

import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.CommonCategory;
import com.exhibition.po.Exhibitor;
import com.exhibition.po.User;

import java.util.List;

/**
 * Created by final on 17-7-25.
 */
public interface ExhibitorService {

    /**
     * 展商注册
     * @param user：包含用户users表的信息
     * @param exhibitor：包含展商所需的额外信息
     * @return 是否注册成功
     */
    boolean register(User user, Exhibitor exhibitor) throws ServiceException;

    /**
     * 根据userId查询展商信息
     * @param userId
     * @return
     */
    Exhibitor selectByUserId(Integer userId) throws ServiceException;

    /**
     * g根据用户名查找展商
     * @param username
     * @return
     */
    Exhibitor selectByUsername(String username);

    /**
     * 获取所有展商的信息
     * @param page
     * @param size
     * @return
     */
    List<Exhibitor> selectAllExhibitor(int page, int size,String status) throws ServiceException;

    /**
     * 修改展商的信息
     * <p><strong>需要在传入的对象中指定展商的id以及需要被修改的id</strong></p>
     * @param exhibitor
     */
    void updateExhibitor(Exhibitor exhibitor);

    /**
     * 根据名字和状态查询
     * @param page
     * @param size
     * @param name
     * @param status
     * @return
     */
    List<Exhibitor> searchExhibitorByName(int page,int size,String name,String status);

    /**
     * 获取相应status展商的总人数
     *
     * @param status
     * @return
     */
    int selectTotal(String status) throws ServiceException;

    /**
     * 根据id删除一个展商
     * @param userId
     * @return
     */
    void deleteExhibitor(int userId);

    /**
     * 获取展商的常用展品标签
     * @param userId
     */
    CommonCategory selectCommonCategory(Integer userId);

    /**
     * 更新展商的常用标签
     * @param tag   新标签
     * @param userId    展商id
     */
    void updateCommonCategory(String tag,Integer userId);
}
