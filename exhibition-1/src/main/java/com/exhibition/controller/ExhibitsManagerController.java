package com.exhibition.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.exhibition.enums.ExceptionEnums;
import com.exhibition.enums.RoleList;
import com.exhibition.enums.UserStatus;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Exhibits;
import com.exhibition.po.ExhibitsPhoto;
import com.exhibition.service.ExhibitorService;
import com.exhibition.service.ExhibitsService;
import com.exhibition.utils.CheckFieldUtils;
import com.exhibition.utils.FieldTransfer;
import com.exhibition.utils.FileUtil;
import com.exhibition.utils.PatternUtils;
import com.exhibition.vo.DataResult;
import com.exhibition.vo.LayuiReplay;
import com.exhibition.vo.ReplyList;
import com.exhibition.vo.ReplyResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/exhibits")
public class ExhibitsManagerController {

	private static Logger logger = Logger.getLogger(ExhibitsManagerController.class);

	@Value("${exhibits.mainImgSavePath}")
	private String ExhibitCoverSavePath;//展品封面的保存根路径

	@Value("${exhibits.mainImgVisitPath}")
	private String ExhibitCoverVisitPath;//展品封面的访问根路径

	@Value("${exhibits.detailImgSavePath}")
	private String ExhibitDetailSavePath;//展品详情图片的保存根路径

	@Value("${exhibits.detailImgVisitPath}")
	private String ExhibitDetailVisitPath;//展品详情图片的访问路径

	@Autowired
	private ExhibitsService exhibitsService;

	@Autowired
	private ExhibitorService exhibitorService;

	/**
	 * 商品的模糊查询
	 * 
	 * @Author 吴德龙
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/searchExhibits")
	public String searchExhibits(HttpServletRequest request,
								 @RequestParam("page")Integer page,@RequestParam("size")Integer size) {

		String exhibitsName = request.getParameter("exhibitionName");
		List<Exhibits> list = exhibitsService.queryExhibitsByName(exhibitsName,page,size);
		logger.info("searchExhibits查询结果条数:" + list.size());
		request.setAttribute("list", list);
		return "/admin/exhibits/showExhibits"; // 调到展示页面
	}

	/**
	 *
	 * 获取所有审核通过的展品列表，由上架时间进行排序
	 *
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/selectAllExhibits",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String selectExhibits(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
		Gson gson = new Gson();
		List<Exhibits> exhibits = exhibitsService.getExhibits(page, size, UserStatus.Passed.getStatus());
		int count = exhibitsService.getCountByStatus(UserStatus.Passed.getStatus());
		String resultJson = gson.toJson(new ReplyList<Exhibits>(exhibits, count));
		logger.info(resultJson);
		return resultJson;
	}

	/**
	 * 获取当前登录的展商的所有展品
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/selectExhibitsByExhibitor",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String selectExhibitsByExhibitorId(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
		Gson gson = new Gson();
		Subject subject = SecurityUtils.getSubject();

		if (!subject.hasRole(RoleList.Exhibitor.getRoleName())) {
			return gson.toJson(new LayuiReplay<Exhibits>(1, "身份错误", 0, null));
		}

		Integer userId = (Integer) subject.getSession().getAttribute("userId");

		List<Exhibits> exhibitsList = exhibitsService.getExhibitsByExhibitorId(page, size, userId);
		int count = exhibitsService.getCount(userId);
		return gson.toJson(new LayuiReplay<Exhibits>(0, "OK", count, exhibitsList));
	}


	/**
	 * 
	 * @Description 显示展品列表
	 * @param page
	 *            :起始页数
	 * @author 张元友
	 * @return String
	 */
	@RequestMapping(value = "/findAllExhibit",method = RequestMethod.GET)
	@ResponseBody
	public String findAllExhibit(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size,
			@RequestParam(value="status",required=false) String status) {
		Gson gson = new Gson();
		Subject subject = SecurityUtils.getSubject();
		Integer userId = (Integer) subject.getSession().getAttribute("userId");
		List<Exhibits> exhibits = null;

		if (subject.hasRole(RoleList.Exhibitor.getRoleName())) {
			// 展商--返回该展商的所有审核通过的展品
			exhibits = exhibitsService.getExhibitsByExhibitorId(page, size, userId);
		} else {
			//管理员
			exhibits = exhibitsService.getExhibits(page, size, status);
		}

		return gson.toJson(exhibits);
	}

	/**
	 * 
	 * @Description 获取列表的记录总数
	 * @param status
	 * @return String
	 */
	@RequestMapping(value = "/getTotal", produces = { "text/html;charset=UTF-8" })
	@ResponseBody
	public String getTotal(
			@RequestParam(value = "status", required = false) String status) {
		Gson gson = new Gson();
		Subject subject = SecurityUtils.getSubject();
		Integer userId = (Integer) subject.getSession().getAttribute("userId");

		int total = 0;// 记录总数
		if (subject.hasRole(RoleList.Exhibitor.getRoleName())) {
			// 展商--返回该展商的所有审核通过的展品
			total = exhibitsService.getCount(userId);
		} else {
			if (status == null) {
				//如果status为空，表示i查询所有的商品总数
				total = exhibitsService.getCount();
			} else {
				total = exhibitsService.getCountByStatus(status);
			}
		}

		return gson.toJson(new ReplyResult(1, String.valueOf(total)));
	}

	/**
	 * 
	 * @Description 设置展品的审核状态（ajax请求，返回json字符串）
	 * @param id
	 *            :展品的id
	 * @param status
	 *            :展品审核状态:1--通过。0--待审核，-1--未通过
	 * @return String
	 *         ：json格式--成功:"status":"1";错误:"status":"0","error":"errorMsg..."
	 */
	@RequestMapping(value = "/setStatus", produces = { "application/json;charset=UTF-8" },method = RequestMethod.POST)
	@ResponseBody
	public String setStatus(@RequestParam("id") Integer id,
			@RequestParam("status") String status) {
		String result = null;
		// 生成json
		Gson gson = new Gson();
		Subject subject = SecurityUtils.getSubject();
		Integer userId = (Integer) subject.getSession().getAttribute("userId");

		Exhibits exhibits = new Exhibits();
		exhibits.setId(id);
		if (!UserStatus.checkStatus(status)) {
			//检查status是否合法
			result = gson.toJson(new ReplyResult(0, ExceptionEnums.WrongStatus.getMessage()));
		}
		exhibits.setStatus(status);

		//查询数据库中是否有这样展品
		Exhibits exhibitsDB = exhibitsService.getExhibitsById(id);

		if (exhibitsDB == null) {
			// id对应展品不存在,返回表示错误信息的json
			result = gson.toJson(new ReplyResult(0, "对应展品不存在"));
		} else {
			// 成功
			logger.info("用户" + userId + "修改展品:\tid=" + exhibits.getId() + "\t展品名:" + exhibits.getExhibitsName());
			exhibitsService.update(exhibits);
			result = gson.toJson(new ReplyResult(1, "修改成功!"));
		}
		return result;
	}

	/**
	 * 
	 * @Description 删除展品
	 * @param exhibitsId
	 *            展品id
	 * @return String
	 */
	@RequestMapping(value = "/deleteExhibits", produces = { "text/html;charset=UTF-8" },method = RequestMethod.POST)
	@ResponseBody
	public String deleteExhibits(@RequestParam("exhibitsId") Integer exhibitsId) {
		Gson gson = new Gson();// 生成json
		Subject subject = SecurityUtils.getSubject();
		Integer userId = (Integer) subject.getSession().getAttribute("userId");

		// 验证权限
		if (!subject.hasRole(RoleList.Manager.getRoleName()) && !subject.hasRole(RoleList.Exhibitor.getRoleName())) {
			return gson.toJson(new ReplyResult(0, "没有该权限!"));
		}
		// 取出该展品
		Exhibits exhibits = exhibitsService.getExhibitsById(exhibitsId);
		if (exhibits == null) {
			return gson.toJson(new ReplyResult(0, "该展品不存在!"));
		}

		//如果角色是展商，则需要检查该展品是否属于该展商
		if (subject.hasRole(RoleList.Exhibitor.getRoleName())) {
			if (exhibits.getExhibitorId() != userId) {
				return gson.toJson(new ReplyResult(0, "只能修改自己的展品"));
			}
		}

		// 删除展品
		//删除展品以及其详情图片
		if (logger.isInfoEnabled()) {
			logger.info("删除展品:" + exhibitsId);
		}
		//删除相关图片(封面图片和详情图片)
		String coverPhotoPath = exhibitsService.getExhibitsById(exhibitsId).getMainPhotoPath();
		try {
			File coverToDelFile = new File(transToSavePath(coverPhotoPath, ExhibitCoverSavePath));
			if (coverToDelFile.exists()) {
				if (logger.isInfoEnabled()) {
					logger.info("删除图片:" + coverToDelFile.getPath());
				}
				coverToDelFile.delete();
			}
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info(e);
			}
		}

		List<ExhibitsPhoto> photoList = exhibitsService.getExhibitsPhotos(exhibitsId);
		for (ExhibitsPhoto exhibitsPhoto : photoList) {
			String visitPath = exhibitsPhoto.getPath();
			String savePath = transToSavePath(visitPath, ExhibitDetailSavePath);
			if (savePath == null) {
				if (logger.isInfoEnabled()) {
					logger.info("删除图片失败:无法获取真实保存路径!\t" + visitPath);
				}
				continue;
			}

			File toDelFile = new File(savePath);
			if (toDelFile.exists()) {
				toDelFile.delete();
			}
		}
		exhibitsService.delete(exhibitsId);
		return gson.toJson(new ReplyResult(1,"OK"));
	}

	/**
	 * 
	 * @Description 跳转到展示展品的详情页
	 * @param id
	 *            展品id
	 * @param request
	 * @return String
	 */
	@RequestMapping(value = "/selectExhibitsById",produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
	@ResponseBody
	public String selectDetail(@RequestParam("id") Integer id,
			HttpServletRequest request) {
		Exhibits exhibits = exhibitsService.getExhibitsById(id);
		Gson gson = new Gson();
		return gson.toJson(exhibits);
	}

	/**
	 * 更新展品信息
	 * @param exhibits
	 * @return
	 */
	@RequestMapping(value = "/updateExhibits",produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
	@ResponseBody
	public String updateExhibits(Exhibits exhibits) {
		Subject subject = SecurityUtils.getSubject();
		Gson gson = new Gson();

		if (!subject.hasRole(RoleList.Exhibitor.getRoleName())) {
			return gson.toJson(new ReplyResult(0, "没有权限!"));
		}

		if (logger.isInfoEnabled()) {
			logger.info(subject.getPrincipal() + "-修改展品信息-" + exhibits);
		}
		FieldTransfer.transStrToNull(exhibits);//将对象中的""字符串设置为null
		exhibitsService.update(exhibits);
		return gson.toJson(new ReplyResult(1, "OK!"));
	}

	/**
	 * 为展品添加详细介绍的图片
	 * @param photos	图片数组，name=uploadPhoto
	 * @param exhibitsId	展品id
	 * @return
	 */
	@RequestMapping(value = "/addDetailPhoto", produces = {"text/html;charset=UTF-8"}, method = RequestMethod.POST)
	@ResponseBody
	public String addDetailPhoto(@RequestParam("uploadPhoto") MultipartFile[] photos,
								 @RequestParam("exhibitsId")Integer exhibitsId,HttpServletRequest request) {
		Gson gson = new Gson();
		int photoLen = photos.length;
		if (photoLen==0) {
			return gson.toJson(new ReplyResult(0,"上传图片不能为空！"));
		}

		Subject subject = SecurityUtils.getSubject();
		Integer userId = (Integer) subject.getSession().getAttribute("userId");

		//检查该展商是否有该展品的权限
		Exhibits exhibitsDB = exhibitsService.getExhibitsById(exhibitsId);
		if (exhibitsDB == null || exhibitsDB.getExhibitorId() != userId) {
			return gson.toJson(new ReplyResult(0, "没有该展品权限！"));
		}

		//遍历图片数组，一当前时间截为文件名保存图片，并设置访问路径，里路成功数和失败数
		int success = 0;
		List<ExhibitsPhoto> photoList = new LinkedList<>();//添加保存成功的图片
		String rootSavePath = ExhibitDetailSavePath + userId + File.separator;//保存的父目录
		String rootVisitPath = request.getContextPath() + ExhibitDetailVisitPath + userId + "/";
		boolean isRootSavePathExist = false;
		for (MultipartFile photo : photos) {
			if (photo.isEmpty())
				continue;

			String originName = photo.getOriginalFilename();
			String fileType = originName.substring(originName.lastIndexOf("."));
			if (!".jpg".equals(fileType) && !".jpeg".equals(fileType)
					&& !".png".equals(fileType) && !".bmp".equals(fileType)) {
				// 检查上传图片的格式
				continue;
			}

			//新建保存对象，并设置展商id和展品id
			ExhibitsPhoto exhibitsPhoto = new ExhibitsPhoto();
			exhibitsPhoto.setExhibitorId(userId);
			exhibitsPhoto.setExhibitsId(exhibitsId);

			//如果不存在路径，就新建一个
			if (!isRootSavePathExist) {
				File checkExistsFile = new File(rootSavePath);
				if ( ! checkExistsFile.exists() ) {
					checkExistsFile.mkdirs();
				}
				isRootSavePathExist = true;
			}

			String fileName = System.currentTimeMillis() + fileType;
			String savepath = rootSavePath + fileName;
			String visitPath = rootVisitPath + fileName;
			visitPath.replace('\\', '/');
			//设置访问路径
			exhibitsPhoto.setPath(visitPath);

			//保存图片
			try {
				photo.transferTo(new File(savepath));
			} catch (IOException e) {
				logger.debug("保存展品" + exhibitsId + "详细图片失败！\t图片名为:" + originName, e);
				continue;
			}

			photoList.add(exhibitsPhoto);
			success++;
		}

		int failed = photoLen - success;//失败个数
		logger.info("展品" + exhibitsId + "添加详细图片，成功:" + success + "\t失败:" + failed);
		exhibitsService.addExhibitsPhoto(photoList);

		return gson.toJson(new ReplyResult(1,"添加详细图片，成功:" + success + "\t失败:" + failed));
	}

	/**
	 * 修改封面图片
	 * @param photo
	 * @param exhibitsId
	 * @return
	 */
	@RequestMapping(value = "/addCoverPhoto", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.POST)
	@ResponseBody
	public String addCoverPhoto(@RequestParam("uploadPhoto") MultipartFile photo, @RequestParam("exhibitsId") Integer exhibitsId,
								HttpServletRequest request) {
		Gson gson = new Gson();
		Subject subject = SecurityUtils.getSubject();
		Integer userId = (Integer) subject.getSession().getAttribute("userId");

		if (photo.isEmpty()) {
			return gson.toJson(new ReplyResult(0, "上传图片不能为空！"));
		}

		//检查该展商是否有该展品的权限
		Exhibits exhibitsDB = exhibitsService.getExhibitsById(exhibitsId);
		if (exhibitsDB == null || exhibitsDB.getExhibitorId() != userId) {
			return gson.toJson(new ReplyResult(0, "没有该展品权限！"));
		}

		/*
        1、保存新图片
        2、保存成功后删除原本地图片
        3、更新新访问路径到数据库
         */
		Exhibits exhibits = exhibitsService.getExhibitsById(exhibitsId);
		//设置路径
		String fileName = System.currentTimeMillis() + FileUtil.getFileType(photo.getOriginalFilename());
		String visitPath = request.getContextPath() + ExhibitCoverVisitPath + exhibits.getExhibitorId() + "/" + fileName;
		String savePath = ExhibitCoverSavePath + exhibits.getExhibitorId() + File.separator + fileName;

		//保存图片
		try {
			photo.transferTo(new File(savePath));
		} catch (IOException e) {
			return gson.toJson(new ReplyResult(0, "更新封面图片失败"));
		}

		//保存成功后删除原来的图片
		String oldPhotoPath = exhibits.getMainPhotoPath();
		String oldSavePath = transToSavePath(oldPhotoPath, ExhibitCoverSavePath);
		if (oldSavePath == null) {
			if (logger.isInfoEnabled()) {
				logger.info("删除展品-" + exhibits + "-图片失败:" + "图片访问路径转化为实际保存路径出现错误!\n" + oldSavePath);
			}
		} else {
			if (logger.isInfoEnabled()) {
				logger.info(oldSavePath);
			}
			File fileToDel = new File(oldSavePath);
			if (fileToDel.exists()) {
				fileToDel.delete();
				if (logger.isInfoEnabled()) {
					logger.info("删除展品-" + exhibitsId + "-封面本地图片:" + oldSavePath);
				}
			} else {
				if (logger.isInfoEnabled()) {
					logger.info("删除展品" + exhibitsId + "封面图片失败：图片不存在--" + oldSavePath);
				}

			}
		}

		try {
			exhibitsService.updateExhibitsCoverPhoto(exhibitsId, visitPath);
			return gson.toJson(new ReplyResult(1, "更新成功!"));
		} catch (Exception e) {
			return gson.toJson(new ReplyResult(0, e.getMessage()));
		}
	}


	/**
	 * 查询展品详情图片的接口
	 * @param exhibitsId	展品id
	 * @return
	 */
	@RequestMapping(value = "/selectPhotos",produces = {"applicaton/json;charset=UTF-8"},method = RequestMethod.GET)
	@ResponseBody
	public String selectPhotos(@RequestParam("exhibitsId") Integer exhibitsId,
							   HttpServletRequest request) {
		Gson gson = new Gson();

		List<ExhibitsPhoto> exhibitsPhotoList = exhibitsService.getExhibitsPhotos(exhibitsId);
		//如果还没有详情图片，则加入一张默认图片
//		final String defaultPhotoPath = request.getContextPath() + "/static/img/photo.jpg";
//		if (exhibitsPhotoList.size() == 0) {
//			ExhibitsPhoto defaultPhoto = new ExhibitsPhoto();
//			defaultPhoto.setPath(defaultPhotoPath);
//			exhibitsPhotoList.add(defaultPhoto);
//		}

		return gson.toJson(new ReplyList<ExhibitsPhoto>(exhibitsPhotoList,exhibitsPhotoList.size()));
	}

	/**
	 * 根据展品id删除图片
	 * @param exhibtsId
	 * @return
	 */
	@RequestMapping(value = "/delete/exhibitsPhotoByExhibitsId",method = RequestMethod.GET)
	@ResponseBody
	public String deletePhotosByExhibitsId(@RequestParam("exhibitsId") Integer exhibtsId) {
		Subject subject = SecurityUtils.getSubject();
		Gson gson = new Gson();

		if (!subject.hasRole(RoleList.Exhibitor.getRoleName())) {
			return gson.toJson(new ReplyResult(0, "没有权限!"));
		}

		Integer userId = (Integer) subject.getSession().getAttribute("userId");
		Exhibits exhibits = exhibitsService.getExhibitsById(exhibtsId);
		if (!exhibits.getExhibitorId().equals(userId)) {
			if (logger.isInfoEnabled()) {
				logger.info("用户-" + userId + "-尝试删除展品图片失败:所删除展品不属于该展商!\n展品信息:" + exhibits);
			}
			return gson.toJson(new ReplyResult(0, "Error!"));
		}
		if (logger.isInfoEnabled()) {
			logger.info("删除展品-" + exhibtsId + "-所有图片\n展品信息:" + exhibits + "\n用户信息:"
					+ exhibitorService.selectByUserId(userId));
		}

		List<ExhibitsPhoto> photoList = exhibitsService.getExhibitsPhotos(exhibtsId);
		for (ExhibitsPhoto exhibitsPhoto : photoList) {
			//删除本地图片
			String visitPath = exhibitsPhoto.getPath();
			String localPath = transToSavePath(visitPath, ExhibitDetailSavePath);
			if (localPath == null) {
				if (logger.isInfoEnabled()) {
					logger.info("在删除展品详情图片时出现错误，无法由展品的访问路径转化为本地路径:" + exhibitsPhoto);
				}
			} else {
				//删除图片
				if (logger.isInfoEnabled()) {
					logger.info("删除图片:" + localPath);
				}
				FileUtil.deleteFile(new File(localPath));
			}
		}

		exhibitsService.deleteExhibitsPhotosByExhibitsId(exhibtsId);
		return gson.toJson(new ReplyResult(1,"OK"));
	}

	/**
	 * 根据图片id删除展品的详情图片
	 * @param exhibtsId	展品ｉｄ
	 * @param photoId	展示图片的ｉｄ
	 * @return
	 */
	@RequestMapping(value = "/delete/exhibitsPhotoByPhotoId",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String deletePhotosByPhotoId(@RequestParam("photoId") Integer photoId,
										@RequestParam("exhibtsId") Integer exhibtsId) {
		Subject subject = SecurityUtils.getSubject();
		Gson gson = new Gson();
		Integer userId = (Integer) subject.getSession().getAttribute("userId");

		if (!subject.hasRole(RoleList.Exhibitor.getRoleName())) {
			return gson.toJson(new ReplyResult(0, "没有权限!"));
		}

		ExhibitsPhoto exhibitsPhoto = exhibitsService.getExhibitsPhotoById(photoId);
		if (!exhibitsPhoto.getExhibitorId().equals(userId)) {
			//检查是否会误删除其他展品的图片
			return gson.toJson(new ReplyResult(0, "Error!"));
		}

		//删除本地图片
		String visitPath = exhibitsPhoto.getPath();
		String localPath = transToSavePath(visitPath, ExhibitDetailSavePath);
		if (localPath == null) {
			if (logger.isInfoEnabled()) {
				logger.info("在删除展品详情图片时出现错误，无法由展品的访问路径转化为本地路径:" + exhibitsPhoto);
			}
		} else {
			//删除图片
			if (logger.isInfoEnabled()) {
				logger.info("删除图片:" + localPath);
			}
			FileUtil.deleteFile(new File(localPath));
		}

		exhibitsService.deleteExhibitsPhotoById(photoId);
		if (logger.isInfoEnabled()) {
			logger.info("删除图片by--" + subject.getPrincipal());
		}
		return gson.toJson(new ReplyResult(1,"OK!"));
	}

	public String transToSavePath(String visitPath,String rootPath) {
		if (org.springframework.util.StringUtils.isEmpty(visitPath)) {
			return null;
		}
		final String regex = "(\\d*/\\d*\\..*$)";
		String result = PatternUtils.getPatternStr(visitPath, regex, 1);
		if (result == null) {
			return null;
		}
		result = rootPath + result;
		return result.replace("/", File.separator);
	}
}
