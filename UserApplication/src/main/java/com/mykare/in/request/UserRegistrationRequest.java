package com.mykare.in.request;

import com.mykare.in.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {

    private String name;
    private String email;
    private String gender;
    private String password;
    private RoleType roleName;


}