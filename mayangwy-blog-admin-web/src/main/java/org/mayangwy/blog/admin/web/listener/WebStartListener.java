package org.mayangwy.blog.admin.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mayangwy.blog.admin.web.TestMLServlet;
import org.mayangwy.blog.admin.web.TestQuanServlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebStartListener implements ServletContextListener {

    //private final static Logger logger = LogManager.getLogger(WebStartListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //logger.info("123321123321");
        ServletRegistration.Dynamic testQuanServlet = sce.getServletContext().addServlet("TestQuanServlet", TestQuanServlet.class);
        testQuanServlet.addMapping("/quan");
        ServletRegistration.Dynamic testMLServlet = sce.getServletContext().addServlet("TestMLServlet", TestMLServlet.class);
        testMLServlet.addMapping("/quan/");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}