package io.kid1999.esystem.api;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.kid1999.esystem.dao.UserDao;
import io.kid1999.esystem.utils.AddressAndContactWayUtil;
import lombok.extern.java.Log;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static io.kid1999.esystem.common.Constants.*;

/**
 * @author kid1999
 * @create 2020-12-29 11:12
 * @description github 第三方登录
 **/
@Log
@RestController
@RequestMapping("/oauth")
public class GithubAuthApi {

    @Autowired
    private UserDao userDao;

    @Resource
    private AddressAndContactWayUtil contactWayUtilUtil;

    @RequestMapping("/login/github")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/callback")
    public Object login(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        AuthResponse<AuthUser> response = authRequest.login(callback);
        JSONObject jsonObject = JSONUtil.parseObj(response.getData(), false);
        // 生成随机用户
        contactWayUtilUtil.findGithubOrSave(jsonObject);
        return response;
    }


    @RequestMapping("/test")
    public Object Test(Authentication authentication) {
        return authentication.getDetails();
    }


    // 创建Request
    private AuthRequest getAuthRequest() {
        return new AuthGithubRequest(AuthConfig.builder()
                .clientId(GITHUB_CLIENT_ID)
                .clientSecret(GITHUB_CLIENT_SECRET)
                .redirectUri(GITHUB_CALLBACK_URL)
                .build());
    }
}