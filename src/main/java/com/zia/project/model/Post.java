package com.zia.project.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

@Document(collection = "posts")
public class Post {
    @Id
    @Getter
    private String id;
    @Setter
    @Getter
    private String title;
    @Setter
    @Getter
    private String content;
    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private Date createdAt;
    public String getFormattedCreatedAt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd HH:mm");
        return sdf.format(createdAt);
    }
}