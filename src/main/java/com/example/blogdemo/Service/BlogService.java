package com.example.blogdemo.Service;

import com.example.blogdemo.domain.Article;
import com.example.blogdemo.dto.AddArticleRequest;
import com.example.blogdemo.repository.BlogRepository;
import lombok.RequiredArgsConstructor; // final 또는 @NotNull 필드의 생성자 추가
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service // 컨테이너에 빈 등록
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity()); //DTO를 엔티티 객체로 만들어서 저장
    }
}
