package com.example.blogdemo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter; //Getter method 자동 생성
import lombok.Builder; //Builder 패턴
import lombok.NoArgsConstructor; //Arg 없는 기본 생성자
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;


@Entity //해당 클래스 객체를 테이블과 맵핑. 네임 속성이 없으면 클래스 이름과, 있다면 그 테이블 이름이 맵핑된다
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //접근제어자
public class Article {

    @Id //기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false) // title 컬럼과 맵핑하고 not null로 지정
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

}