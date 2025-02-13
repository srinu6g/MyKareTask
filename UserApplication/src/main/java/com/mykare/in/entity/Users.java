package com.mykare.in.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(schema = "cfss", name = "users")
public class Users {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;

    @Email(message = "Please provide a valid email address.")
    @NotBlank(message = "Email is required and cannot be empty.")
    @Column(name = "email", unique = true, nullable = false)
    private String email;


    @Column(name="geneder")
    private String gender;

    @Column(name="password")
    private  String password;

    @ManyToOne
    @JoinColumn(name = "role_id",referencedColumnName = "role_id")
    private RoleMst role;

    @Column(name="country")
    private String country;

    @Column(name="ip_addr")
    private String ipAddr;

    @Column(name="created_at")
    private Timestamp createdAt;

    @Column(name="updated_at")
    private Timestamp updatedAt;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="updated_by")
    private String updatedBy;



    
    
    
}