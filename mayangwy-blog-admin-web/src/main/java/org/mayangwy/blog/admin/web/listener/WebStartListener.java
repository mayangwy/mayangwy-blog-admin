package org.mayangwy.blog.admin.web.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

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
        //InputStream inputStream = sce.getServletContext().getResourceAsStream("WEB-INF/classes/application.properties");

        //只需要加载ServiceFactory即可
        //是否可以扫描包，判断注解是否加载，spring扫描过的类是否执行静态代码块
        //Class.forName("org.mayangwy.blog.admin.common.jdbc.QueryRunnerBuilder");
        //Class.forName("org.mayangwy.blog.admin.dao.DaoFactory");

        try {
            Class.forName("org.mayangwy.blog.admin.service.ServiceFactory");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}