package org.mayangwy.blog.admin.web.listener;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.util.EnumSet;

@WebListener
@Slf4j
public class WebStartListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //System.exit(1);
        //logger.info("123321123321");
        /*ServletRegistration.Dynamic testQuanServlet = sce.getServletContext().addServlet("TestQuanServlet", TestQuanServlet.class);
        testQuanServlet.addMapping("/quan");
        ServletRegistration.Dynamic testMLServlet = sce.getServletContext().addServlet("TestMLServlet", TestMLServlet.class);
        testMLServlet.addMapping("/quan/");*/
        FilterRegistration.Dynamic druidFilter = sce.getServletContext().addFilter("DruidFilter", WebStatFilter.class);
        druidFilter.setInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,*.jsp,/druid/*,/download/*");
        druidFilter.setInitParameter("sessionStatMaxCount", "2000");
        druidFilter.setInitParameter("sessionStatEnable", "true");
        druidFilter.setInitParameter("principalSessionName", "session_user_key");
        druidFilter.setInitParameter("profileEnable", "true");

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST,
                                                        DispatcherType.INCLUDE,
                                                        DispatcherType.ASYNC,
                                                        DispatcherType.FORWARD,
                                                        DispatcherType.ERROR);
        druidFilter.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

        ServletRegistration.Dynamic druidStatViewServlet = sce.getServletContext().addServlet("DruidStatViewServlet", StatViewServlet.class);
        druidStatViewServlet.setInitParameter("resetEnable", "true");
        druidStatViewServlet.setInitParameter("loginUsername", "druid");
        druidStatViewServlet.setInitParameter("loginPassword", "druid");

        druidStatViewServlet.addMapping("/druid/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}