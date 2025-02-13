package com.mykare.in.entity;

import com.mykare.in.enums.RoleType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "cfss", name = "role_mst")
public class RoleMst  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_id", unique = true)
    private Integer roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private RoleType  roleName;
}