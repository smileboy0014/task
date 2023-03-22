package org.example.multimodule.core.blog.service;

import lombok.RequiredArgsConstructor;
import org.example.multimodule.core.blog.model.Blog;
import org.example.multimodule.core.blog.repo.BlogJpaRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogJpaRepository blogRepository;

    @Transactional
    @CacheEvict(value = {"keyword", "domain"}, allEntries = true)
    public void saveKeywordAndDomain(String domain, String keyword) {
        Blog blog = findByKeywordAndDomain(keyword, domain);

        if (blog == null) {
            blog = Blog.builder().keyword(keyword).domain(domain).count(1).build();

        } else {
            blog.setCount(blog.getCount() + 1);
        }

        blogRepository.save(blog);
    }

    @Transactional(readOnly = true)
    public Blog findByKeywordAndDomain(String keyword, String domain) {
        return blogRepository.findByKeywordAndDomain(keyword, domain);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "keyword")
    public List<Object[]> findPopularKeywords() {
        return blogRepository.descKeyword();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "domain")
    public List<Object[]> findPopularDomains() {
        return blogRepository.descDomain();
    }


    @Transactional(readOnly = true)
    public List<Blog> findAll(){
        return blogRepository.findAll();
    }

}

