package com.project.pojo;

import com.project.common.constants.AccountStatus;
import com.project.common.constants.UserRoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    private Integer userId;
    private UserRoleType userRoleType;
    private AccountStatus accountStatus;
}
