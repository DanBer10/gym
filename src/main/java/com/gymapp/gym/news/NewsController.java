package com.gymapp.gym.news;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class NewsController {
    private final NewsService service;

    @GetMapping("/news")
    public ResponseEntity<NewsResponse> getNews() {
        return ResponseEntity.ok(service.getNews()
        );
    }

    @GetMapping("/getNewsInformationForSpecificBlog/{blogId}")
    public ResponseEntity<NewsResponse> getSpecificNews(@PathVariable String blogId) {
        return ResponseEntity.ok(service.getSpecificNews(blogId));
    }

}
