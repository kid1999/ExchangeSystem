package io.kid1999.esystem.api;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.kid1999.esystem.utils.AddressAndContactWayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

import static io.kid1999.esystem.common.Constants.*;

/**
 * @author kid1999
 * @create 2020-12-29 11:12
 * @description github 第三方登录
 **/
@Slf4j
@RestController
@RequestMapping("/oauth")
@Api(tags = "第三方登录")
public class GithubAuthApi {

    @Resource
    private AddressAndContactWayUtil contactWayUtilUtil;


    @RequestMapping("/login/github")
    @ApiOperation("登录github")
    public void renderAuth(HttpServletResponse response) throws IOException {
        log.info("登录github");


    }


    @RequestMapping("/callback")
    @ApiOperation("github回调")
    public void githubAuthCallback(@RequestParam("code") String code,
                                     @RequestParam("state") String state,
                                     HttpSession session) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("code",code);
        map.put("state",state);
        map.put("redirect_url",GITHUB_CALLBACK_URL);
        map.put("client_id",GITHUB_CLIENT_ID);
        map.put("client_secret",GITHUB_CLIENT_SECRET);

        // 获取access token
        String access_token_str = HttpUtil.post("https://github.com/login/oauth/access_token", map);
        String access_token = access_token_str.split("&")[0].split("=")[1];


        // 获取 用户信息
        String userResult = HttpUtil.get("https://api.github.com/user?access_token=" + access_token);
        JSONObject obj = JSONUtil.parseObj(userResult);

        /**  返回user对象
         * {"gists_url":"https://api.github.com/users/kid1999/gists{/gist_id}",
         * "repos_url":"https://api.github.com/users/kid1999/repos",
         * "following_url":"https://api.github.com/users/kid1999/following{/other_user}",
         * "twitter_username":null,
         * "bio":null,
         * "created_at":"2018-10-20T17:23:34Z",
         * "login":"kid1999",
         * "type":"User",
         * "blog":"",
         * "subscriptions_url":"https://api.github.com/users/kid1999/subscriptions",
         * "updated_at":"2021-03-05T15:13:05Z",
         * "site_admin":false,
         * "company":null,
         * "id":44324159,
         * "public_repos":21,
         * "gravatar_id":"",
         * "email":null,
         * "organizations_url":"https://api.github.com/users/kid1999/orgs",
         * "hireable":null,
         * "starred_url":"https://api.github.com/users/kid1999/starred{/owner}{/repo}",
         * "followers_url":"https://api.github.com/users/kid1999/followers",
         * "public_gists":0,
         * "url":"https://api.github.com/users/kid1999",
         * "received_events_url":"https://api.github.com/users/kid1999/received_events",
         * "followers":2,
         * "avatar_url":"https://avatars.githubusercontent.com/u/44324159?v=4",
         * "events_url":"https://api.github.com/users/kid1999/events{/privacy}",
         * "html_url":"https://github.com/kid1999",
         * "following":0,
         * "name":"hell Xy",
         * "location":null,
         * "node_id":"MDQ6VXNlcjQ0MzI0MTU5"}
         */

        contactWayUtilUtil.findGithubOrSave(obj);
        System.out.println(obj.toString());
    }

}