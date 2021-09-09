package com.zd.communitity.controller;

import com.zd.communitity.dto.AccessTokenDTO;
import com.zd.communitity.dto.GithubUser;
import com.zd.communitity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizationController {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("callback")
    public String callback(@RequestParam(name ="code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("f94384b2dc5fa4119a72");
        accessTokenDTO.setClient_secret("b870b485ef74964e646b93931264172d32f15a2b");
        accessTokenDTO.setRedirect_uri("http://localhost:10086/callback");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName()+user.getId());
        return "index";
    }
}
