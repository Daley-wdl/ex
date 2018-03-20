package com.exhibition.dao;

import com.exhibition.po.Exhibits;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by final on 17-7-28.
 */
public interface ExhibitsDao {


    /**
     * 新增展品到数据库
     * @param exhibits
     * @return
     */
    void insertExhibits(Exhibits exhibits);

    /**
     * 根据展品id删除
     * @param id
     */
    void deleteExhibits(Integer id);

    /**
     * 更新展品信息，需要指定展品id
     * @param exhibits
     */
    void updateExhibits(Exhibits exhibits);

    /**
     *
     * @Description 根据展品名称对展品进行模糊搜索
     * @param exhibitsName	:展品名称
     * @return List<Exhibits>	:符合展品名称的商品列表
     */
    public List<Exhibits> queryExhibitsByName(@Param("exhibitsName") String exhibitsName, @Param("start") Integer start,
                                              @Param("size") Integer size) ;

    /**
     * 根据名字和状态查询展品
     * @param start
     * @param size
     * @param name
     * @param status
     * @return
     */
    public List<Exhibits> selectExhibitsByName(@Param("start")Integer start,@Param("size")Integer size,
                                               @Param("name")String name,@Param("status")String status);

    /**
     *
     * @Description 根据展品名称获取展品（唯一）
     * @param exhibitsName	：展品名称
     * @return Exhibits
     */
    public Exhibits getExhibitsByName(String exhibitsName) ;

    /**
     *
     * @Description 根据展品id获取展品（唯一）
     * @param id
     * @return Exhibits
     */
    public Exhibits getExhibitsById(Integer id) ;

    /**
     *
     * @Description 获取展品列表
     * @param status	审核状态:展品状态：0-待审核，1-审核通过，-1-禁用
     * @param start		在数据库中的索引
     * @return List<Exhibits>
     */
    public List<Exhibits> getExhibits(@Param("status") String status,
                                      @Param("start") Integer start, @Param("size") Integer size);

    /**
     *
     * @Description 获取指定id的展商的展品
     * @param start		在数据库中的索引
     * @param size	每页显示数量
     * @param exhibitorId	展商id
     * @return List<Exhibits>
     */
    public List<Exhibits> getExhibitsByExhibitorId(@Param("exhibitorId") Integer exhibitorId,
                                                   @Param("start") Integer start, @Param("size") Integer size);

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
    int getCountByStatus(String status);

    /**
     * 根据展商id查询该展商上传的展品总数
     * @param exhibitorId
     * @return
     */
    int getCountByExhibitorId(Integer exhibitorId);

    /**
     * 减少商品数量
     * @param exhibitsId
     * @param buyNumber
     */
    void reduceExhibitsNumber(@Param("exhibitsId") Integer exhibitsId, @Param("buyNumber") Integer buyNumber);

    /**
     * 增加商品
     * @param exhibitsId
     * @param buyNumber
     */
    void addExhibitsNumber(@Param("exhibitsId") Integer exhibitsId, @Param("buyNumber") Integer buyNumber);

    /**
     * 更新展品的平均好评度
     * @param exhibitsId
     * @param grade
     */
    void updateAvgGrade(@Param("exhibitsId") int exhibitsId,@Param("grade") int grade);
}
