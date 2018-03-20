package com.exhibition.shiro;

import com.exhibition.constants.LoginContants;
import com.exhibition.vo.ReplyResult;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

@SuppressWarnings("JavaDoc")
public class ExtendFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger log = Logger.getLogger(FormAuthenticationFilter.class);

    /**
     * 表示当访问拒绝时,与父类处理相同
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if(this.isLoginRequest(request, response)) {
            if(this.isLoginSubmission(request, response)) {
                if(log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }

                return this.executeLogin(request, response);
            } else {
                if(log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }

                return true;
            }
        } else {
            if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest)request)
                    .getHeader("X-Requested-With"))) {
                // ajax请求
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                PrintWriter out = httpServletResponse.getWriter();
                out.println(new Gson().toJson(new ReplyResult(LoginContants.LOGIN_FALLED, "请先登录!")));
                out.flush();
                out.close();
                return false;
            }
            if(log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [" + this.getLoginUrl() + "]");
            }

            this.saveRequestAndRedirectToLogin(request, response);
            return false;
        }
    }

    /**
     * 当登录成功
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        log.info("登录成功");

        if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest
                .getHeader("X-Requested-With"))) {// 不是ajax请求
            log.info("XMLHttpRequest...");
            issueSuccessRedirect(request, response);
        } else {
            httpServletResponse.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = httpServletResponse.getWriter();
            out.println(new Gson().toJson(new ReplyResult(LoginContants.LOGIN_SUCCESS, "登录成功!")));
            out.flush();
            out.close();
        }
        return false;
    }

    /**
     * 当登录失败
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request)
                .getHeader("X-Requested-With"))) {// 不是ajax请求
            setFailureAttribute(request, e);
            return true;
        }
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            String message = e.getClass().getSimpleName();
            if ("IncorrectCredentialsException".equals(message)) {
                out.println(new Gson().toJson(new ReplyResult(LoginContants.LOGIN_FALLED, "密码错误!")));
            } else if ("UnknownAccountException".equals(message)) {
                out.println(new Gson().toJson(new ReplyResult(LoginContants.LOGIN_FALLED, "账号不存在!")));
            } else if ("LockedAccountException".equals(message)) {
                out.println(new Gson().toJson(new ReplyResult(LoginContants.LOGIN_FALLED, "账号被锁定!")));
            } else {
                out.println(new Gson().toJson(new ReplyResult(LoginContants.LOGIN_FALLED, "未知错误!")));
            }
            out.flush();
            out.close();
        } catch (IOException el) {
            if (log.isInfoEnabled()) {
                log.info(el);
            }
        }
        return false;
    }

}
