package com.exhibition.controller;


import com.exhibition.constants.LoginContants;
import com.exhibition.constants.SessionConstants;
import com.exhibition.enums.RoleList;
import com.exhibition.exceptions.ServiceException;
import com.exhibition.po.Exhibitor;
import com.exhibition.po.User;
import com.exhibition.service.ExhibitorService;
import com.exhibition.service.UserService;
import com.exhibition.utils.FileUtil;
import com.exhibition.utils.RandomUtil;
import com.exhibition.vo.ReplyResult;
import com.exhibition.vo.UserLoginInfo;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;


@Controller
@RequestMapping("/common")
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);

    @Value("${exhibitor.savePath}")
    private String ExhibitorPhotoSavePath;//展商营业执照的保存根路径

    @Value("${exhibitor.visitPath}")
    private String ExhibitorPhotoVisitPath;//展商营业执照的访问根路径

    @Autowired
    private UserService userService;

    @Autowired
    private ExhibitorService exhibitorService;

    @Autowired
    private Producer captchaProducer;


    @RequestMapping(value="/login",method=RequestMethod.GET)
    public String loginForm(Model model){
        model.addAttribute("user", new User());
        return "redirect:/static/login.html";
//        return "login";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
    public String login(User user, @RequestParam("vaild") String vaild
            , @RequestParam(value = "redirectURL", required = false) String redirectURL) throws UnsupportedEncodingException {
        //使用权限工具进行用户登录，登录成功后跳到shiro配置的successUrl中，与下面的return没什么关系！
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();

        session.setAttribute("redirectURL",redirectURL);
        //验证--验证码
        String vaildInSession = (String) subject.getSession().getAttribute(SessionConstants.KAPTCHA_SESSION_KEY);
        //取出验证码后，该验证码作废,设置为null
        subject.getSession().setAttribute(SessionConstants.KAPTCHA_SESSION_KEY, null);
        if (vaildInSession == null || !vaild.equals(vaildInSession)) {
            if (logger.isInfoEnabled()) {
                logger.info("验证码验证错误--input:" + vaild + "\tsession:" + vaildInSession);
            }
            return "redirect:/static/login.html?error=" + java.net.URLEncoder.encode("验证码错误!", "UTF-8");
        }

        //打印登录信息
        if (logger.isInfoEnabled()) {
            logger.info("登录--用户信息为：" + user.getUsername() + "\t" + user.getPassword());
        }
        try {
            //获取session，并且保存用户的用户名信息
            session.setAttribute("username", user.getUsername());

            //通过用户名获取用户id，并放置在session中
            Integer userId = userService.getIdByName(user.getUsername());
            subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
            if (userId != null) {
                if (logger.isInfoEnabled()) {
                    logger.info("用户登录成功--userId=" + userId);
                }
                session.setAttribute("userId", userId);
            }
            return "forward:/common/route";
//            return "login";
        } catch (ServiceException e) {
            logger.info("service层异常:" + e.getMessage());
            return "redirect:/static/login.html?error=" + java.net.URLEncoder.encode(e.getMessage(), "UTF-8");
        } catch (ExcessiveAttemptsException e) {
            logger.info("登录失败多次，账户锁定10分钟");
//            session.setAttribute("message","登录失败多次，账户锁定10分钟");
            return "redirect:/static/login.html?error=" + java.net.URLEncoder.encode("登录失败多次，账户锁定10分钟", "UTF-8");
//            return "login";
        } catch (UnknownAccountException e) {
            logger.info("没有该用户");
//            session.setAttribute("message","没有该用户");
            return "redirect:/static/login.html?error=" + java.net.URLEncoder.encode("没有该用户", "UTF-8");
//            return "redirect:/common/route";
//            return "login";
        } catch (AuthenticationException e) {
            logger.info("用户名或密码错误");
//            session.setAttribute("message","用户名或密码错误");
            return "redirect:/static/login.html?error=" + java.net.URLEncoder.encode("用户名或密码错误", "UTF-8");
        }
    }

    /**
     * 登录后根据角色不同，跳转到不同页面
     * @return
     */
    @RequestMapping(value = "/route")
    public String route(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();

        String url = null;
//        String contextPath = ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath();
        //如果是普通用户，跳转到main.html
        if (subject.hasRole(RoleList.User.getRoleName())) {
            Object redirectURL = session.getAttribute("redirectURL");
            session.setAttribute("redirectURL", null);
            if (redirectURL != null) {
                url = (String) redirectURL;
            } else {
                url = "/static/visitor/index.html";
            }
        } else if (subject.hasRole(RoleList.Exhibitor.getRoleName())) {
            //展商
            url = "/static/exhibitor/exhibitors.html";
        } else if(subject.hasRole(RoleList.Admin.getRoleName())){
            //超级管理员
            url="/static/superAdmin/suAdmin.html";
        }else{
            //管理员跳转路径
            url = "/static/admin/admin.html";
        }
        return "redirect:" + url;
    }

    @RequestMapping(value = "/doLogin/user",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String login(User user,HttpServletRequest request) {
        Gson gson = new Gson();
        if (!"XMLHttpRequest".equalsIgnoreCase((request)
                .getHeader("X-Requested-With"))) {
            return gson.toJson(new ReplyResult(LoginContants.LOGIN_FALLED, "请求方式有误!"));
        }

        logger.info(user);

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        //获取session，并且保存用户的用户名信息
        session.setAttribute("username", user.getUsername());
        //通过用户名获取用户id，并放置在session中
        try {
            Integer userId = userService.getIdByName(user.getUsername());
            session.setAttribute(SessionConstants.USER_ID, userId);
            subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
            if (!subject.hasRole(RoleList.User.getRoleName())) {
                //不是用户
                subject.logout();
                return gson.toJson(new ReplyResult(LoginContants.LOGIN_FALLED, "角色错误"));
            }
            if (userId != null) {
                if (logger.isInfoEnabled()) {
                    logger.info("用户登录成功--userId=" + userId);
                }
                session.setAttribute("userId", userId);
            }
        } catch (ServiceException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e.fillInStackTrace());
            }
            return gson.toJson(new ReplyResult(LoginContants.LOGIN_FALLED, e.getMessage()));
        } catch (DataAccessException e) {
            logger.debug(e);
            return gson.toJson(new ReplyResult(LoginContants.LOGIN_FALLED, "出现错误!"));
        } catch (Exception e) {
            logger.info(e);
            String message = e.getClass().getSimpleName();
            if ("IncorrectCredentialsException".equals(message)) {
                return new Gson().toJson(new ReplyResult(LoginContants.LOGIN_FALLED, "密码错误!"));
            } else if ("UnknownAccountException".equals(message)) {
                return new Gson().toJson(new ReplyResult(LoginContants.LOGIN_FALLED, "账号不存在!"));
            } else if ("LockedAccountException".equals(message)) {
                return new Gson().toJson(new ReplyResult(LoginContants.LOGIN_FALLED, "账号被锁定!"));
            } else {
                return new Gson().toJson(new ReplyResult(LoginContants.LOGIN_FALLED, "未知错误!"));
            }
        }
        return gson.toJson(new ReplyResult(LoginContants.LOGIN_SUCCESS, "登录成功!"));

    }



//    /**
//     * 生成图片验证码并输入到response中
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(value = "/getVaild",method = RequestMethod.GET)
//    public void vaild(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        // 禁止缓存
//        response.setDateHeader("Expires", -1);
//        response.setHeader("Cache-Control", "no-Cache");
//        response.setHeader("pragma", "no-Cache");
//
//        int height = 30;
//        int width = 30 * 4;
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        Graphics2D g = (Graphics2D) image.getGraphics();
//
//        // 1.填充矩形
//        g.setColor(Color.GRAY);
//        g.fillRect(0, 0, width, height);
//
//        // 2.画边框
//        g.setColor(Color.BLACK);
//        g.drawRect(0, 0, width - 1, height - 1);
//
//        // 3.话干扰线
//        g.setColor(Color.RED);
//        for (int i = 0; i < 5; i++) {
//            g.drawLine(RandomUtil.getRandInt(0, width), RandomUtil.getRandInt(0, height),
//                    RandomUtil.getRandInt(0, width), RandomUtil.getRandInt(0, height));
//        }
//        // 4.随机生成字符写到图片上
//        String base = "\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31\u53bb\u5b50\u5f97\u4e5f\u548c\u90a3\u8981\u4e0b\u770b\u5929\u65f6\u8fc7\u51fa\u5c0f\u4e48\u8d77\u4f60\u90fd\u628a\u597d\u8fd8\u591a\u6ca1\u4e3a\u53c8\u53ef\u5bb6\u5b66\u53ea\u4ee5\u4e3b\u4f1a\u6837\u5e74\u60f3\u751f\u540c\u8001\u4e2d\u5341\u4ece\u81ea\u9762\u524d\u5934\u9053\u5b83\u540e\u7136\u8d70\u5f88\u50cf\u89c1\u4e24\u7528\u5979\u56fd\u52a8\u8fdb\u6210\u56de\u4ec0\u8fb9\u4f5c\u5bf9\u5f00\u800c\u5df1\u4e9b\u73b0\u5c71\u6c11\u5019\u7ecf\u53d1\u5de5\u5411\u4e8b\u547d\u7ed9\u957f\u6c34\u51e0\u4e49\u4e09\u58f0\u4e8e\u9ad8\u624b\u77e5\u7406\u773c\u5fd7\u70b9\u5fc3\u6218\u4e8c\u95ee\u4f46\u8eab\u65b9\u5b9e\u5403\u505a\u53eb\u5f53\u4f4f\u542c\u9769\u6253\u5462\u771f\u5168\u624d\u56db\u5df2\u6240\u654c\u4e4b\u6700\u5149\u4ea7\u60c5\u8def\u5206\u603b\u6761\u767d\u8bdd\u4e1c\u5e2d\u6b21\u4eb2\u5982\u88ab\u82b1\u53e3\u653e\u513f\u5e38\u6c14\u4e94\u7b2c\u4f7f\u5199\u519b\u5427\u6587\u8fd0\u518d\u679c\u600e\u5b9a\u8bb8\u5feb\u660e\u884c\u56e0\u522b\u98de\u5916\u6811\u7269\u6d3b\u90e8\u95e8\u65e0\u5f80\u8239\u671b\u65b0\u5e26\u961f\u5148\u529b\u5b8c\u5374\u7ad9\u4ee3\u5458\u673a\u66f4\u4e5d\u60a8\u6bcf\u98ce\u7ea7\u8ddf\u7b11\u554a\u5b69\u4e07\u5c11\u76f4\u610f\u591c\u6bd4\u9636\u8fde\u8f66\u91cd\u4fbf\u6597\u9a6c\u54ea\u5316\u592a\u6307\u53d8\u793e\u4f3c\u58eb\u8005\u5e72\u77f3\u6ee1\u65e5\u51b3\u767e\u539f\u62ff\u7fa4\u7a76\u5404\u516d\u672c\u601d\u89e3\u7acb\u6cb3\u6751\u516b\u96be\u65e9\u8bba\u5417\u6839\u5171\u8ba9\u76f8\u7814\u4eca\u5176\u4e66\u5750\u63a5\u5e94\u5173\u4fe1\u89c9\u6b65\u53cd\u5904\u8bb0\u5c06\u5343\u627e\u4e89\u9886\u6216\u5e08\u7ed3\u5757\u8dd1\u8c01\u8349\u8d8a\u5b57\u52a0\u811a\u7d27\u7231\u7b49\u4e60\u9635\u6015\u6708\u9752\u534a\u706b\u6cd5\u9898\u5efa\u8d76\u4f4d\u5531\u6d77\u4e03\u5973\u4efb\u4ef6\u611f\u51c6\u5f20\u56e2\u5c4b\u79bb\u8272\u8138\u7247\u79d1\u5012\u775b\u5229\u4e16\u521a\u4e14\u7531\u9001\u5207\u661f\u5bfc\u665a\u8868\u591f\u6574\u8ba4\u54cd\u96ea\u6d41\u672a\u573a\u8be5\u5e76\u5e95\u6df1\u523b\u5e73\u4f1f\u5fd9\u63d0\u786e\u8fd1\u4eae\u8f7b\u8bb2\u519c\u53e4\u9ed1\u544a\u754c\u62c9\u540d\u5440\u571f\u6e05\u9633\u7167\u529e\u53f2\u6539\u5386\u8f6c\u753b\u9020\u5634\u6b64\u6cbb\u5317\u5fc5\u670d\u96e8\u7a7f\u5185\u8bc6\u9a8c\u4f20\u4e1a\u83dc\u722c\u7761\u5174\u5f62\u91cf\u54b1\u89c2\u82e6\u4f53\u4f17\u901a\u51b2\u5408\u7834\u53cb\u5ea6\u672f\u996d\u516c\u65c1\u623f\u6781\u5357\u67aa\u8bfb\u6c99\u5c81\u7ebf\u91ce\u575a\u7a7a\u6536\u7b97\u81f3\u653f\u57ce\u52b3\u843d\u94b1\u7279\u56f4\u5f1f\u80dc\u6559\u70ed\u5c55\u5305\u6b4c\u7c7b\u6e10\u5f3a\u6570\u4e61\u547c\u6027\u97f3\u7b54\u54e5\u9645\u65e7\u795e\u5ea7\u7ae0\u5e2e\u5566\u53d7\u7cfb\u4ee4\u8df3\u975e\u4f55\u725b\u53d6\u5165\u5cb8\u6562\u6389\u5ffd\u79cd\u88c5\u9876\u6025\u6797\u505c\u606f\u53e5\u533a\u8863\u822c\u62a5\u53f6\u538b\u6162\u53d4\u80cc\u7ec6";
//        g.setFont(new Font("宋体", Font.BOLD, 20));
//        g.setColor(Color.BLACK);
//        StringBuffer buffer = new StringBuffer();
//        for (int i = 0; i < 4; i++) {
//            double c = RandomUtil.getRandInt(-45, 45) / 180F * Math.PI;
//            g.rotate(c, 5 + width / 4 * i, 22);
//            String s = base.charAt(RandomUtil.getRandInt(0, base.length() - 1)) + "";
//            buffer.append(s);
//            g.drawString(s, 5 + width / 4 * i, 22);
//            g.rotate(-c, 5 + width / 4 * i, 22);
//        }
//
//        if (logger.isInfoEnabled()) {
//            logger.info(buffer.toString());//打印验证码
//        }
//        //放入session中
//        SecurityUtils.getSubject().getSession().setAttribute(VALIDATE_CODE, buffer.toString());
//        // 5.释放资源
//        g.dispose();
//        // 6.利用ImageIO进行输出
//        ImageIO.write(image, "jpg", response.getOutputStream());
//    }

    @RequestMapping(value = "/getVaild")
    @ResponseBody
    public String getVaild(HttpServletRequest request, HttpServletResponse response){
        response.setDateHeader("Expires", -1);// 禁止server端缓存
        // 设置标准的 HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // 设置IE扩展 HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");// 设置标准 HTTP/1.0 不缓存图片
        response.setContentType("image/jpeg");// 返回一个 jpeg 图片，默认是text/html(输出文档的MIMI类型)
        String capText = captchaProducer.createText();// 为图片创建文本

        SecurityUtils.getSubject().getSession().setAttribute(SessionConstants.KAPTCHA_SESSION_KEY, capText);

        BufferedImage bi = captchaProducer.createImage(capText); // 创建带有文本的图片
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            // 图片数据输出
            ImageIO.write(bi, "jpg", out);
            out.flush();

        } catch (IOException e) {
            if (logger.isInfoEnabled()) {
                logger.info(e);
            }
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                if (logger.isInfoEnabled()) {
                    logger.info(e);
                }
            }
        }

        if (logger.isInfoEnabled()) {
            logger.info("Session 验证码是：" + SecurityUtils.getSubject().getSession().getAttribute(SessionConstants.KAPTCHA_SESSION_KEY));
        }
        return null;
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/logout",produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String logout(){
        Gson gson = new Gson();
        SecurityUtils.getSubject().logout();
        return gson.toJson(new ReplyResult(1,"您已安全退出"));
    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        return "403";
    }

    /**
     * 普通用户注册
     * @param user  用户信息：username、password；(creatTime、locked为默认值）
     * @return
     */
    @RequestMapping(value = "/register/user", method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String doRegisterUser(@Valid User user, BindingResult bindingResult) {
        Gson gson = new Gson();

        if (bindingResult.hasErrors()) {
            return gson.toJson(new ReplyResult(1, "数据长度不符合"));
        }

        if (logger.isInfoEnabled()) {
            logger.info(user);
        }

        boolean result = false;
        try {
            result = userService.register(user);
            if (!result) {
                //保存失败，未抛出异常
                logger.error(user.getUsername() + "注册失败");
                return gson.toJson(new ReplyResult(1, "注册失败"));
            }
            //注册成功
            if (logger.isInfoEnabled()) {
                logger.info("注册用户成功:" + user.getUsername());
            }
            return gson.toJson(new ReplyResult(0, "OK"));
        } catch (ServiceException e) {
            String message = e.getExEnums().getMessage();
            if (logger.isDebugEnabled()) {
                logger.debug(user.getUsername() + "\t注册失败：" + message, e);
            }
            return gson.toJson(new ReplyResult(1, message));
        } catch (Exception e) {
            logger.debug(e);
            return gson.toJson(new ReplyResult(1, "注册出现错误，请重试"));
        }
    }

    /**
     * <p>展商注册</p>
     * <p>先保存上传的营业执照图片，使用用户注册的时间截作为文件名</p>
     * <p>如果文件类型不是图片或者保存图片失败，则返回错误信息和状态码status=1，并且删除图片</p>
     * <p>保存成功后，将用户信息和展商信息保存到数据库</p>
     * <p>如果抛出自定义的异常，则返回错误信息，status=1，并删除图片</p>
     * <p>如果未抛出异常但保存失败(返回值为false)，则记录错误</p>
     * @param user  包含基本用户信息
     * @param exhibitor 包含展商的额外信息
     * @param file  上传的营业执照图片
     * @return  返回json字符串，包含状态码status和信息message，注册成功是status=0
     */
    @RequestMapping(value = "/register/exhibitor",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String doRegisterExhibitor(@Validated User user, @Validated Exhibitor exhibitor,BindingResult bindingResult,
                                      @RequestParam("file") MultipartFile file) {
        Gson gson = new Gson();
        if (bindingResult.hasErrors()) {
            return gson.toJson(new ReplyResult(1,"Error"));
        }

        if (logger.isInfoEnabled()) {
            logger.info(String.format("%s\n\t%s\n\t%s","展商注册",user.toString(),exhibitor.toString()));
        }

        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));//文件类型，如.jpg
        //使用注册时间的时间截作为文件名
        fileName = System.currentTimeMillis() + type;
        String savePath = ExhibitorPhotoSavePath + fileName;//文件保存路径
        String visitPath = ExhibitorPhotoVisitPath + fileName;//文件访问路径
        exhibitor.setLicensePhoto(visitPath);//设置访问路径

        File fileToSave = new File(savePath);
        try {
            file.transferTo(fileToSave);
            if (logger.isInfoEnabled()) {
                logger.info("保存文件:" + fileToSave);
            }
            if ( ! FileUtil.isImage(fileToSave)) {
                //如果上传的文件不是图片
                return gson.toJson(new ReplyResult(1,"请上传图片"));
            }
        } catch (IOException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            if (logger.isInfoEnabled()) {
                logger.info(e.getMessage());
            }
            fileToSave.delete();//删除图片
            return gson.toJson(new ReplyResult(1,"保存图片失败"));
        }

        //保存数据到数据库
        boolean result = false;
        try {
            result = exhibitorService.register(user, exhibitor);
            if (!result) {
                //保存失败，未抛出异常
                logger.error(user.getUsername() + "注册失败");
                fileToSave.delete();
                FileUtil.deleteFile(fileToSave);
                return gson.toJson(new ReplyResult(1, "注册失败"));
            }
        } catch (ServiceException e) {
            String message = e.getExEnums().getMessage();
            if (logger.isInfoEnabled()) {
                logger.info(user.getUsername() + "\t注册到数据库失败:" + message, e);
            }
            if (logger.isDebugEnabled()) {
                logger.debug(e);
            }
            fileToSave.delete();
            return gson.toJson(new ReplyResult(1, message));
        }

        //注册成功
        return gson.toJson(new ReplyResult(0,"OK"));
    }


    /**
     * 获取当前游客是否登录的信息,status表示登录信息:0为游客，1为用户
     * @return
     */
    @RequestMapping(value = "/getLoginInfo",method = RequestMethod.GET,produces = {"applivation/json;charset=UTF-8"})
    @ResponseBody
    public String getUserLoginInfo() {
        Subject subject = SecurityUtils.getSubject();
        Gson gson = new Gson();
        if (subject.isAuthenticated()) {
            //如果已经登录
            //TODO  设置默认头像
            UserLoginInfo userLoginInfo=UserLoginInfo.defaultInstance().setStatus("1");
            return gson.toJson(userLoginInfo);
        }

        //未登录，status=0
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setStatus("0");
        return gson.toJson(userLoginInfo);
    }
}
