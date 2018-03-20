package com.exhibition.service;

import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Exhibits;
import com.exhibition.po.ExhibitsPhoto;
import com.exhibition.po.Exhibitstore;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by final on 17-7-30.
 */
public interface ExhibitsService {


    /**
     * 删除展品
     * @param id
     */
    void delete(Integer id);

    /**
     *
     * @Description 获取所有通过审核的展品
     * @param status	审核状态:展品状态：0-待审核，1-审核通过，-1-禁用
     * @return List<Exhibits>
     */
    List<Exhibits> getExhibits(Integer page, Integer size, String status);

    /**
     *
     * @Description 获取指定id的展商的展品
     * @param page	当前页数
     * @param size	每页显示数量
     * @param exhibitorId	展商id
     * @return List<Exhibits>
     */
    List<Exhibits> getExhibitsByExhibitorId(Integer page, Integer size, Integer exhibitorId);

    /**
     * 根据展品名称进行模糊查询
     * @param exhibitsName
     * @param page
     * @param size
     * @return
     */
    List<Exhibits> queryExhibitsByName(String exhibitsName, Integer page, Integer size);

    /**
     *
     * @Description 通过展品名获得唯一符合的展品
     * @param exhibitsName
     * @return Exhibits
     */
    Exhibits getExhibitsByName(String exhibitsName);

    /**
     * 根据名字和状态获取
     * @param page
     * @param size
     * @param name
     * @param status
     * @return
     */
    List<Exhibits> searchExhibitsByName(int page,int size,String name,String status);

    /**
     * 添加展品，需要包含展品所属展商的ExhibitorId(userId)
     * @param exhibits
     * @param exhibitstoreDB
     * @return  true：执行添加操作，false：执行增加库存操作
     * @throws DataAccessException
     */
    boolean addExhibits(Exhibits exhibits,Exhibitstore exhibitstoreDB) throws DataAccessException;

    /**
     *
     * @Description 购买展品，生成订单<p>未实现</P>
     * @param user
     * @param exhibits
     * @return Order
     */
//    Order buyExhibits(Integer exhibitsId , Integer userId);

    /**
     *
     * @Description 设置展品的审核状态
     * @param id
     * @param status	:1--通过。0--待审核，-1--未通过(数据库对status字段没有设置约束，所以可以设置成其他字符串)
     * @return Exhibits
     */
    void setExhibitsStatus(Integer id, String status);

    /**
     * 获取展品记录总数
     * @return
     */
    int getCount();

    /**
     * 获取展品相应status的记录总数
     * @param status
     * @return
     */
    int getCountByStatus(String status) throws ServiceException;

    /**
     * 根据展商id查询该展商上传的展品总数
     * @param exhibitorId
     * @return
     */
    int getCount(Integer exhibitorId);

    Exhibits getExhibitsById(Integer id);

    /**
     * 更新展品信息
     * @param exhibits
     */
    void update(Exhibits exhibits);

    /**
     * 为展品添加详细介绍的图片
     * @param exhibitsPhoto
     */
    void addExhibtsPhoto(ExhibitsPhoto exhibitsPhoto);

    /**
     * 批量增加图片
     * @param photoList
     */
    void addExhibitsPhoto(List<ExhibitsPhoto> photoList);

    /**
     * 更新展品的封面图片
     * @param exhibitsId
     * @param visitPath
     */
    void updateExhibitsCoverPhoto(Integer exhibitsId, String visitPath);

    /**
     * 根据展品id获取该展品详情信息的图片
     * @param exhibitsId
     * @return
     */
    List<ExhibitsPhoto> getExhibitsPhotos(Integer exhibitsId);

    /**
     * 根据展品id删除图片
     * @param exhibitsId
     */
    void deleteExhibitsPhotosByExhibitsId(Integer exhibitsId);

    /**
     * 根据图片id删除图片
     * @param photoId
     */
    void deleteExhibitsPhotoById(Integer photoId);

    /**
     * 获取图片信息
     * @param photoId
     * @return
     */
    ExhibitsPhoto getExhibitsPhotoById(Integer photoId);

    /**
     * 更新展品的平均好评度
     * @param exhibitsId
     */
    void updateAvgGrade(int exhibitsId);
}
