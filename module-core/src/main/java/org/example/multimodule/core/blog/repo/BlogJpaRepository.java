package org.example.multimodule.core.blog.repo;


import org.example.multimodule.core.blog.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogJpaRepository extends JpaRepository<Blog, Long> {
    Blog findByKeywordAndDomain(String keyword, String domain);

    @Query(value =
            "SELECT keyword AS keyword, SUM(count) AS count FROM BLOG GROUP BY keyword ORDER BY count desc"
            , nativeQuery = true
    )
    List<Object[]> descKeyword();

    @Query(value =
            "SELECT b.domain AS domain, SUM(b.count) AS count FROM BLOG b GROUP BY b.domain ORDER BY count desc"
            , nativeQuery = true
    )
    List<Object[]> descDomain();

    List<Blog> findAll();
}