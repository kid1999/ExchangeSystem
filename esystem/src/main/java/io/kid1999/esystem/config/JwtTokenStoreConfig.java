package io.kid1999.esystem.config;

import io.kid1999.esystem.common.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author kid1999
 * @create 2021-01-20 14:05
 * @description  JWTToken 配置
 **/
@Configuration
public class JwtTokenStoreConfig {

    @Bean
    public TokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        // 配置JWT 使用的秘钥
        accessTokenConverter.setSigningKey(Constants.TOKEN_SECRET);
        accessTokenConverter.setVerifierKey(Constants.TOKEN_SECRET);
        return accessTokenConverter;
    }

    /**
     * jwt 内容扩展
     */
    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer(){
        return new JwtTokenEnhancer();
    }
}