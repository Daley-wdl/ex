package com.exhibition.service.impl;

import com.exhibition.constants.ExhibitsContants;
import com.exhibition.dao.CommentDao;
import com.exhibition.dao.ExhibitsDao;
import com.exhibition.dao.ExhibitsPhotoDao;
import com.exhibition.dao.UserDao;
import com.exhibition.enums.ExceptionEnums;
import com.exhibition.enums.UserStatus;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Exhibits;
import com.exhibition.po.ExhibitsPhoto;
import com.exhibition.po.Exhibitstore;
import com.exhibition.service.ExhibitStoreService;
import com.exhibition.service.ExhibitsService;
import com.exhibition.utils.CheckFieldUtils;
import com.exhibition.utils.FileUtil;
import com.exhibition.utils.PatternUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by final on 17-7-30.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
@Transactional
public class ExhibitsServiceImpl implements ExhibitsService {


    private static final Logger logger = Logger.getLogger(ExhibitsServiceImpl.class);

    @Autowired
    private ExhibitsDao exhibitsDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ExhibitsPhotoDao exhibitsPhotoDao;

    @Autowired
    private ExhibitStoreService exhibitStoreService;

    @Autowired
    private CommentDao commentDao;

    private final int default_size = 20;//默认的请求条数size大小

    /**
     * 删除展品
     * @param id
     */
    @Override
    public void delete(Integer id) {
        if (id != null) {

            exhibitsDao.deleteExhibits(id);
            exhibitsPhotoDao.deleteByExhibitsId(id);
        }
    }

    /**
     * @param page
     * @param size
     * @param status 审核状态:展品状态：0-待审核，1-审核通过，-1-禁用  @return List<Exhibits>
     * @Description 获取所有通过审核的展品
     */
    @Override
    public List<Exhibits> getExhibits(Integer page, Integer size, String status) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = default_size;
        }
        Integer start = (page.intValue() - 1) * size.intValue();

        int statusVal = Integer.parseInt(status);
        if (statusVal >= -1 && statusVal <= 1) {
            //status在-1~1的范围内
            return exhibitsDao.getExhibits(status, start, size);
        }
        return null;
    }

    /**
     * @param page        当前页数
     * @param size        每页显示数量
     * @param exhibitorId 展商id
     * @return List<Exhibits>
     * @Description 获取指定id的展商的展品
     */
    @Override
    public List<Exhibits> getExhibitsByExhibitorId(Integer page, Integer size, Integer exhibitorId) {
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = default_size;
        }
        Integer start = (page.intValue() - 1) * size.intValue();
        if (exhibitorId <= 0) {
            return null;
        }
        return exhibitsDao.getExhibitsByExhibitorId(exhibitorId, start, size);
    }

    /**
     * @param exhibitsName
     * @return List<Exhibits>
     * @Description 根据展品名称进行模糊查询
     */
    @Override
    public List<Exhibits> queryExhibitsByName(String exhibitsName,Integer page, Integer size) {
        if (StringUtils.isEmpty(exhibitsName)) {
            return null;
        }
        if (page == null || page <= 0) {
            page = 1;
        }
        if (size == null || size <= 0) {
            size = default_size;
        }
        Integer start = (page.intValue() - 1) * size.intValue();
        return exhibitsDao.queryExhibitsByName(exhibitsName, start, size);
    }

    /**
     * @param exhibitsName
     * @return Exhibits
     * @Description 通过展品名获得唯一符合的展品
     */
    @Override
    public Exhibits getExhibitsByName(String exhibitsName) {
        if (StringUtils.isEmpty(exhibitsName)) {
            return null;
        }
        return exhibitsDao.getExhibitsByName(exhibitsName);
    }

    /**
     * 根据名字和状态返回展品
     * @param page
     * @param size
     * @param name
     * @param status
     * @return
     */
    @Override
    public List<Exhibits> searchExhibitsByName(int page, int size, String name, String status) {
        page = (page <= 1) ? 1 : page;
        size = (size <= 1) ? 1 : default_size;
        int start = (page - 1) * size;
        if (!UserStatus.checkStatus(status)) {
            //如果输入的status不合法
            throw new ServiceException(ExceptionEnums.WrongStatus);
        }
        List<Exhibits> list=exhibitsDao.selectExhibitsByName(start,size,name,status);
        return list;
    }

    /**
     * 添加展品，需要包含展品所属展商的ExhibitorId(userId)
     * @param exhibits
     * @param exhibitstoreDB
     * @return  true：执行添加操作，false：执行增加库存操作
     * @throws DataAccessException
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean addExhibits(Exhibits exhibits,Exhibitstore exhibitstoreDB) {
        boolean result = false;
        Integer number = exhibits.getNumber();
        Integer price = exhibits.getPrice();
        //设置总数和单价
        if (number == null || number < 0 || price == null || price <= 0) {
            throw new ServiceException(ExceptionEnums.LackInfo);
        }

        //1、检查是否已经发布过该展品，如果已发布而且仓库表中的展品数量不为0，则执行添加展品，否则执行插入
        Exhibits exhibitsDB = exhibitsDao.getExhibitsById(exhibits.getId());
        if (exhibitsDB == null) {
            //插入新纪录
            exhibitsDao.insertExhibits(exhibits);
            result = true;
        } else {
            //更新数量
            Exhibits exhibitsToUpdate = new Exhibits();
            exhibitsToUpdate.setId(exhibits.getId());
            Integer totalNum = exhibitsDB.getNumber() + exhibitstoreDB.getNumber();
            if (totalNum < 0) {
                //溢出
                logger.error("展商添加展品数量，发生溢出");
                throw new ServiceException(ExceptionEnums.Error);
            }
            exhibitsToUpdate.setNumber(totalNum);
            exhibitsDao.updateExhibits(exhibitsToUpdate);
            result = false;
        }
        //2、将仓库中的展品数量置0
        Exhibitstore exhibitstore = new Exhibitstore();
        exhibitstore.setId(exhibits.getId());
        exhibitstore.setNumber(0);
        exhibitStoreService.update(exhibitstore);
        return result;
    }

    /**
     * @param id
     * @param status :1--通过。0--待审核，-1--未通过(数据库对status字段没有设置约束，所以可以设置成其他字符串)
     * @return Exhibits
     * @Description 设置展品的审核状态
     */
    @Override
    public void setExhibitsStatus(Integer id, String status) {
        Exhibits exhibits = new Exhibits();
        exhibits.setExhibitorId(id);
        exhibits.setStatus(status);
        exhibitsDao.updateExhibits(exhibits);
    }

    /**
     * 获取展品记录总数
     *
     * @return
     */
    @Override
    public int getCount() {
        return exhibitsDao.getCount();
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

        if (intStatus < -1 || intStatus > 1) {
            //不在-1，0，1之间
            throw new ServiceException(ExceptionEnums.WrongStatus);
        }
        return exhibitsDao.getCountByStatus(status);
    }

    /**
     * 根据展商id查询该展商上传的展品总数
     *
     * @param exhibitorId
     * @return
     */
    @Override
    public int getCount(Integer exhibitorId) {
        return exhibitsDao.getCountByExhibitorId(exhibitorId);
    }

    @Override
    public Exhibits getExhibitsById(Integer id) {
        return exhibitsDao.getExhibitsById(id);
    }

    /**
     * 更新展品信息
     *
     * @param exhibits
     */
    @Override
    public void update(Exhibits exhibits) {
        exhibitsDao.updateExhibits(exhibits);
    }

    /**
     * 为展品添加详细介绍的图片,需要包含展品id，展商userId，访问路径
     *
     * @param exhibitsPhoto
     */
    @Override
    public void addExhibtsPhoto(ExhibitsPhoto exhibitsPhoto) {
        if (!CheckFieldUtils.checkField(exhibitsPhoto, 3)) {
            throw new ServiceException(ExceptionEnums.LackInfo);
        }
        exhibitsPhotoDao.add(exhibitsPhoto);
    }

    /**
     * 批量增加图片,需要包含展品id，展商userId，访问路径
     *
     * @param photoList
     */
    @Override
    public void addExhibitsPhoto(List<ExhibitsPhoto> photoList) {
        exhibitsPhotoDao.addBatch(photoList);
    }

    /**
     * 更新展品的封面图片
     *
     * @param exhibitsId
     * @param visitPath
     */
    @Override
    public void updateExhibitsCoverPhoto(Integer exhibitsId, String visitPath) {


        //更新到数据库
        Exhibits exhibitsToUpdate = new Exhibits();
        exhibitsToUpdate.setMainPhotoPath(visitPath);
        exhibitsToUpdate.setId(exhibitsId);
        exhibitsDao.updateExhibits(exhibitsToUpdate);

    }

    /**
     * 根据展品id获取该展品详情信息的图片
     *
     * @param exhibitsId
     * @return
     */
    @Override
    public List<ExhibitsPhoto> getExhibitsPhotos(Integer exhibitsId) {
        return exhibitsPhotoDao.getPhotos(exhibitsId);
    }

    /**
     * 根据展品id删除图片
     *
     * @param exhibitsId
     */
    @Override
    public void deleteExhibitsPhotosByExhibitsId(Integer exhibitsId) {
        exhibitsPhotoDao.deleteByExhibitsId(exhibitsId);
    }

    /**
     * 根据图片id删除图片
     *
     * @param photoId
     */
    @Override
    public void deleteExhibitsPhotoById(Integer photoId) {
        exhibitsPhotoDao.delete(photoId);
    }

    /**
     * 获取图片信息
     *
     * @param photoId
     * @return
     */
    @Override
    public ExhibitsPhoto getExhibitsPhotoById(Integer photoId) {
        return exhibitsPhotoDao.getExhibitsById(photoId);
    }

    /**
     * 更新展品的平均好评度
     *
     * @param exhibitsId
     */
    @Override
    public void updateAvgGrade(int exhibitsId) {
        int grade = 0;
        float avgGrade = commentDao.getAvgGrade(exhibitsId);
        grade = Math.round(avgGrade);//四舍五入取整

        if (grade > ExhibitsContants.MAX_AVG_GRADE) {
            throw new ServiceException(ExceptionEnums.SurpassMaxCount);
        }
        exhibitsDao.updateAvgGrade(exhibitsId, grade);
    }


}
