package com.zd.communitity.controller;

import com.zd.communitity.dto.AccessTokenDTO;
import com.zd.communitity.dto.GithubUser;
import com.zd.communitity.mapper.UserMapper;
import com.zd.communitity.model.User;
import com.zd.communitity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizationController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("callback")
    public String callback(@RequestParam(name ="code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubuser = githubProvider.getUser(accessToken);
        if (githubuser!=null){
            //登录成功，写入session和cookie
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubuser.getName());
            user.setAccountId(String.valueOf(githubuser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
            Cookie cookie = new Cookie("token",token);
            response.addCookie(cookie);
//            request.getSession().setAttribute("user",githubuser);
            return "redirect:/";
        }else {
            //登录失败，返回首页
            return "redirect:/";
        }
    }
}
