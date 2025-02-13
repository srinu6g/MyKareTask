package com.mykare.in.response;

import com.mykare.in.entity.RoleMst;
import com.mykare.in.entity.Users;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private String gender;
    private boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String createdBy;
    private String updatedBy;
    private  RoleResponse role;

    public UserResponseDto(Users userData) {
        this.id = userData.getId();
        this.name = userData.getName();
        this.email = userData.getEmail();
        this.gender = userData.getGender();
        this.createdAt = userData.getCreatedAt();
        this.updatedAt = userData.getUpdatedAt();
        this.createdBy = userData.getCreatedBy();
        this.updatedBy = userData.getUpdatedBy();

        if (userData.getRole() != null) {
            this.role = new RoleResponse();
            this.role.setRoleId(userData.getRole().getRoleId());
            this.role.setRoleName(userData.getRole().getRoleName());
        }
    }



}
