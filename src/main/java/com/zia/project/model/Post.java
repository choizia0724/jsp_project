package com.zia.project.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String title;
    private String content;
    private String username;
    private Date createdAt;
    public String getFormattedCreatedAt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd HH:mm");
        return sdf.format(createdAt);
    }
}