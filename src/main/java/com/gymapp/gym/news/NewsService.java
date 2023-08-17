package com.gymapp.gym.news;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository repository;

    public NewsResponse getNews() {
        List<News> newsList = repository.findAll();
        return NewsResponse.builder().newsList(newsList).build();
    }
}
