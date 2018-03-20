package com.exhibition.service;

import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Exhibitstore;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Created by final on 17-7-30.
 */
public interface ExhibitStoreService {

    /**
     * 保存
     * @param exhibitstore
     * @return
     */
    boolean save(Exhibitstore exhibitstore) throws ServiceException,DataAccessException;

    /**
     * 更新仓库信息，需要指定id
     * @param exhibitstore
     * @return
     */
    void update(Exhibitstore exhibitstore) throws ServiceException;

    /**
     *
     * @Description 根据status获取展品仓库中的展品
     * @param status	展品审核状态
     * @param page	起始页数，可传入null（默认为1）
     * @param size	每页显示数量，传入null（默认为20）
     * @return List<Exhibitstore>
     */
    List<Exhibitstore> getALLExhibitstores(String status, Integer page, Integer size);

    /**
     *
     * @Description 根据展商名字获取该展商仓库中的展品
     * @param exhibitorName	展商名
     * @return List<Exhibitstore>
     */
    List<Exhibitstore> getExhibitstoresByExhibitorName(String exhibitorName, Integer page, Integer size) throws ServiceException;

    /**
     *
     * @Description 根据展商姓名(模糊查询)查找展品列表
     * @param exhibitorName
     * @param page
     * @param size
     * @return List<Exhibitstore>
     */
//    public List<Exhibitstore> searchExhibitstoresByExhibitorName(String exhibitorName,Integer page,Integer size);

    /**
     *
     * @Description 根据展品名字(匹配)从仓库中获取展品列表
     * @param exhibitsName	展品名字
     * @return List<Exhibitstore>
     */
    List<Exhibitstore> searchExhibitstoresByName(String exhibitsName, Integer page,
                                                 Integer size);

    /**
     * 根据展品id查询展品
     * @param id    展品id
     * @return
     */
    Exhibitstore getExhibitById(Integer id);

    /**
     *
     * @Description 设置仓库中展品的审核状态
     * <p>status选取在-1,0,1之间，否则不会生效，也不会抛出异常</p>
     * @param id	展品id
     * @param status	审核状态
     */
    void setExhibitstoreStatus(Integer id, String status) ;

    /**
     * 获取展品记录总数
     * @return
     */
    int getCount();

    /**
     * 获取相应status的记录总数
     * @param status
     * @return
     */
    int getCountByStatus(String status) throws ServiceException;

    /**
     * 根据展商id获取改展商所上架的展品数量
     * @param userId
     * @return
     */
    int getCountByExhibitorId(int userId);

    /**
     * 删除指定id的展品
     * @param exhibitorId
     */
    void deleteExhibitsById(Integer exhibitorId);

    /**
     * 根据展商的userid删除该展商的所有展品
     * @param userId
     */
    void deleteExhibitsByUserId(Integer userId);
}
