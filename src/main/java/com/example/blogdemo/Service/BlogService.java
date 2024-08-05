package com.example.blogdemo.Service;

import com.example.blogdemo.domain.Article;
import com.example.blogdemo.dto.AddArticleRequest;
import com.example.blogdemo.dto.UpdateArticleRequest;
import com.example.blogdemo.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor; // final 또는 @NotNull 필드의 생성자 추가
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service // 컨테이너에 빈 등록
public class BlogService {
    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity()); //DTO를 엔티티 객체로 만들어서 저장
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest req) {
        Article article = blogRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        article.update(req.getTitle(), req.getContent());
        return article;
    }

}