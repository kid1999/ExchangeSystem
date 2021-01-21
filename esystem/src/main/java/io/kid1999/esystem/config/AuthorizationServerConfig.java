package io.kid1999.esystem.config;

import io.kid1999.esystem.common.Constants;
import io.kid1999.esystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kid1999
 * @create 2021-01-20 14:26
 * @description 授权服务器 配置
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenStore jwtTokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;


    /**
     * Jwt 和 密码模式需要的配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints){
        // 配置jwt扩展内容
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> list = new ArrayList<>();
        list.add(jwtTokenEnhancer);
        list.add(jwtAccessTokenConverter);
        tokenEnhancerChain.setTokenEnhancers(list);

        endpoints.authenticationManager(authenticationManager)
                // 用户校验策略
                .userDetailsService(userService)
                // token 存储策略
                .tokenStore(jwtTokenStore)
                // 配置accessToken与JWT转换
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(tokenEnhancerChain);
    }

    /**
     * 授权验证信息
     * 验证：
     * client-id 和 client-secret
     * 访问权限scope
     * token有效期
     * 授权模式
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
        // 配置client-id
        .withClient(Constants.CLIENT_ID)
        // 配置client 加密
        .secret(passwordEncoder.encode(Constants.CLIENT_SECRET))
        // 配置 Token 有效期
        .accessTokenValiditySeconds(Constants.TOKEN_EXPIRE_DATE)
        // 申请访问的权限范围
        .scopes("all")
        // 配置授权类型
        .authorizedGrantTypes("refresh_token", "authorization_code","password");
    }

    /**
     * 安全放行规则
     */

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 允许客户端访问 OAuth2 授权接口，否则请求 token 会返回 401
        security.allowFormAuthenticationForClients();
        // 第二行和第三行分别是允许已授权用户访问 checkToken 接口和获取 token 接口
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");

    }
}