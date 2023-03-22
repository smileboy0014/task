package org.example.multimodule.popular.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.multimodule.core.blog.model.Blog;
import org.example.multimodule.core.blog.service.BlogService;
import org.example.multimodule.core.common.enums.ErrorCode;
import org.example.multimodule.core.common.exceptions.CustomException;
import org.example.multimodule.popular.api.model.Domain;
import org.example.multimodule.popular.api.model.Keyword;
import org.example.multimodule.popular.api.model.Popular;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PopularService {

    private final BlogService blogService;

    public List<Popular> getPopularKeywords() {
        log.info("SERVICE ::: getPopularKeywords service start");
        List<Object[]> populars = blogService.findPopularKeywords();
        if (populars.size() == 0) {
            throw new CustomException(ErrorCode.BAD_REQUEST_PARAMETER, "검색 데이터가 존재하지 않습니다.");
        }

        return makeKeywordRanking(populars);
    }

    public List<Popular> getPopularDomains() {
        log.info("SERVICE ::: getPopularDomains service start");
        List<Object[]> populars = blogService.findPopularDomains();

        if (populars.size() == 0) {
            throw new CustomException(ErrorCode.BAD_REQUEST_PARAMETER, "검색 데이터가 존재하지 않습니다.");
        }

        return makeDomainRanking(populars);
    }

    public List<Popular> makeKeywordRanking(List<Object[]> populars) {
        List<Popular> popularList = new ArrayList<>();
        int cnt = 1;
        for (Object[] object : populars) {
            if (object[0].equals("")) continue;
            if (cnt > 10) break;
            Keyword keyword = new Keyword();

            keyword.setKeyword((String) object[0]);
            keyword.setCount((BigInteger) object[1]);
            keyword.setRank(cnt);
            log.debug("SERVICE :: keyword value is that :: keyword = {}, count = {}, rank = {}", object[0], object[1], cnt);
            popularList.add(keyword);

            cnt++;
        }
        return popularList;
    }

    public List<Popular> makeDomainRanking(List<Object[]> populars) {
        List<Popular> popularList = new ArrayList<>();
        int cnt = 1;
        for (Object[] object : populars) {
            if (object[0].equals("")) continue;
            if (cnt > 10) break;
            Domain domain = new Domain();

            domain.setDomain((String) object[0]);
            domain.setCount((BigInteger) object[1]);
            domain.setRank(cnt);
            log.debug("SERVICE :: domain value is that :: domain = {}, count = {}, rank = {}", object[0], object[1], cnt);
            popularList.add(domain);

            cnt++;
        }
        return popularList;
    }

}
