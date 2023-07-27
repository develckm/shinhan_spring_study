package com.example.shinhan_spring_study.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
//어노테이션 : 컴파일실 검사,자동완성
//스프링 : 구현의 추상화 -> 자동완성 (어노테이션)
@Entity // spring jpa 컨테이너가 접속한 db의 테이블을 맵핑할 때 사용
//@Component>@Entity (스프링 컨테이너가 관리하는 객체)
@Table(name = "BOARDS")
@Getter@Setter@ToString
public class Boards {
    public enum StatusType {
        PUBLIC, PRIVATE, REPORT, BLOCK
        }
    //jpa entity 를 기반으로 insert 나 update 쿼리를 생성할 때
    //어떤 필드를 수정 삭제에 제외할 건 명시해야한다.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "b_id",insertable = false, updatable = false)
    private int bId;//b_id; pk generate key(auto increment)
    @Column(name="u_id",updatable = false)
    private String uId;//u_id; fk Users.u_id
    @Column(name="post_time",
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date postTime;//post_time;
    @Column(name="update_time",
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    //2023-07-27T15:38
    @DateTimeFormat
    (pattern = "yyyy-MM-dd'T'HH:mm")
    private Date updateTime;//update_time;
    @Column(insertable = false)
    @Enumerated(EnumType.STRING) //문자열을 enum으로 파싱
    private StatusType status; //default PUBLIC
    private String title;
    private String content;
    @Column(name="view_count",insertable = false,updatable = false)
    private int viewCount;//view_count;

    //유저가 게시글 여러개
    //* join 과 유사하게 fetch 를 users 조회
    //fetch = FetchType.EAGER : 즉시 로딩 (무조건 조회)
    //fetch = FetchType.LAZY : 지연 로딩, 타입리프의 view 렌더할 때
    // user의 필드를 조회하면 그 때 로딩

    //boards : users = N : 1
    @ManyToOne(fetch = FetchType.LAZY) //boards 필드가 아니라 조인대상
    @JoinColumn(name = "u_id",insertable = false,updatable = false)
    private Users user;

}
