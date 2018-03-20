package com.exhibition.dao;

import com.exhibition.po.CommonCategory;
import com.exhibition.po.Exhibitstore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by final on 17-7-28.
 */
public interface ExhibitstoreDao {

    /**
     * 新增展品
     * @param exhibitstore
     * @return
     */
    int addExhibits(Exhibitstore exhibitstore);

    /**
     * 删除展品
     * @param exhibitorId   展品id
     */
    void deleteExhibits(Integer exhibitorId);

    /**
     * 根据展商的userid删除该展商的所有展品
     * @param userId
     */
    void deleteExhibitsByUserId(Integer userId);

    /**
     * 更新展品信息
     * <strong>需要指定展品id</strong>
     * @param exhibitstore
     */
    void updateExhibits(Exhibitstore exhibitstore);

    /**
     *
     * @Description 获取审核响应状态的展品
     * @param status	:展品状态：0-待审核，1-审核通过，-1-禁用
     * @param start	所查询的记录在数据库中的索引
     * @param size	每页的显示数量（默认20）
     * @return List<Exhibits>
     */
    public List<Exhibitstore> getAllExhibitstore(@Param("status") String status, @Param("start") Integer start, @Param("size") Integer size);

    /**
     *
     * @Description 在展会仓库中根据展商的名称查询展品
     * @param exhibitorId	展商名字
     * @param start	所查询的记录在数据库中的索引
     * @param size	每页的数量
     * @return List<Exhibitstore>
     */
    public List<Exhibitstore> getExhibitstoreByExhibitorId(@Param("exhibitorId") Integer exhibitorId,
                                                           @Param("start") Integer start, @Param("size") Integer size) ;


    /**
     *
     * @Description 在仓库中根据展品的名字查询展品
     * @param exhibitstoreName	展品名字
     * @return Exhibitstore
     */
    public Exhibitstore getExhibitstoresByName(@Param("exhibitstoreName") String exhibitstoreName) ;

    /**
     *
     * @Description 在仓库中根据展品的名字搜索展品
     * @param exhibitstoreName	展品名字
     * @param start	所查询的记录在数据库中的索引
     * @param size	每页显示数量
     * @return Exhibitstore
     */
    public List<Exhibitstore> searchExhibitstoresByName(@Param("exhibitstoreName") String exhibitstoreName,
                                                        @Param("start") Integer start, @Param("size") Integer size) ;

    /**
     * 根据展品id查询展品
     * @param id    展品id
     * @return
     */
    Exhibitstore getExhibitById(Integer id);

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
     * 根据展商id获取改展商所上架的展品数量
     * @param userId
     * @return
     */
    int getCountByExhibitorId(int userId);


}
