package com.exhibition.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.exhibition.enums.ExceptionEnums;
import com.exhibition.enums.RoleList;
import com.exhibition.enums.UserStatus;
import com.exhibition.po.Exhibitor;
import com.exhibition.po.User;
import com.exhibition.service.ExhibitorService;
import com.exhibition.service.UserService;
import com.exhibition.utils.CheckFieldUtils;
import com.exhibition.utils.FieldTransfer;
import com.exhibition.vo.ReplyList;
import com.exhibition.vo.ReplyResult;
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

/**
 * 展商管理，处理展商管理的内容
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/exhibitor")
public class ExhibitorController {

	private static Logger logger = Logger.getLogger(ExhibitorController.class);

	@Value("${exhibitor.savePath}")
	private String PhotoSavePath;
	
	@Autowired
	private ExhibitorService exhibitorService;

	@Autowired
	private UserService userService;

    
	//默认构造函数
//	public ExhibitorController() {
//		System.out.println("============ExhibitorManagerController======================");
//	}
	
	/**
	 *	查询所有已审核通过的展商
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/listAllExhibitor",method = RequestMethod.GET)
	@ResponseBody
	public String listAllExhibitor(HttpServletRequest request,
			@RequestParam("page")Integer page,@RequestParam("size")Integer pageSize){
//		List<Exhibitor> exhibitors = exhibitorDao.getAllExhibitor() ;
		List<Exhibitor> exhibitors = exhibitorService.selectAllExhibitor(page, pageSize, UserStatus.Passed.getStatus());
		int count = exhibitorService.selectTotal(UserStatus.Passed.getStatus());
		return new Gson().toJson(new ReplyList<Exhibitor>( exhibitors,count));
		//将展商添加到request域里
//		request.setAttribute("exhibitors",exhibitors) ;
//		request.setAttribute("page", page);
//		//将结果带到exhibitor/exhibitor-list.jsp页面区显示
//		return "admin/exhibitor/exhibitor-list" ;
	}
	
	/**
	 * 
	 * @Descoption 查询所有待审核的展商
	 * @param request
	 * @return String
	 */
	@RequestMapping(value="/listAllCheckingExhibitor",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String listAllCheckingExhibitor(HttpServletRequest request,
			@RequestParam("page")Integer page,@RequestParam("size")Integer pageSize){
//		List<Exhibitor> exhibitors = exhibitorDao.getAllCheckingExhibitor();
		List<Exhibitor> exhibitors = exhibitorService.selectAllExhibitor(page, pageSize, UserStatus.Waiting.getStatus());
		int count = exhibitorService.selectTotal(UserStatus.Failed.getStatus());
		return new Gson().toJson(new ReplyList<Exhibitor>( exhibitors,count));

//		request.setAttribute("exhibitors",exhibitors) ;
//		request.setAttribute("page", page);
//		return "admin/exhibitor/checking-list" ;
	}
	
	/**
	 * 
	 * @Descoption 查看展商详情
	 * @return String
	 */
	@RequestMapping(value="/exhibitorShow",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	private String exhibitorDatail(){
		Gson gson = new Gson();
//		Exhibitor exhibitor = exhibitorDao.get(id);
		Subject subject = SecurityUtils.getSubject();
		Integer id = (Integer) subject.getSession().getAttribute("userId");

		//session内userId不存在
		if (id == null) {
			if (logger.isInfoEnabled()) {
				logger.info("userId为空!");
			}
			return gson.toJson(new Exhibitor());
		}
		Exhibitor exhibitor = exhibitorService.selectByUserId(id);

		return gson.toJson(exhibitor);

//		request.setAttribute("exhibitor", exhibitor);
//		return "admin/exhibitor/exhibitor-show";
	}
	
	/**
	 * 
	 * @Descoption 跳转到编辑展商页面
	 * @param request
	 * @return String
	 */
	@RequestMapping(value="/jumpEditExhibitor")
	private String jumpEditExhibitor(HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();

		if (!subject.hasRole(RoleList.Exhibitor.getRoleName())) {
			request.setAttribute("error", "没有权限修改展商的信息");
			//不是展商
			return "public/error";
		}

		//未在request中放置roleId
//		Integer roleId = (Integer) request.getSession().getAttribute("roleId");
//		if (3 != roleId) {
//			request.setAttribute("error", "没有权限修改展商的信息");
//			//不是展商
//			return "public/error";
//		}
//		Exhibitor exhibitor = exhibitorDao.get(id);

		//获取用户名
		String username = subject.getPrincipal().toString();
		//根据用户名获取id

		Integer userId = (Integer) subject.getSession().getAttribute("adminId");

		//如果id不存在（未登录）则返回到登录页面
		if (userId == null) {
			return "redirect:/common/login";
		}
		
		Exhibitor exhibitor = exhibitorService.selectByUserId(userId);
		request.setAttribute("exhibitor", exhibitor);
//		return "admin/exhibitor/exhibitor-edit";
		return "admin/exhibitor/editExhibitor";
	}
	
	/**
	 * 
	 * @Descoption 跳转到修改密码页面
	 * @param request
	 * @param id
	 * @return String
	 */
	@RequestMapping(value="/jumpChangePass")
	public String jumpChangePass(HttpServletRequest request,Integer id){
//		Exhibitor exhibitor = exhibitorDao.get(id);
		Exhibitor exhibitor = exhibitorService.selectByUserId(id);
		request.setAttribute("exhibitor", exhibitor);
		return "admin/exhibitor/exhibitor-changePass";
	}


	/**
	 * 
	 * @Description 修改展商信息（包括名字。邮箱、介绍、电话号码）
	 * @param exhibitor
	 * @return String
	 */
	@RequestMapping(value="/editExhibitor",method = RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String editExhibitor(Exhibitor exhibitor) {
		Subject subject = SecurityUtils.getSubject();

		Gson gson = new Gson();
		String result=null;
		//获取权限
		if ( !subject.hasRole(RoleList.Exhibitor.getRoleName()) ) {
			//检查是否有更改展商信息的权限
			result=gson.toJson(new ReplyResult(0,"没有该权限!"));
		} else {
//			Exhibitor exhibitorDB = exhibitorService.selectByUserId(exhibitor.getUserId() );
//			//需要对密码进行加密(加密步骤应该放到service或者dao中)
//			exhibitorDB.setRealName(exhibitor.getRealName() );
//			exhibitorDB.setPassword(exhibitor.getPassword() );
//			exhibitorDB.setEmail(exhibitor.getEmail() );
//			exhibitorDB.setIntro(exhibitor.getIntro() );
//			exhibitorDB.setPhone(exhibitor.getPhone() );

			Integer userId = (Integer) subject.getSession().getAttribute("userId");
			exhibitor.setUserId(userId);
			//修改
			FieldTransfer.transStrToNull(exhibitor);//将传入对象的string类型字段从""转换成null
			if (logger.isInfoEnabled()) {
				logger.info("修改展商信息:" + exhibitor);
			}
			exhibitorService.updateExhibitor(exhibitor);
			result=gson.toJson(new ReplyResult(1,"修改成功!"));
		}
		if (logger.isInfoEnabled()) {
			logger.info("userId:" + exhibitor.getUserId() + "\t修改结果:" + result);
		}
		return result;
	}
	
	/**
	 * 
	 * @Description 设置展商的审核状态status
	 * @return String	：返回json字符串:status:状态（1正常，0出错），如果出错--error:错误信息
	 */
	@RequestMapping(value="/setStatus",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String setStatus( @RequestParam("status") String status) {
		Gson gson = new Gson();
		Subject subject = SecurityUtils.getSubject();

		if (!UserStatus.checkStatus(status)) {
			//如果status不合法
			return gson.toJson(new ReplyResult(0,"status错误!"));
		}

		//新建Exhibitor对象并填充属性
		Exhibitor exhibitor = new Exhibitor();
		Integer userId = (Integer) subject.getSession().getAttribute("userId");
		exhibitor.setId(userId);
		exhibitor.setStatus(status);

		//更新到数据库
		exhibitorService.updateExhibitor(exhibitor);

		return gson.toJson(new ReplyResult(1,"修改成功!"));
	}
	
	/**
	 * 
	 * @Description 展商修改密码
	 * @param password	密码
	 * @return String
	 */
	@RequestMapping(value="/changePass",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String changePass(@RequestParam("password")String password) {
		Gson gson = new Gson();
		Subject subject = SecurityUtils.getSubject();

		if (StringUtils.isEmpty(password) ){
			return gson.toJson(new ReplyResult(0, ExceptionEnums.WrongLength.getMessage()));
		}

		//新建Exhibitor对象并填充属性
		User user = new User();
		Integer userId = (Integer) subject.getSession().getAttribute("userId");
		user.setUserId(userId);
		String username = userService.findUserById(userId).getUsername();
		user.setUsername(username);
		user.setPassword(password);

		//更新密码
		userService.updatePassword(user);

		return gson.toJson(new ReplyResult(1,"修改成功!"));
	}
	
	/**
	 * 
	 * @Description 获取展商记录条数
	 * @return String
	 */
	@RequestMapping(value="/getTotal",produces={"text/html;charset=UTF-8"})
	@ResponseBody
	public String getTotal(@RequestParam("status") String status) {
		Gson gson = new Gson();
		int result = exhibitorService.selectTotal(status);
		return gson.toJson(new ReplyResult(1, String.valueOf(result) ));
	}
}
