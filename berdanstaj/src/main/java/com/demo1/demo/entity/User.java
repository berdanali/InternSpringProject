package com.demo1.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name ="user_role")
@Data
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false,length = 100)
    private String fullname;
    @Column(name="mail", nullable = false, unique = true)
    private String mail;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="role", nullable = false)
    private String role;
    @Column(name = "university" , nullable = false)
    private String University;

}
