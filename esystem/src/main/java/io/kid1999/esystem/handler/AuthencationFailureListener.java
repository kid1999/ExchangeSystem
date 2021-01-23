package io.kid1999.esystem.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.*;
import org.springframework.stereotype.Component;

/**
 * @author kid1999
 * @create 2021-01-21 18:09
 * @description 认证失败 监听器
 **/
@Slf4j
@Component
public class AuthencationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {
    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        if (event instanceof AuthenticationFailureBadCredentialsEvent) {
            //提供的凭据是错误的，用户名或者密码错误
            log.error("提供的凭据是错误的，用户名或者密码错误");
        } else if (event instanceof AuthenticationFailureCredentialsExpiredEvent) {
            //验证通过，但是密码过期
            log.error("验证通过，但是密码过期");
        } else if (event instanceof AuthenticationFailureDisabledEvent) {
            //验证过了但是账户被禁用
            log.error("验证过了但是账户被禁用");
        } else if (event instanceof AuthenticationFailureExpiredEvent) {
            //验证通过了，但是账号已经过期
            log.error("验证通过了，但是账号已经过期");
        } else if (event instanceof AuthenticationFailureLockedEvent) {
            //账户被锁定
            log.error("账户被锁定");
        } else if (event instanceof AuthenticationFailureProviderNotFoundEvent) {
            //配置错误，没有合适的AuthenticationProvider来处理登录验证
            log.error("配置错误，没有合适的AuthenticationProvider来处理登录验证");
        } else if (event instanceof AuthenticationFailureProxyUntrustedEvent) {
            //代理不受信任，用于Oauth、CAS这类三方验证的情形，多属于配置错误
            log.error("代理不受信任，用于Oauth、CAS这类三方验证的情形，多属于配置错误");
        } else if (event instanceof AuthenticationFailureServiceExceptionEvent) {
            //其他任何在AuthenticationManager中内部发生的异常都会被封装成此类
            log.error("其他AuthenticationManager错误");
        }
    }
}