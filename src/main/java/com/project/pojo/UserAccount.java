package com.project.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {
    private Integer userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String securityPhrase;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
