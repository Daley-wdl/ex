package com.exhibition.shiro;

import com.exhibition.vo.ReplyResult;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * 自定义Filter，继承自shiro的AuthorizationFilter
 * 主要用于前端用ajax请求时，如果session已经过期，返回json字符串而不是302重定向
 */
public class OwnRoleAuthorizationFilter extends AdviceFilter {

    private static Logger logger = Logger.getLogger(OwnRoleAuthorizationFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Integer userId = (Integer) httpServletRequest.getSession().getAttribute("userId");
        if (null == userId && httpServletRequest.getRequestURI().indexOf("/login") == -1) {
            String requestedWith = httpServletRequest.getHeader("X-Requested-With");
            if (StringUtils.isNotEmpty(requestedWith) && StringUtils.equals(requestedWith, "XMLHttpRequest")) {//如果是ajax返回指定数据

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(new ReplyResult(-1, "您尚未登录或登录时间过长,请重新登录或刷新页面!")));
                return false;
            } else {//不是ajax进行重定向处理
                if (logger.isInfoEnabled()) {
                    logger.info("未登录，返回login页面");
                }

//                httpServletResponse.sendRedirect("/login/local");
                WebUtils.issueRedirect(request,response,"/static/login.html");
                return false;
            }
        }
        return true;
    }
}