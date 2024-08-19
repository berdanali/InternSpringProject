package com.demo1.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_role")
    private User user;
    private String title;
    private String content;
    private Date startDate;
    private Date endDate;





    // Getters and Setters
}
