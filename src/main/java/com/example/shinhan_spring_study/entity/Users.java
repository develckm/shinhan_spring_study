package com.example.shinhan_spring_study.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "USERS")
@Data
public class Users {
    @Id
    String uId;
    String pw;
    String name;
    String phone;
    String imgPath;
    String email;
//    post_time
//            birth
//    gender
//            address
//    detail_address
//            permission
//    status
//            email_check_code
}
