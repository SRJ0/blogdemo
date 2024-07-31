package com.example.blogdemo.dto;

import com.example.blogdemo.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder() // Builder 이용하면 가독성을 높일 수 있음
                .title(title)
                .content(content)
                .build();
    }
}
