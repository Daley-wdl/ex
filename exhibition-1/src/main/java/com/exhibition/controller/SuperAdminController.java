package com.exhibition.controller;

import com.exhibition.enums.ExceptionEnums;
import com.exhibition.enums.RoleList;
import com.exhibition.enums.UserStatus;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.User;
import com.exhibition.service.UserService;
import com.exhibition.vo.LayuiReplay;
import com.exhibition.vo.ReplyResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Type;
import java.util.List;

@Controller
@RequestMapping("superAdmdin")
public class SuperAdminController {

    private static Logger logger = Logger.getLogger(SuperAdminController.class);

    @Autowired
    private UserService userService;

    /**
     * 根据角色得到所有的用户
     * @param page
     * @param size
     * @param role
     * @return
     */
    @RequestMapping(value = "/getAllUser", produces = { "application/json;charset=UTF-8" },method = RequestMethod.GET)
    @ResponseBody
    public String getAllUser(@RequestParam("page") int page, @RequestParam("size") int size,@RequestParam("role") Integer role) {

        Gson gson=new Gson();

        List<User> user=userService.selectUserByRole(role,page,size);
        int count=user.size();
        //如果admin不为空返回json
        if(user==null){
            return gson.toJson(new ReplyResult(0,"没有用户"));
        }
        return gson.toJson(new LayuiReplay<User>(0,"OK",count,user));
    }

    /**
     * 根据id删除admin
     * @param admin
     * @return
     */
    @RequestMapping("removeAdmin")
    @ResponseBody
    public String removeAdmin(@RequestParam("admin") String admin){

        Gson gson=new Gson();

        Type type=new TypeToken<List<User>>(){}.getType();
        List<User> rs=gson.fromJson(admin,type);

        for(User u:rs){
            User user = userService.findUserById(u.getUserId());
            if (user == null) {
                return gson.toJson(new ReplyResult(0, "用户不存在!"));
            }
            userService.deleteUser(u.getUserId());
            logger.info("删除"+u.getUsername());
        }

        return gson.toJson(new ReplyResult(1,"删除成功"));
    }

    /**
     * 设置用户的角色
     * @param user
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/setAdminRole", produces = { "application/json;charset=UTF-8" },method = RequestMethod.GET)
    @ResponseBody
    public String setAdminRole(@RequestParam("users") String user,@RequestParam("roleId") Integer roleId){

        String result = null;
        // 生成json
        Gson gson = new Gson();

        if (!RoleList.checkStatus(roleId)) {
            //检查roleId是否合法
            result = gson.toJson(new ReplyResult(0, ExceptionEnums.WrongStatus.getMessage()));
        }

        //将json转换成User类型
        Type type = new TypeToken<List<User>>() {}.getType();
        List<User> rs=gson.fromJson(user, type);

        // 取出该展品
        for(User u:rs){
            if(null==userService.findUserById(u.getUserId())){
                result = gson.toJson(new ReplyResult(0, "对应用户不存在"));
            }
            userService.setUserRole(u.getUserId(),roleId);
            logger.info("通过"+u.getUsername());
        }
        result = gson.toJson(new ReplyResult(1, "修改成功!"));
        return result;
    }

    /**
     * 根据名字和角色查询用户
     * @param page
     * @param size
     * @param name
     * @param status
     * @param roleId
     * @return
     */
    @RequestMapping(value="/searchUser",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String searchUser(@RequestParam("page")Integer page,@RequestParam("size")Integer size,
                             @RequestParam("name")String name,@RequestParam("status")String status,@RequestParam("roleId")Integer roleId){

        Gson gson=new Gson();
        List<User> userList=userService.searchUserByNameAndRole(page,size,name,status,roleId);
        int count=userList.size();
        if(userList==null){
            logger.info("查不到结果");
            return gson.toJson(new ReplyResult(0,"查不到结果！"));
        }

        return gson.toJson(new LayuiReplay<User>(0,"OK",count,userList));
    }
}
