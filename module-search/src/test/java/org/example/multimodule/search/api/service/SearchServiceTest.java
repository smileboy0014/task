package org.example.multimodule.search.api.service;

import com.google.gson.Gson;
import org.example.multimodule.core.blog.model.Blog;
import org.example.multimodule.core.blog.repo.BlogJpaRepository;
import org.example.multimodule.core.blog.service.BlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;


@DataJpaTest
class SearchServiceTest {

    @Autowired
    BlogJpaRepository blogJpaRepository;
    BlogService blogService = new BlogService(blogJpaRepository);
    SearchService searchService = new SearchService(blogService);

    @BeforeEach
    public void setUp() {
        //setUp
        List<Blog> blogs = new ArrayList<>();
        blogs.add(Blog.builder().domain("https://kakaobank.com/").keyword("카카오뱅크").count(100).build());
        blogs.add(Blog.builder().domain("https://want.com/").keyword("가고싶다").count(100).build());
        blogs.add(Blog.builder().domain("https://naver.com/1").keyword("고기").count(4).build());
        blogs.add(Blog.builder().domain("").keyword("독서").count(5).build());
        blogs.add(Blog.builder().domain("https://naver.com/2").keyword("맛집").count(6).build());
        blogs.add(Blog.builder().domain("https://daum.com/@tour").keyword("겨울").count(7).build());

        blogJpaRepository.saveAll(blogs);
    }

    @Test
    @DisplayName("blog search API 검색 결과")
    void getBlogSearchAPi() {
        //given
        String query = "여행";
        String sort = "recency";
        Integer page = null;
        Integer size = null;
        //when
        Object searchResult = searchService.getBlogSearchAPi(query, sort, page, size);
        //then
        //결과값을 보기 위해 json 형태로 변환
        String jsonDomains = new Gson().toJson(searchResult);
        System.out.println(jsonDomains);
    }


}