package com.exhibition.dao;

import com.exhibition.po.ExhibitsPhoto;

import java.util.List;

public interface ExhibitsPhotoDao {

    /**
     * 添加图片
     * <p>需要指定对应展品的id，展商的userId，访问路径</p>
     * @param exhibitsPhoto
     */
    void add(ExhibitsPhoto exhibitsPhoto);

    /**
     * 批量增加照片
     * @param exhibitsPhotoList
     */
    void addBatch(List<ExhibitsPhoto> exhibitsPhotoList);

    /**
     * 删除指定exhbitsId
     * @param exhibitsId
     */
    void deleteByExhibitsId(int exhibitsId);

    /**
     * 入参为展商的userId，展出该展商所上传的所有图片
     * @param exhibitorId
     */
    void deleteByExhibitorId(int exhibitorId);

    /**
     * 根据图片的id删除
     * @param id
     */
    void delete(int id);

    /**
     * 查询展品的所有详细图片的额路径
      * @param exhibitsId
     * @return
     */
    List<ExhibitsPhoto> getPhotos(Integer exhibitsId);

    /**
     * 删除相应展品的详细图片
     * @param exhibitssId
     */
    void deletePhotosByExhibitsId(Integer exhibitssId);

    /**
     * 珊瑚展商的所有展品的详情页图片
     * @param exhibitorId
     */
    void deletePhotosByExhibitorId(Integer exhibitorId);

    /**
     * 获取图片所属的展品id
     * @param photoId
     * @return
     */
    ExhibitsPhoto getExhibitsById(Integer photoId);

}
