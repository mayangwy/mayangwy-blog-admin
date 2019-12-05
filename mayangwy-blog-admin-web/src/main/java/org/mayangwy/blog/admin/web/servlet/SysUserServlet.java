package org.mayangwy.blog.admin.web.servlet;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.mayangwy.blog.admin.dao.po.SysUser;
import org.mayangwy.blog.admin.service.SysUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "SysUserServlet", urlPatterns = {"/sys/user/*"}, loadOnStartup = 1)
@Slf4j
public class SysUserServlet extends HttpServlet {

    public static final String URL_PRE = "/sys/user/";

    private SysUserService sysUserService;

    @Override
    public void init() throws ServletException {
        super.init();
        sysUserService = Singleton.get(SysUserService.class);

        log.info("org.mayangwy.blog.admin.web.servlet.SysUserServlet init success !!!");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = ServletUtil.getBody(request);
        SysUser sysUser = JSON.parseObject(body, SysUser.class);
        String servletPath = request.getRequestURI();
        log.info(request.getContextPath());
        log.info(request.getServletPath());
        log.info(request.getRequestURL().toString());
        String endPath = StrUtil.removePrefix(servletPath, URL_PRE);

        Map<String, Object> resultMap = Maps.newLinkedHashMap();
        resultMap.put("respFlag", true);
        switch (endPath) {
            case "insert" :
            case "update" :
                sysUserService.save(sysUser);
                break;
            case "delete" :
                sysUserService.delete(sysUser.getId());
                break;
            case "find" :
                List<SysUser> sysUsers = sysUserService.find(sysUser);
                resultMap.put("data", sysUsers);
                break;
            default :
                resultMap.put("respFlag", false);
                resultMap.put("respMsg", "未找到相关路径！！！");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
        response.getWriter().write(JSON.toJSONString(resultMap));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}