package com.gymapp.gym.news;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository repository;

    public NewsResponse getNews() {
        List<News> newsList = repository.findAll();
        if (newsList.isEmpty()) {
            return NewsResponse.builder().errorMessage("No news available").build();
        }
        return NewsResponse.builder().newsList(newsList).build();
    }

    public NewsResponse getSpecificNews(String blogId) {
        News news = repository.findById(blogId);

        if (news == null) {
            throw new RuntimeException("No news found for this id");
        }

        return NewsResponse.builder().title(news.getTitle()).body(news.getBody()).createdAt(news.getCreatedAt()).author(news.getAuthor()).category(news.getCategory()).imageUrl(news.getImageUrl()).build();
    }
}
