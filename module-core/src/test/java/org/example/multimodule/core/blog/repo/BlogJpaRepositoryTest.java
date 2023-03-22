package org.example.multimodule.core.blog.repo;


import org.example.multimodule.core.blog.model.Blog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Transactional
class BlogJpaRepositoryTest {

    @Autowired
    private BlogJpaRepository blogJpaRepository;

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
    @DisplayName("키워드와 도메인으로 검색 결과 조회")
    void findByKeywordAndDomain() {
        //given
        String keyword = "겨울";
        String domain = "https://daum.com/@tour";

        //when
        Blog blog = blogJpaRepository.findByKeywordAndDomain(keyword, domain);

        //then
        assertThat(blog.getKeyword()).isEqualTo(keyword);
        assertThat(blog.getDomain()).isEqualTo(domain);
    }

    @Test
    @DisplayName("count 가 높은 키워드 순으로 검색 결과 조회")
    void descKeyword() {
        //given
        String keyword = "가고싶다";
        int count = 100;

        //when
        List<Object[]> keywords = blogJpaRepository.descKeyword();

        if (keywords.size() > 0) {
            for (Object[] object : keywords) {
                //then
                assertThat(object[0]).isEqualTo(keyword);
                assertThat((object[1])).isEqualTo(BigInteger.valueOf(count));
                break;
            }

        }
    }

    @Test
    @DisplayName("count 가 높은 도메인 순으로 검색 결과 조회")
    void descDomain() {
        //given
        String domain = "https://kakaobank.com/";
        int count = 100;
        //when
        List<Object[]> keywords = blogJpaRepository.descDomain();

        if (keywords.size() > 0) {
            for (Object[] object : keywords) {
                //then
                assertThat(object[0]).isEqualTo(domain);
                assertThat((object[1])).isEqualTo(BigInteger.valueOf(count));
                break;
            }

        }

    }
}