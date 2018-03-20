package com.exhibition.service.impl;

import com.exhibition.dao.ExhibitiorDao;
import com.exhibition.dao.RoleDao;
import com.exhibition.dao.UserDao;
import com.exhibition.enums.ExceptionEnums;
import com.exhibition.enums.RoleList;
import com.exhibition.enums.UserStatus;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.CommonCategory;
import com.exhibition.po.Exhibitor;
import com.exhibition.po.User;
import com.exhibition.service.ExhibitorService;
import com.exhibition.utils.CheckFieldUtils;
import com.exhibition.utils.FieldTransfer;
import com.exhibition.utils.PasswordUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by final on 17-7-25.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class ExhibitorServiceImpl implements ExhibitorService {

    private static final Logger logger = Logger.getLogger(ExhibitorServiceImpl.class);

    @Autowired
    private ExhibitiorDao exhibitiorDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordUtils passwordUtils;

    private final int default_size = 20;//默认的请求条数size大小
    public static final int MAX_TAG_SIZE = 200;//展商常用标签的最多长度
    public static final String TAG_GAP = ";";//作为一串标签的分隔符

    /**
     * 展商注册
     *
     * @param user      ：包含用户users表的信息
     * @param exhibitor ：包含展商所需的额外信息
     * @return 是否注册成功
     */
    @Override
    public boolean register(User user, Exhibitor exhibitor) throws ServiceException {

        User userInDb = userDao.findUserByName(user.getUsername());
        if (userInDb != null) {
            //如果用户名已经存在
            throw new ServiceException(ExceptionEnums.UsernameExists);
        }

        //加密密码,设置盐值
        passwordUtils.encryptPassword(user);
        user.setCreatTime(new Timestamp(System.currentTimeMillis()));
        //插入记录成功时，返回值为1
        boolean success = (userDao.insertUser(user) == 1);
        if (!success) {
            throw new ServiceException(ExceptionEnums.Error);
        }

        //exhibitor设置userId（userDao再插入user成功后，会返回自增主键的值）
        exhibitor.setUserId(user.getUserId());

        //为用户   注册角色
        boolean result = true;
        result = result && (roleDao.registRole(user, RoleList.Exhibitor.getRoleId()) == 1);

        result = result && (exhibitiorDao.insertExhibitor(exhibitor)==1);

        return result;
    }

    /**
     * 根据userId查询展商信息
     *
     * @param userId
     * @return
     */
    @Override
    public Exhibitor selectByUserId(Integer userId) throws ServiceException {
        if (userId == null || userId < 0) {
            //userId不为空且大于0
            throw new ServiceException(ExceptionEnums.WrongId);
        }
        return exhibitiorDao.selectByUserId(userId);
    }

    /**
     * g根据用户名查找展商
     *
     * @param username
     * @return
     */
    @Override
    public Exhibitor selectByUsername(String username) {
        User user = userDao.findUserByName(username);
        if (user == null) {
            //该用户名不存在
            throw new ServiceException(ExceptionEnums.UserNotExist);
        }
        Integer userId = user.getUserId();
        Exhibitor exhibitor = exhibitiorDao.selectByUserId(userId);
        return exhibitor;
    }

    /**
     * 获取所有展商的信息
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<Exhibitor> selectAllExhibitor(int page, int size,String status) throws ServiceException {
        //检查输入
        page = (page <= 1) ? 1 : page;
        size = (size <= 1) ? 1 : default_size;
        int start = (page - 1) * size;
        if (!UserStatus.checkStatus(status)) {
            //检查输入的status是否合法
            throw new ServiceException(ExceptionEnums.WrongStatus);
        }

        List<Exhibitor> exhibitorList = exhibitiorDao.selectAllExhibitor(start, size,status);

        return exhibitorList;
    }


    /**
     * 修改展商的信息
     *
     * @param exhibitor
     */
    @Override
    public void updateExhibitor(Exhibitor exhibitor) {
        FieldTransfer.transStrToNull(exhibitor);
        exhibitiorDao.updateExhibitor(exhibitor);
    }

    /**
     *
     * @param page
     * @param size
     * @param name
     * @param status
     * @return
     */
    @Override
    public List<Exhibitor> searchExhibitorByName(int page, int size, String name, String status) {
        page = (page <= 1) ? 1 : page;
        size = (size <= 1) ? 1 : default_size;
        int start = (page - 1) * size;
        if (!UserStatus.checkStatus(status)) {
            //如果输入的status不合法
            throw new ServiceException(ExceptionEnums.WrongStatus);
        }
        List<Exhibitor> list=exhibitiorDao.selectExhibitorByName(start,size,name,status);
        return list;
    }

    /**
     * 获取相应status展商的总人数
     *
     * @param status
     * @return
     */
    @Override
    public int selectTotal(String status) throws ServiceException {
        if (!UserStatus.checkStatus(status)) {
            //如果输入的status不合法
            throw new ServiceException(ExceptionEnums.WrongStatus);
        }

        int result = exhibitiorDao.selectTotal(status);
        return result;
    }

    /**
     * 删除一个展商
     * @param userId
     * @return
     */
    @Override
    public void deleteExhibitor(int userId) {
        if (userId <= 0) {
            return;
        }
        userDao.deleteUser(userId);//删除用户users表中的记录
        userDao.deleteUserToRole(userId);//删除用户-角色表user_role的信息
        exhibitiorDao.deleteExhibitorByUserId(userId); //删除exhibitor里的展商
    }

    /**
     * 获取展商的常用展品标签
     *
     * @param userId
     */
    @Override
    public CommonCategory selectCommonCategory(Integer userId) {
        if (userId == null) {
            return null;
        }
        return exhibitiorDao.selectCommonCategory(userId);
    }

    /**
     * 更新展商的常用标签
     * @param tag   新标签
     * @param userId    展商id
     */
    public void updateCommonCategory(String tag,Integer userId){
        //1、如果数据库中还没有该展商的常用标签，则新建一个
        //2、如果已有记录，则更新标签

        CommonCategory commonCategory = exhibitiorDao.selectCommonCategory(userId);
        if (commonCategory == null) {
            commonCategory = new CommonCategory();
            commonCategory.setUserId(userId);
            commonCategory.setTag(tag + TAG_GAP);
            exhibitiorDao.insertCommonCategory(commonCategory);
        } else {
            //如果原有标签记录已达到最多长度
            String oriTag = commonCategory.getTag();
            if (oriTag.indexOf(tag) != -1) {
                return;
            }
            String tagToSave = null;
            //当原有的标签长度过大，无法继续加入新标签时，裁取掉最前面部分的标签
            while ((oriTag.length() + tag.length()) >= MAX_TAG_SIZE) {
                int pos = oriTag.indexOf(TAG_GAP);
                if (pos == -1) {
                    break;//以防万一
                }
                oriTag = oriTag.substring(pos);
            }
            tagToSave = oriTag + tag + TAG_GAP;
            commonCategory.setTag(tagToSave);
            exhibitiorDao.updateCommonCategory(commonCategory);
        }
    }

}
