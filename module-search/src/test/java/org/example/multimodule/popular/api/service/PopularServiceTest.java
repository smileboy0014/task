package org.example.multimodule.popular.api.service;

import com.google.gson.Gson;
import org.example.multimodule.core.blog.model.Blog;
import org.example.multimodule.core.blog.repo.BlogJpaRepository;
import org.example.multimodule.core.blog.service.BlogService;
import org.example.multimodule.popular.api.model.Popular;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Transactional
class PopularServiceTest {

    @Autowired
    BlogJpaRepository blogJpaRepository;
    BlogService blogService = new BlogService(blogJpaRepository);
    PopularService popularService = new PopularService(blogService);

    @BeforeEach
    public void setUp() {
        //setUp
        List<Blog> blogs = new ArrayList<>();
        blogs.add(Blog.builder().domain("https://kakaobank.com/").keyword("카카오뱅크").count(100).build());
        blogs.add(Blog.builder().domain("https://want.com/").keyword("가고싶다").count(100).build());
        blogs.add(Blog.builder().domain("").keyword("여행").count(3).build());
        blogs.add(Blog.builder().domain("https://naver.com/1").keyword("고기").count(4).build());
        blogs.add(Blog.builder().domain("").keyword("독서").count(5).build());
        blogs.add(Blog.builder().domain("https://naver.com/2").keyword("맛집").count(6).build());
        blogs.add(Blog.builder().domain("https://daum.com/@tour").keyword("겨울").count(7).build());

        blogJpaRepository.saveAll(blogs);
    }

    @Test
    @DisplayName("count 가 높은 키워드 순으로 검색 결과")
    void getPopularKeywords() {
        //given
        List<Object[]> keywords = blogJpaRepository.descKeyword();
        //when
        List<Popular> popularKeywordList = popularService.makeKeywordRanking(keywords);
        //then
        //결과값을 요청과 똑같이 보기 위해 json 형태로 변환
        String jsonKeywords = new Gson().toJson(popularKeywordList);
        System.out.println(jsonKeywords);
    }

    @Test
    @DisplayName("count 가 높은 도메인 순으로 검색 결과 json 으로 변환")
    void getPopularDomains() {
        //given
        List<Object[]> domains = blogJpaRepository.descDomain();
        //when
        List<Popular> popularDomainList = popularService.makeDomainRanking(domains);
        //then
        //결과값을 보기 위해 json 형태로 변환
        String jsonDomains = new Gson().toJson(popularDomainList);
        System.out.println(jsonDomains);
    }
}
