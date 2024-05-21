package com.zia.project.model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private String password;

}
