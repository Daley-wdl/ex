package com.exhibition.controller;

import com.exhibition.enums.ExceptionEnums;
import com.exhibition.enums.RoleList;
import com.exhibition.enums.UserStatus;
import com.exhibition.po.Exhibitor;
import com.exhibition.po.Exhibits;
import com.exhibition.po.User;
import com.exhibition.service.ExhibitorService;
import com.exhibition.service.ExhibitsService;
import com.exhibition.service.UserService;
import com.exhibition.vo.LayuiReplay;
import com.exhibition.vo.ReplyList;
import com.exhibition.vo.ReplyResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlElementRef;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    private static Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ExhibitorService exhibitorService;

    @Autowired
    private ExhibitsService exhibitsService;
    /**
     * 得到所有的user
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value="/getAllUser",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getAllUser(@RequestParam("page") Integer page, @RequestParam("size") Integer pageSize) {

        Gson gson=new Gson();

        List<User> userList=userService.selectUserByRole(RoleList.User.getRoleId(),page,pageSize);
        //如果user不为空返回json
        int count= userList.size();

        if(userList==null){
            return gson.toJson(new ReplyResult(1,"没有用户"));
        }
        return gson.toJson(new LayuiReplay<User>(0,"OK",count,userList));
    }

    /**
     * 根据名字和状态查询
     * @param page
     * @param size
     * @param name
     * @param status
     * @return
     */
    @RequestMapping(value="/searchUser",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String searchUser(@RequestParam("page")Integer page,@RequestParam("size")Integer size,
                             @RequestParam("name")String name,@RequestParam("status")String status){

        Gson gson=new Gson();
        List<User> userList=userService.searchUserByName(page,size,name,status);
        int count=userList.size();
        if(userList==null){
            return gson.toJson(new ReplyResult(0,"查不到结果！"));
        }
        return gson.toJson(new LayuiReplay<User>(0,"OK",count,userList));
    }
    /**
     * 根据id删除user
     * @param users
     * @return
     */
    @RequestMapping("remove")
    @ResponseBody
    public String remove(@RequestParam("users") String users){

        Gson gson=new Gson();

        //将json转换成exhibits类型
        Type type = new TypeToken<List<User>>() {}.getType();
        List<User> rs=gson.fromJson(users, type);

        // 取出该展品
        for(User e:rs){
            User user = userService.findUserById(e.getUserId());
            if (user == null) {
                return gson.toJson(new ReplyResult(0, "展品不存在!"));
            }
            userService.deleteUser(e.getUserId());
            logger.info("删除"+e.getUsername());
        }

        return gson.toJson(new ReplyResult(1,"删除成功"));
    }

    /**
     * 根据名字查询
     * @param username
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/getUserByName", method=RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getUserByName(@RequestParam("username") String username, @RequestParam("page")Integer page,@RequestParam("size")Integer size){

        Gson gson=new Gson();
        User user=userService.findUserByName(username);
        if(user!=null){
            return gson.toJson(user);
        }
        return gson.toJson(new ReplyResult(0,"未找到该用户"));
    }
    /**
     * 解锁/上锁用户
     * @param username
     * @param locked
     */
    @RequestMapping("lockUser")
    @ResponseBody
    public String lockUser(@RequestParam("username") String username, @RequestParam("locked") boolean locked){

        Gson gson=new Gson();
        if(username!=null||username!=""){
            userService.lockUser(username,locked);
            return locked? gson.toJson(new ReplyResult(0,"锁定用户")):gson.toJson(new ReplyResult(0,"解锁用户"));
        }
        return gson.toJson(new ReplyResult(1,"该用户不存在"));
    }

    /*******************************对exhibitor操作**********************************/

    /**
     *	查询所有已审核通过的展商
     * @param request
     * @return
     */
    @RequestMapping(value="/listAllExhibitor",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listAllExhibitor(HttpServletRequest request,
                                   @RequestParam("page")Integer page,@RequestParam("size")Integer pageSize){
        List<Exhibitor> exhibitors = exhibitorService.selectAllExhibitor(page, pageSize, UserStatus.Passed.getStatus());
        int count = exhibitorService.selectTotal(UserStatus.Passed.getStatus());
        return new Gson().toJson(new LayuiReplay<Exhibitor>( 0,"OK",count,exhibitors));
    }

    /**
     *
     * @Descoption 查询所有待审核的展商
     * @return String
     */
    @RequestMapping(value="/listAllCheckingExhibitor",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listAllCheckingExhibitor(HttpServletRequest request,
                                           @RequestParam("page")Integer page,@RequestParam("size")Integer pageSize){
        List<Exhibitor> exhibitors = exhibitorService.selectAllExhibitor(page, pageSize, UserStatus.Waiting.getStatus());
        int count = exhibitorService.selectTotal(UserStatus.Waiting.getStatus());
        return new Gson().toJson(new LayuiReplay<Exhibitor>( 0,"OK",count,exhibitors));

    }

    /**
     *
     * @Description 设置展商的审核状态status
     * @return String	：返回json字符串:status:状态（1正常，0出错），如果出错--error:错误信息
     */
    @RequestMapping(value="/setExhibitorStatus", produces = { "application/json;charset=UTF-8" },method = RequestMethod.GET)
    @ResponseBody
    public String setExhibitorStatus( @RequestParam("exhibitor") String exhibitor,@RequestParam("status") String status) {
        Gson gson = new Gson();
        String result=null;
        if (!UserStatus.checkStatus(status)) {
            //如果status不合法
            return gson.toJson(new ReplyResult(0,"status错误!"));
        }

        //将json转换成exhibits类型
        Type type = new TypeToken<List<Exhibitor>>() {}.getType();
        List<Exhibitor> rs=gson.fromJson(exhibitor, type);

        // 取出该展品
        for(Exhibitor e:rs){
            if(null==exhibitorService.selectByUserId(e.getId())){
                result = gson.toJson(new ReplyResult(0, "对应展品不存在"));
            }
            Exhibitor extor = new Exhibitor();
            extor.setUserId(e.getUserId());
            extor.setStatus(status);
            exhibitorService.updateExhibitor(extor);
            logger.info("通过审核："+e.getRealName());
        }
        return gson.toJson(new ReplyResult(1,"修改成功!"));
    }

    /**
     *
     * @Descoption 查询所有未通过审核的展商
     * @param request
     * @return String
     */
    @RequestMapping(value="/listAllFailExhibitor",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String listAllFailExhibitor(HttpServletRequest request,
                                       @RequestParam("page")Integer page,@RequestParam("size")Integer pageSize){
//		List<Exhibitor> exhibitors = exhibitorDao.getAllCheckingExhibitor();
        List<Exhibitor> exhibitors = exhibitorService.selectAllExhibitor(page, pageSize, UserStatus.Failed.getStatus());
        int count = exhibitorService.selectTotal(UserStatus.Failed.getStatus());
        return new Gson().toJson(new LayuiReplay<Exhibitor>( 0,"OK",count,exhibitors));
    }

    /**
     * 根据userid删除exhibitor
     * @param exhibitor
     * @return
     */
    @RequestMapping(value="/removeExhibitor", produces = { "application/json;charset=UTF-8" },method = RequestMethod.GET)
    @ResponseBody
    public String removeExhibitor(@RequestParam("exhibitor") String exhibitor){
        Gson gson=new Gson();

        //将json转换成exhibits类型
        Type type = new TypeToken<List<Exhibitor>>() {}.getType();
        List<Exhibitor> rs=gson.fromJson(exhibitor, type);

        // 取出该展品
        for(Exhibitor e:rs){
            Exhibitor extor = exhibitorService.selectByUserId(e.getUserId());
            if (extor == null) {
                return gson.toJson(new ReplyResult(0, "展商不存在!"));
            }
            exhibitorService.deleteExhibitor(e.getUserId());
            logger.info("删除"+e.getRealName());
        }

        return gson.toJson(new ReplyResult(1,"删除成功"));
    }

    @RequestMapping(value="/searchExhibitorByName", produces = { "application/json;charset=UTF-8" },method = RequestMethod.GET)
    @ResponseBody
    public String searchExhibitorByName(@RequestParam("page")Integer page,@RequestParam("size")Integer size,
                                        @RequestParam("name")String name,@RequestParam("status")String status){

        Gson gson=new Gson();
        List<Exhibitor> exhibitorList=exhibitorService.searchExhibitorByName(page,size,name,status);
        int count=exhibitorList.size();
        if(exhibitorList==null){
            return gson.toJson(new ReplyResult(0,"查不到结果！"));
        }
        return gson.toJson(new LayuiReplay<Exhibitor>(0,"OK",count,exhibitorList));
    }
    /*******************************对exhibitor操作**********************************/


    /*******************************对exhibits操作**********************************/
    /**
     *
     * 获取所有审核不通过的展品列表，由上架时间进行排序
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/selectFailedExhibits",method=RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String selectFailedExhibits(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Gson gson = new Gson();
        List<Exhibits> exhibits = exhibitsService.getExhibits(page, size, UserStatus.Failed.getStatus());
        int count = exhibitsService.getCountByStatus(UserStatus.Failed.getStatus());
        String resultJson = gson.toJson(new LayuiReplay<Exhibits>(0,"OK",count, exhibits));
        logger.info(resultJson);
        return resultJson;
    }

    /**
     *
     * 获取所有审核通过的展品列表，由上架时间进行排序
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/selectAllExhibits",method=RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String selectExhibits(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Gson gson = new Gson();
        List<Exhibits> exhibits = exhibitsService.getExhibits(page, size, UserStatus.Passed.getStatus());
        int count = exhibitsService.getCountByStatus(UserStatus.Passed.getStatus());
        String resultJson = gson.toJson(new LayuiReplay<Exhibits>(0,"OK", count,exhibits));
         logger.info(resultJson);
        return resultJson;
    }

    /**
     * 根据名字和状态查询展品
     * @param page
     * @param size
     * @param name
     * @param status
     * @return
     */
    @RequestMapping(value="/searchExhibitsByName", produces = { "application/json;charset=UTF-8" },method = RequestMethod.GET)
    @ResponseBody
    public String searchExhibitsByName(@RequestParam("page")Integer page,@RequestParam("size")Integer size,
                                       @RequestParam("name")String name,@RequestParam("status")String status){
        Gson gson=new Gson();
        List<Exhibits> exhibitsList=exhibitsService.searchExhibitsByName(page,size,name,status);
        int count=exhibitsList.size();
        if(count==0){
            return gson.toJson(new ReplyResult(0,"查不到结果！"));
        }
        return gson.toJson(new LayuiReplay<Exhibits>(0,"OK",count,exhibitsList));
    }

    /**
     * @Description 管理员通过商品id删除商品
     * @param exhibits
     * @return
     */
    @RequestMapping(value="/removeExhibits",produces={"application/json;charset=UTF-8"},method=RequestMethod.GET)
    @ResponseBody
    public String removeExhibits(@RequestParam("exhibits")String exhibits){

        Gson gson=new Gson();
        Subject subject= SecurityUtils.getSubject();
        Integer userId = (Integer) subject.getSession().getAttribute("userId");

        // 验证权限
        if (!subject.hasRole(RoleList.Manager.getRoleName())) {
            return gson.toJson(new ReplyResult(0, "没有该权限!"));
        }

        //将json转换成exhibits类型
        Type type = new TypeToken<List<Exhibits>>() {}.getType();
        List<Exhibits> rs=gson.fromJson(exhibits, type);

        // 取出该展品
        for(Exhibits e:rs){
            Exhibits exts = exhibitsService.getExhibitsById(e.getId());
            if (exts == null) {
                return gson.toJson(new ReplyResult(0, "展品不存在!"));
            }
            exhibitsService.delete(e.getId());
            logger.info("删除"+e.getExhibitsName());
        }

        return gson.toJson(new ReplyResult(1,"删除成功"));
    }

    /**
     *
     * 获取所有待审核的展品列表，由上架时间进行排序
     *
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/selectCheckingExhibits",method=RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String selectCheckingExhibits(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        Gson gson = new Gson();
        List<Exhibits> exhibits = exhibitsService.getExhibits(page, size, UserStatus.Waiting.getStatus());
        int count = exhibitsService.getCountByStatus(UserStatus.Waiting.getStatus());
        String resultJson = gson.toJson(new LayuiReplay<Exhibits>(0,"OK", count,exhibits));
        logger.info(resultJson);
        return resultJson;
    }

    /**
     *
     * @Description 设置展品的审核状态（ajax请求，返回json字符串）
     * @param exhibits
     *            :展品的id
     * @param status
     *            :展品审核状态:1--通过。0--待审核，-1--未通过
     * @return String
     *         ：json格式--成功:"status":"1";错误:"status":"0","error":"errorMsg..."
     */
    @RequestMapping(value = "/setExhibitsStatus", produces = { "application/json;charset=UTF-8" },method = RequestMethod.GET)
    @ResponseBody
    public String setExhibitsStatus(@RequestParam("exhibits") String exhibits,
                                    @RequestParam("status") String status) {
        String result = null;
        // 生成json
        Gson gson = new Gson();

        if (!UserStatus.checkStatus(status)) {
            //检查status是否合法
            result = gson.toJson(new ReplyResult(0, ExceptionEnums.WrongStatus.getMessage()));
        }

        //将json转换成exhibits类型
        Type type = new TypeToken<List<Exhibits>>() {}.getType();
        List<Exhibits> rs=gson.fromJson(exhibits, type);

        // 取出该展品
        for(Exhibits e:rs){
            if(null==exhibitsService.getExhibitsById(e.getId())){
                result = gson.toJson(new ReplyResult(0, "对应展品不存在"));
            }
            Exhibits exts = new Exhibits();
            exts.setId(e.getId());
            exts.setStatus(status);
            exhibitsService.update(exts);
        }
        result = gson.toJson(new ReplyResult(1, "修改成功!"));
        return result;
    }

    /*******************************对exhibits操作**********************************/

}
