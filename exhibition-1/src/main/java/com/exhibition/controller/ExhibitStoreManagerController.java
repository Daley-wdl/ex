package com.exhibition.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exhibition.enums.ExceptionEnums;
import com.exhibition.enums.ExhibitsStatus;
import com.exhibition.enums.RoleList;
import com.exhibition.enums.UserStatus;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.CommonCategory;
import com.exhibition.po.Exhibits;
import com.exhibition.po.Exhibitstore;
import com.exhibition.service.ExhibitStoreService;
import com.exhibition.service.ExhibitorService;
import com.exhibition.service.ExhibitsService;
import com.exhibition.utils.CheckFieldUtils;
import com.exhibition.utils.FieldTransfer;
import com.exhibition.utils.FileUtil;
import com.exhibition.utils.PatternUtils;
import com.exhibition.vo.DataResult;
import com.exhibition.vo.LayuiReplay;
import com.exhibition.vo.ReplyResult;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;


@Controller
@RequestMapping(value = "/exhibitStore")
public class ExhibitStoreManagerController {

	//上传展品到展品仓库，进行审核，审核后，加入exhibits表

	private static Logger logger = Logger.getLogger(ExhibitStoreManagerController.class);

	@Value("${exhibitstore.imgSavePath}")
	private String EXHIBITSTORE_IMG_SAVEPATH;
	@Value("${exhibitstore.imgVisitPath}")
	private String EXHIBITSTORE_IMG_VISITPATH;

	@Value("${exhibits.mainImgSavePath}")
	private String ExhibitCoverSavePath;//展品封面的保存根路径

	@Value("${exhibits.mainImgVisitPath}")
	private String ExhibitCoverVisitPath;//展品封面的访问路径
	

	@Resource
	private ExhibitsService exhibitsService;
	
	@Resource
	private ExhibitStoreService exhibitStoreService;

	@Resource
	private ExhibitorService exhibitorService;


	/**
	 * 
	 * @Description 根据审核状态查询仓库中的展品
	 * @param request
	 * @param page
	 *            当前页数
	 * @param size
	 *            每页的数量
	 * @param status
	 *            审核状态
	 * @return String
	 */
	@RequestMapping(value = "/findAllExhibit")
	public String findAllExhibit(HttpServletRequest request,
			@RequestParam("page") Integer page,
			@RequestParam("size") Integer size,
			@RequestParam(value="status") String status) {
		request.setAttribute("page", page);//将当前页数放入请求域

		//g获取当前账户
		Subject subject = SecurityUtils.getSubject();

		List<Exhibitstore> exhibitList = null;
		if (subject != null && subject.hasRole(RoleList.Exhibitor.getRoleName())) {
			//操作由展商发出,查找该展商上传的展品（所有审核状态）
			String exhibitorName = (String) subject.getSession().getAttribute("username");
			exhibitList = exhibitStoreService.getExhibitstoresByExhibitorName(exhibitorName, page, size);
			request.setAttribute("exhibitList", exhibitList);
			return "admin/exhibitStore/exhibitStore-list";
		} else {
			//操作由管理员或者普通用户发出,根据展品审核状态查找所有展商上传的展品
			exhibitList = exhibitStoreService
					.getALLExhibitstores(status, page, size);
			request.setAttribute("exhibitList", exhibitList);
			request.setAttribute("status", status);
			return "admin/exhibitStore/exhibitStore-list-admin";
		}
	}

	/**
	 * 
	 * @Description 新增一个展品，包含信息有：展品名-exhibitsName，类别-category，展品介绍-intro
	 * @param photo
	 *            展品照片
	 * @param exhibitstore
	 *            有springmvc自动组装的Exhibitstore对象
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 *             String
	 */
	@RequestMapping(value="/addExhibits",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String addExhibitstore(@RequestParam("uploadPhoto") MultipartFile photo, @Validated Exhibitstore exhibitstore,
								  BindingResult bindingResult, HttpServletRequest request)
			throws IllegalStateException, IOException {
		Gson gson = new Gson();
		if (bindingResult.hasErrors()) {
			return gson.toJson(new ReplyResult(0,"Error"));
		}
		Subject subject = SecurityUtils.getSubject();
		Integer userId = (Integer) subject.getSession().getAttribute("userId");

		/*
		验证
		 */
		if (photo.isEmpty()) {
			// 未上传照片
			//或者除id、exhibitorId、mainPhotoPath、status以外还存在空值
			return gson.toJson(new ReplyResult(0, "信息不完整"));
		}
		if ( !subject.hasRole(RoleList.Exhibitor.getRoleName()) ) {
			// 未登录或者不是展商
			return gson.toJson(new ReplyResult(0,"只有展商才能增加展品"));
		}


		/*
		为保存图片做准备
		 */
		String originName = photo.getOriginalFilename();
		String fileType = originName.substring(originName.lastIndexOf(".") ).toLowerCase();//文件类型(.*)
		if (!FileUtil.checkPhotoType(photo) || photo.isEmpty()) {
			//检查上传图片的格式
			return gson.toJson(new ReplyResult(0, "图片格式不正确"));
		}
		String fileName = System.currentTimeMillis()+fileType;//已当前时间为文件名保存图片

		// 相对于当前项目的访问路径
		//访问路径由（path + “/” + 用户id + “/” + 文件名)组成
		String contextPath = request.getContextPath();
		contextPath.replace('\\', '/');
		String visitPath = contextPath + EXHIBITSTORE_IMG_VISITPATH + userId + "/" + fileName;
		// 保存图片
		String saveRootPath = EXHIBITSTORE_IMG_SAVEPATH + userId ;
		File file = new File(saveRootPath );
		if (!file.exists()) {
			// 该展商的文件夹不存在则新建一个文件夹
			file.mkdirs();
		}


		/*
		先保存记录到数据库，成功后保存图片
		 */
		//填充其他属性
		exhibitstore.setMainPhotoPath(visitPath);//设置保存路径
		exhibitstore.setExhibitorId(userId );// 设置展商id,即userId
		exhibitstore.setStatus("0");// 设置审核状态（0）
		try {
			boolean successed = exhibitStoreService.save(exhibitstore);// 保存
			if (successed) {
				//保存常用标签到数据库
				exhibitorService.updateCommonCategory(exhibitstore.getCategory(), userId);
				//保存图片
				String savePath = saveRootPath + File.separator + fileName;
				photo.transferTo(new File(savePath));
				if (logger.isInfoEnabled()) {
					logger.info("新增展品到展品仓库成功！\n展品封面图片保存路径:" + savePath + "\n访问路径:" + visitPath);
				}
				return gson.toJson(new ReplyResult(1, "保存成功"));
			} else {
				return gson.toJson(new ReplyResult(0, "保存失败"));
			}
		} catch (ServiceException e) {
			if (logger.isInfoEnabled()) {
				logger.info(e);
			}
			return gson.toJson(new ReplyResult(0, e.getMessage()));
		} catch (DataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info(e);
				//展品同名
				return gson.toJson(new ReplyResult(0, "保存到数据库失败!"));
			}
		}
		return gson.toJson(new ReplyResult(0,"保存失败"));
	}

	/**
	 * 
	 * @Description 修改展品的status
	 * @return String
	 */
	@RequestMapping(value="/setStatus",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String setStatus(Exhibitstore exhibitstore) {
		String status = exhibitstore.getStatus();//要修改的status
		Integer id = exhibitstore.getId();//展品id
		Gson gson = new Gson();
		Subject subject = SecurityUtils.getSubject();

		// 验证角色
		if ( !subject.hasRole(RoleList.Manager.getRoleName()) ) {
			// 不是管理员时，返回错误信息
			return gson.toJson(new ReplyResult(0, ExceptionEnums.Error.getMessage()));
		}

		if (!UserStatus.checkStatus(status)) {
			//status不合法
			return gson.toJson(new ReplyResult(0, ExceptionEnums.WrongStatus.getMessage()));
		}
		
		exhibitStoreService.update(exhibitstore);
		
		String result = gson.toJson(new ReplyResult(1,"修改成功!") );
		logger.info(result);
		return result;
	}
	
	/**
	 * 
	 * @Description 查看展品详情
	 * @param id	展品id
	 * @return String
	 */
	@RequestMapping(value="/showDetial",produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    @ResponseBody
	public String showDetial(@RequestParam("id")Integer id) {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        if (!subject.hasRole(RoleList.Exhibitor.getRoleName())) {
            return gson.toJson(new ReplyResult(0, "权限不足"));
        }

        Exhibitstore exhibitstore = exhibitStoreService.getExhibitById(id);

		String json = new DataResult<Exhibitstore>(1, "OK", exhibitstore).toJson();
		return json;
	}
	
	/**
	 * 
	 * @Description 页面通过页数、数量动态获取列表（by ajax）
	 * @param page	页数
	 * @param size	每页显示数量
	 * @param status	审核状态
	 * @return String
	 */
	@RequestMapping(value="/getExhibitsList",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String getExhibitsList(
			@RequestParam("page") Integer page,
			@RequestParam("size") Integer size,
			@RequestParam(value="status",required=false) String status) {
		Subject subject = SecurityUtils.getSubject();

		List<Exhibitstore> exhibitList = null;//结果
		if ( subject.hasRole(RoleList.Exhibitor.getRoleName()) ) {
			//操作由展商发出,查找该展商上传的展品（所有审核状态）
			String exhibitorName = (String) subject.getSession().getAttribute("username");
			try {
				exhibitList = exhibitStoreService.getExhibitstoresByExhibitorName(exhibitorName, page, size);
			} catch (ServiceException e) {
				return new Gson().toJson(new LayuiReplay<Exhibitstore>(1,e.getMessage(),0,Collections.emptyList()));
			}
		} else if (subject.hasRole(RoleList.Manager.getRoleName())) {
			//操作由管理员发出,根据展品审核状态查找所有展商上传的展品
			exhibitList = exhibitStoreService
					.getALLExhibitstores(status, page, size);
		} else {
			//除管理员和展商外，其他角色无法查询
			exhibitList = Collections.emptyList();
		}

		Integer count = exhibitStoreService.getCountByExhibitorId((Integer) subject.getSession().getAttribute("userId"));
		String result = new Gson().toJson(new LayuiReplay<Exhibitstore>(0,"OK",count,exhibitList));
		return result;
	}
	
    /**
     * 更新展品仓库item的封面图片
      * @param photo
     * @param exhibitsId
     * @param request
     * @return
     */
	@RequestMapping(value = "/updateMainPhoto",produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    @ResponseBody
    public String updateMainPhoto(@RequestParam(value = "uploadPhoto") MultipartFile photo,@RequestParam("exhibitsId") Integer exhibitsId,
                                  HttpServletRequest request) {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();

        if (!subject.hasRole(RoleList.Exhibitor.getRoleName())) {
            return gson.toJson(new ReplyResult(0, "没有权限"));
        }

        Integer userId = (Integer) subject.getSession().getAttribute("userId");
		if (logger.isInfoEnabled()) {
			logger.info("用户"+userId + "修改展品:"+exhibitsId );
		}

        if (!photo.isEmpty()) {

            Exhibitstore exhibitstoreDB = exhibitStoreService.getExhibitById(exhibitsId);
            if ( ! exhibitstoreDB.getExhibitorId().equals( userId ) ) {
                //防止修改其他展商的展品
                return gson.toJson(new ReplyResult(0,ExceptionEnums.Error.getMessage()));
            }

            String originName = photo.getOriginalFilename();
            String fileType = originName.substring(originName.lastIndexOf(".")).toLowerCase();//文件类型(.*)
            if (!FileUtil.checkPhotoType(photo)) {
                //检查上传图片的格式
                return gson.toJson(new ReplyResult(0, "图片格式不正确"));
            }
            String fileName = System.currentTimeMillis() + fileType;//已当前时间为文件名保存图片

            // 设置相对于当前项目的访问路径
            //访问路径由（path + “/” + 用户id + “/” + 文件名)组成
            String contextPath = request.getContextPath();
            String visitPath = contextPath + EXHIBITSTORE_IMG_VISITPATH + userId + "/" + fileName;
//            visitPath.replace('\\', '/');

            // 设置保存图片的本地路径
            String saveRootPath = EXHIBITSTORE_IMG_SAVEPATH + userId;
            File file = new File(saveRootPath);
            if (!file.exists()) {
                // 该展商的文件夹不存在则新建一个文件夹
                file.mkdirs();
            }

            //保存图片并更新到数据库
            String savePath = saveRootPath + File.separator + fileName;
            try {
                photo.transferTo(new File(savePath));
//                visitPath = EXHIBITSTORE_IMG_VISITPATH + userId + "/" + fileName;
                Exhibitstore exhibitstore = new Exhibitstore();
                exhibitstore.setMainPhotoPath(visitPath);// 设置访问路径
                exhibitstore.setId(exhibitsId);
                exhibitStoreService.update(exhibitstore);//更新操作
            } catch (IOException e) {
                return gson.toJson(new ReplyResult(0, "保存图片失败"));
            }

            //删除原来的照片
            String oldPath = exhibitstoreDB.getMainPhotoPath();
            if (oldPath != null) {
				oldPath = EXHIBITSTORE_IMG_SAVEPATH + userId + File.separator + oldPath.substring(oldPath.lastIndexOf("/") + 1);
				File oldPhoto = new File(oldPath);

                if (oldPhoto.exists()) {
					boolean delResult = oldPhoto.delete();
					if (logger.isInfoEnabled()) {
						logger.info("用户-" + userId + "-更新展品-" + exhibitsId + "-封面图片\n删除旧图片:" + oldPath+"\n"+delResult);
					}
                }
            }

            return gson.toJson(new ReplyResult(1, "保存成功"));

        } else {
            return gson.toJson(new ReplyResult(1, "图片不能为空!"));
        }
    }

    /**
     * 更新展品仓库信息
     * @param exhibitstore
     * @return
     */
    @RequestMapping(value = "/updateExhibitstore",produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    @ResponseBody
    public String updateExhibitstore(Exhibitstore exhibitstore) {
        Subject subject = SecurityUtils.getSubject();
        Gson gson = new Gson();

		FieldTransfer.transStrToNull(exhibitstore);
		if (exhibitstore == null || exhibitstore.getId() == null) {
            return gson.toJson(new ReplyResult(0, "确实必要信息"));
        }

        if (!subject.hasRole(RoleList.Exhibitor.getRoleName())) {
            return gson.toJson(new ReplyResult(0, "没有该权限"));
        }

        try {
            exhibitstore.setMainPhotoPath(null);//防止误提交封面图片信息
            exhibitStoreService.update(exhibitstore);
            if (logger.isInfoEnabled()) {
                logger.info("用户更新展品仓库信息:\n" + exhibitstore);
            }
            return gson.toJson(new ReplyResult(1, "更新成功"));
        } catch (ServiceException e) {
            return gson.toJson(new ReplyResult(0, e.getMessage()));
        }

    }

    /**
	 * 
	 * @Description 获取查询的总数
	 * @param request
	 * @param status	如果是管理员进行查询，需要status审核状态参数
	 * @return String
	 */
	@RequestMapping(value="/getTotal",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String getTotalNumber(HttpServletRequest request,
			@RequestParam(value="status",required=false)String status ) {
		Gson gson = new Gson();
		Subject subject = SecurityUtils.getSubject();
		Integer userId = (Integer) subject.getSession().getAttribute("userId");

		int count = 0;
		if (subject.hasRole(RoleList.Exhibitor.getRoleName())) {
			//展商
			count = exhibitStoreService.getCountByExhibitorId(userId);
		}else if ( subject.hasRole(RoleList.Manager.getRoleName())) {
			//管理员
			if (status == null) {
				//如果status为空，表示i查询所有的商品总数
				count = exhibitStoreService.getCount();
			} else {
				count = exhibitStoreService.getCountByStatus(status);
			}
		} else {
			//既不是展商也不是管理员，则查询失败
			return gson.toJson(new ReplyResult(0,ExceptionEnums.LackInfo.getMessage()));
		}

		return gson.toJson(new ReplyResult(1,String.valueOf(count)));
	}

	/**
	 * 展商发布展品
	 * @param id	仓库表中待发布展品的id
	 * @return
	 */
	@RequestMapping(value="/releaseExhibits",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String releaseExhibits(@RequestParam("id")Integer id, HttpServletRequest request) {
		Gson gson = new Gson();
		Subject subject = SecurityUtils.getSubject();
		Integer userId = (Integer) subject.getSession().getAttribute("userId");
		String username = (String) subject.getSession().getAttribute("username");

		//验证权限
		if (!subject.hasRole(RoleList.Exhibitor.getRoleName())) {
			return gson.toJson(new ReplyResult(0,"没有该权限!"));
		}

		//从仓库中取出展品
		Exhibitstore exhibitstoreDB = exhibitStoreService.getExhibitById(id);
		if (exhibitstoreDB == null ) {
			if (logger.isInfoEnabled()) {
				logger.info("id-"+id+" 不存在!");
			}
			return gson.toJson(new ReplyResult(0,"该展品不存在!"));
		}
		//检查该展品是否属于该展商
		if (exhibitstoreDB.getExhibitorId() != userId) {
			if (logger.isInfoEnabled()) {
				logger.info("id-" + id + " 不属于当前展商-" + userId);
			}
			return gson.toJson(new ReplyResult(0,"该展品不存在!"));
		}

        if (!exhibitstoreDB.getStatus().equals(ExhibitsStatus.Passed.getStatus() ) ) {
		    //审核还未通过
            return gson.toJson(new ReplyResult(0,"只能发布已经通过审核的展品!"));
        }

		if (exhibitstoreDB.getNumber() <= 0) {
			//库存为0
			return gson.toJson(new ReplyResult(0,"展品库存为0!"));
		}

		//将展品仓库下的图片复制一份到展品文件夹中
		String storePath = exhibitstoreDB.getMainPhotoPath();
		final String regex = "(\\d*/\\d*\\.(jpg|png|gif|jpng)$)";
		String regexPath = PatternUtils.getPatternStr(storePath, regex, 1);
		String origenPath = EXHIBITSTORE_IMG_SAVEPATH + regexPath;
		String exhibitsMainPhotoPath = ExhibitCoverSavePath + regexPath;
		//检查保存目录是否存在
		File dirToSave = new File(ExhibitCoverSavePath + userId);
		if (!dirToSave.exists()) {
			dirToSave.mkdirs();
		}

		String visitPath = request.getContextPath() + ExhibitCoverVisitPath + regexPath;
		Exhibits exhibitsToSave = new Exhibits();
		//将exhibitstore的同名属性保存到exhibits中填充属性
		FieldTransfer.fieldTrans(exhibitstoreDB, exhibitsToSave);
		exhibitsToSave.setId(id);
		exhibitsToSave.setMainPhotoPath(visitPath);//设置封面图片访问路径

		//保存
		try {
			boolean addExhibits = exhibitsService.addExhibits(exhibitsToSave, exhibitstoreDB);
			if (addExhibits) {
				//只有执行了添加展品的操作后，才赋值图片，避免多余的IO
				FileUtil.copyFile(origenPath, exhibitsMainPhotoPath);//复制文件
			}
		} catch (ServiceException e) {
			if (logger.isInfoEnabled()) {
				logger.info("发布展品" + exhibitsToSave.getExhibitsName() + "失败:" + e.getMessage());
			}
			return gson.toJson(new ReplyResult(0, e.getMessage()));
		} catch (DataAccessException e) {
			if (logger.isInfoEnabled()) {
				logger.info("展品-" + id + "保存失败!\n", e);
			}
			return gson.toJson(new ReplyResult(0, "发布失败，请重试!"));
		}
		return gson.toJson(new ReplyResult(1,"发布成功,请到-已发布展品-页面完善展品信息"));
	}

    /**
     * 删除展品接口
     * @param exhibitsId
     * @return
     */
	@RequestMapping(value = "/delete",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
	public String deleteExhibitstore(@RequestParam("exhibitsId") Integer exhibitsId) {
        Gson gson = new Gson();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        if (!subject.isAuthenticated()) {
            //用户未登录
            return gson.toJson(new ReplyResult(0, "请先登录"));
        }

        if (!subject.hasRole(RoleList.Exhibitor.getRoleName())) {
            return gson.toJson(new ReplyResult(0, "没有该权限"));
        }

        Exhibitstore exhibitstore = exhibitStoreService.getExhibitById(exhibitsId);
        Integer userId = (Integer) session.getAttribute("userId");

        if (exhibitstore == null) {
            return gson.toJson(new ReplyResult(0, "没有该展品"));
        }
        if (exhibitstore.getExhibitorId() != userId) {
            return gson.toJson(new ReplyResult(0, "展品不属于当前用户"));
        }
        //删除本地图片
		String mainPhotoVisitPath = exhibitstore.getMainPhotoPath();
		final String regex = "(\\d*/\\d*\\.(jpg|png|gif)$)";
		String tmp = PatternUtils.getPatternStr(mainPhotoVisitPath, regex, 1);
		if (tmp != null) {
			String savePath = EXHIBITSTORE_IMG_SAVEPATH + tmp;
			File file = new File(savePath);
			if (file.exists()) {
				file.delete();
			}
		}

		if (logger.isInfoEnabled()) {
			logger.info("用户-"+userId+"-删除展品仓库项:\nexhibitstore:"+exhibitstore);
		}
		exhibitStoreService.deleteExhibitsById(exhibitsId);
		return gson.toJson(new ReplyResult(1, "删除成功!"));
    }

	/**
	 * 获取当前登录展商的常用标签
	 * @return
	 */
	@RequestMapping(value = "/getCommonCategory",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getCommonCategory() {
		Subject subject = SecurityUtils.getSubject();
		Gson gson = new Gson();

		Integer userId = (Integer) subject.getSession().getAttribute("userId");
		CommonCategory commonCategory = exhibitorService.selectCommonCategory(userId);
		if (commonCategory == null) {
			commonCategory = new CommonCategory();
		}
		String json = gson.toJson(commonCategory);
		return json;
	}

	@RequestMapping(value = "/updateCommonCategory",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String updateCommonCategory(@RequestParam("tag") String tag) {
		Subject subject = SecurityUtils.getSubject();
		Gson gson = new Gson();

		Integer userId = (Integer) subject.getSession().getAttribute("userId");
		try {
			exhibitorService.updateCommonCategory(tag,userId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e);
			}
			return gson.toJson(new ReplyResult(0, "添加从用标签失败!"));
		}
		return gson.toJson(new ReplyResult(1, "OK!"));
	}
}
