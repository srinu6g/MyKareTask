package com.mykare.in.response;

import com.mykare.in.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private Integer roleId;
    private RoleType roleName;
}
