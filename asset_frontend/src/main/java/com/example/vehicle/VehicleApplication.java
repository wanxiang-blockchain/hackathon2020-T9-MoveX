package com.example.vehicle;

import com.example.vehicle.filter.HeaderFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class VehicleApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleApplication.class, args);
    }
    /**
     * 配置跨域访问的过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean registerFilter(){
        FilterRegistrationBean bean=new FilterRegistrationBean();
        bean.addUrlPatterns("/*");
        bean.setFilter(new HeaderFilter());
        return bean;
    }
}
