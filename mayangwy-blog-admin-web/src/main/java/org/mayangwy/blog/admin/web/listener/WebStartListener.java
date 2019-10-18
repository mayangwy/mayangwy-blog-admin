package org.mayangwy.blog.admin.web.listener;

import lombok.extern.slf4j.Slf4j;
import org.mayangwy.blog.admin.common.jdbc.DruidDataSourceBuilder;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@WebListener
@Slf4j
public class WebStartListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //logger.info("123321123321");
        /*ServletRegistration.Dynamic testQuanServlet = sce.getServletContext().addServlet("TestQuanServlet", TestQuanServlet.class);
        testQuanServlet.addMapping("/quan");
        ServletRegistration.Dynamic testMLServlet = sce.getServletContext().addServlet("TestMLServlet", TestMLServlet.class);
        testMLServlet.addMapping("/quan/");*/
        InputStream inputStream = sce.getServletContext().getResourceAsStream("WEB-INF/classes/application.properties");
        //InputStream inputStream = sce.getServletContext().getResourceAsStream("/application.properties");
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            Set<String> sets = properties.stringPropertyNames();
            log.info("---------- properties文件信息打印开始 ----------");
            sets.forEach(item -> {
                log.info("properties : key({}) : value({}) : length({})", item, properties.getProperty(item), properties.getProperty(item).length());
            });
            log.info("---------- properties文件信息打印结束 ----------");
            Map<String, String> dataSourceConfigMap = new HashMap<>();
            sets.forEach(item -> {
               if(item.startsWith("jdbc.config")){
                   dataSourceConfigMap.put(item.substring("jdbc.config.".length()), properties.getProperty(item));
               }
            });
            try {
                DruidDataSourceBuilder.createDataSource(dataSourceConfigMap);
                DataSource dataGet = DruidDataSourceBuilder.getDataSource();
                //log.info(dataGet.getConnection().toString());
            } catch (Exception e) {
                System.exit(1);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}