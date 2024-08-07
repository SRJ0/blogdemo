package com.example.blogdemo.dto;

import com.example.blogdemo.domain.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {
    private String title;
    private String content;

    public ArticleViewResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }

}
