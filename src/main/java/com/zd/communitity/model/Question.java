package com.zd.communitity.model;

import lombok.*;

import java.io.Serializable;
@Data
public class Question implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
}
