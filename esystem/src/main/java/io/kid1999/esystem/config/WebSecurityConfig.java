package io.kid1999.esystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author kid1999
 * @create 2021-01-19 16:52
 * @description Spring Security配置
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * PasswordEncoder 密码加密规则，
     * 这里使用的是不加密，推荐使用BCryptPasswordEncoder加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }



    /**
     * 密码授权处理
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 拦截链，
     * 其他接口必须认证后才能访问

     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 关闭跨域
        http.csrf().disable();

        // 授权认证  -- 可用注解代替
        // 此处拦截 -> resource server 再拦截 -> 注解拦截
        http.authorizeRequests()
                .antMatchers("/**").permitAll();



        // 退出登录
//        http.logout()
//                // 退出登录处理
//                .addLogoutHandler()
//                // 退出登录处理 success
//                .logoutSuccessHandler()
//                // 清除session
//                .invalidateHttpSession()


        // 记住我
//        http.rememberMe()
//                // Token失效时间
//                .tokenValiditySeconds(3600)
//                // 属性名
//                .rememberMeParameter("rememberMe")
//                // 登录逻辑
//                .userDetailsService(userDetailService)
//                // 持久层对象
//                .tokenRepository(persistentTokenRepository);



    }

}