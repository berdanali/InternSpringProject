package com.demo1.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
// controller ve service katmanları userdto ile haberleşecek

@Data
public class UserDto {
    // kullanıcıya dönmesini istediğimiz veriler
    private Long id;
    private String fullname;
    private String mail;
    private String password;
    private String role;
    private Date createdDate;
    private Date updatedAt;
    private String University;
}
