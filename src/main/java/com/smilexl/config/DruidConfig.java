package com.smilexl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: DruidConfig
 * @Description: //TODO
 * @Author: LL
 * @Date: 2019/4/10 14:20
 * @Version: V1.0
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource dataSource(){
        return new DruidDataSource();
    }

    //配置druid数据监控
    //1、配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //设置参数
        Map<String, String> initParams = new HashMap<>();

        //登录后台用户名
        initParams.put("loginUsername","admin");
        //登录后台密码
        initParams.put("loginPassword","123456");
        //允许登录的
        initParams.put("allow","");//默认允许所有
        //拒绝登录的
//        initParams.put("deny","192.168.15.32");

        bean.setInitParameters(initParams);
        return bean;
    }

    //2、配置一个web监控filter
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());

        //设置初始化参数
        Map<String,String> initParams = new HashMap<>();
        //设置不拦截的请求
        initParams.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        //设置拦截所有请求
        bean.setUrlPatterns(Arrays.asList("/*"));

        return  bean;
    }

}
