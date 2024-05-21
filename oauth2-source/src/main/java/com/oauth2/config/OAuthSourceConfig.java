package com.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * description :
 *
 * @author kunlunrepo
 * date :  2024-05-20 15:45
 */
@Configuration
@EnableResourceServer
public class OAuthSourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    /**
     * 指定token持久化策略
     * 类型：InMemoryTokenStore-内存；Redis-redis；JdbcTokenStore-数据库
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 配置资源ID和存储方案
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId("product_api")
                .tokenStore(tokenStore());
    }

    /**
     * 配置请求类型访问资源权限
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 指定不同请求方式访问资源所有需要的权限
                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")
                .and()
                .headers().addHeaderWriter((request, response) -> {
                    // 允许跨域
                    response.addHeader("Access-Control-Allow-Origin", "*");
                    // 如果是跨域的预检请求，则需向下传递请求头信息
                    if (request.getMethod().equals("OPTIONS")) {
                        response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
                    }
                });
    }
}
