package com.zd.communitity.model;

import lombok.*;

import java.io.Serializable;
@Data
public class User implements Serializable {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    //private String avatarUrl;
}
