package com.exhibition.dao;

import com.exhibition.po.CommonCategory;
import com.exhibition.po.Exhibitor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by final on 17-7-24.
 */
public interface ExhibitiorDao {

    /**
     * 新增展商到数据库
     * @param exhibitor
     * @return
     */
    int insertExhibitor(Exhibitor exhibitor);

    /**
     * 根据userId查询展商信息
     * @param userId
     * @return
     */
    Exhibitor selectByUserId(Integer userId);

    /**
     * 获取所有展商的信息
     * @param start
     * @param size
     * @return
     */
    List<Exhibitor> selectAllExhibitor(@Param("start") int start, @Param("size") int size,
                                       @Param("status")String status);

    /**
     * 根据展商名称搜索展商（模糊查询）
     * @param username  用户名，不是真实姓名
     * @return
     */
    List<Exhibitor> searchByName(String username);

    /**
     * 根据名字状态查询
     * @return
     */
    List<Exhibitor> selectExhibitorByName(@Param("start") int start,@Param("size") int size,
                                          @Param("name") String name,@Param("status")String status);

    /**
     * 更新展商信息
     * <strong>必须指定id</strong>
     * @param exhibitor
     */
    void updateExhibitor(Exhibitor exhibitor);

    /**
     * 获取相应status展商的总人数
     *
     * @param status
     * @return
     */
    int selectTotal(String status);

    /**
     * 通过userid删除展商
     * @param userId
     * @return
     */
    void deleteExhibitorByUserId(int userId);


    /**
     * 获取展商的常用标签
     * @param userId
     * @return
     */
    CommonCategory selectCommonCategory(Integer userId);

    /**
     * 更新展商的常用类别标签
     * @param commonCategory
     */
    void updateCommonCategory(CommonCategory commonCategory);

    /**
     * 新增展商的常用标签
     * @param commonCategory
     */
    void insertCommonCategory(CommonCategory commonCategory);

}
