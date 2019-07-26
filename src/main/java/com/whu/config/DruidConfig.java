package com.whu.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

@Configuration
public class DruidConfig
{
    /**
     * 配置Druid连接池数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid()
    {
        DruidDataSource druidDataSource = new DruidDataSource();
        List filterList = new ArrayList<>();
        filterList.add(wallFilter());
        druidDataSource.setProxyFilters(filterList);

        return druidDataSource;
    }

    /**
     * 后台管理druid的servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet()
    {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>();
        initParams.put("loginUsername","chris");//登录druid监控的账户
        initParams.put("loginPassword","164815");//登录druid监控的密码
        initParams.put("allow","");//默认就是允许所有访问
        //initParams.put("deny","192.168.15.21");//黑名单的IP

        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * Web后台过滤规则的配置
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter()
    {

        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        //拦截所有请求
        bean.setUrlPatterns(Arrays.asList("/*"));

        //下列请求可访问
        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        bean.setInitParameters(initParams);
        return  bean;
        /*
        return new FilterRegistrationBean(new Filter() {
            public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                    throws IOException, ServletException
            {
                HttpServletRequest request = (HttpServletRequest) req;
                HttpServletResponse response = (HttpServletResponse) res;
                String method = request.getMethod();
                // this origin value could just as easily have come from a database
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, OPTIONS");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Headers", "Accept, Origin, X-Requested-With, Content-Type,Last-Modified,device,token");
                if ("OPTIONS".equals(method)) {//检测是options方法则直接返回200
                    response.setStatus(HttpStatus.OK.value());
                } else {
                    chain.doFilter(req, res);
                }
            }

            public void init(FilterConfig filterConfig) {
            }

            public void destroy() {
            }
        });
        */
    }


    /**
     * 配置允许批量SQL
     * @return
     */
    @Bean
    public WallFilter wallFilter()
    {

        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig());
        return wallFilter;
    }

    @Bean
    public WallConfig wallConfig()
    {
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);
        config.setNoneBaseStatementAllow(true);
        return config;
    }
}
