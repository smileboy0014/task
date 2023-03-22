package org.example.multimodule.search.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.multimodule.core.blog.service.BlogService;
import org.example.multimodule.core.common.enums.EndPoint;
import org.example.multimodule.core.common.enums.Key;
import org.example.multimodule.core.common.enums.URL;
import org.example.multimodule.core.common.util.WebClientUtil;
import org.example.multimodule.search.api.model.Daum;
import org.example.multimodule.search.api.model.Naver;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {
    private final BlogService blogService;

    public Object getBlogs(String query, String sort, Integer page, Integer size) {
        log.info("SERVICE ::: getBlogs service start");

        // kakao blog search api call
        Object searchResult = getBlogSearchAPi(query, sort, page, size);

        // db에 검색어 및 도메인 정보 저장
        saveQueryParams(query);

        return searchResult;
    }


    public Object getBlogSearchAPi(String query, String sort, Integer page, Integer size) {
        log.info("SERVICE ::: getBlogSearchAPi service start");
        WebClient client = WebClientUtil.getBaseUrl(URL.DEV_KAKAO_URL);
        Object blogResult;

        MultiValueMap<String, String> params = setParams(query, sort, page, size);
        // kakao api 를 호출하여 blog 정보 get
        try {

            blogResult = client.get()
                    .uri(uriBuilder ->
                            uriBuilder
                                    .path(EndPoint.KAKAO_BLOG_SEARCH)
                                    .queryParams(params).build()
                    )
                    .header("Authorization", Key.KAKAO_REST_KEY)
                    .retrieve()
                    .bodyToMono(Daum.class)
                    .log()
                    .block();
        } catch (Exception e) {
            // 만약 kakao api 를 호출하여 error 가 발생하면 naver blog 검색 결과로 대체
            log.error(e.getMessage());
            WebClient subClient = client.mutate().baseUrl(URL.DEV_NAVER_URL).build();
            MultiValueMap<String, String> newParams = setParamKeyChange(params);
            blogResult = subClient.get()
                    .uri(uriBuilder ->
                            uriBuilder
                                    .path(EndPoint.NAVER_BLOG_SEARCH)
                                    .queryParams(newParams).build()
                    )
                    .header("X-Naver-Client-Id", Key.NAVER_CLIENT_ID)
                    .header("X-Naver-Client-Secret", Key.NAVER_CLIENT_SECRET)
                    .retrieve()
                    .bodyToMono(Naver.class)
                    .log()
                    .block();

        }
        return blogResult;
    }

    public void saveQueryParams(String query) {
        log.info("SERVICE ::: saveQueryParams service start");
        // 내 db에 검색 키워드 및 도메인 저장
        String[] domainAndKeyword = query.split(" ");
        if (domainAndKeyword.length == 1) {
            //  도메인일 경우
            if (domainAndKeyword[0].contains("http:") || domainAndKeyword[0].contains("https:")) {
                blogService.saveKeywordAndDomain(domainAndKeyword[0], "");
            }
            //  검색어일 경우
            else {
                blogService.saveKeywordAndDomain("", domainAndKeyword[0]);
            }
        }
        //  도메인과 검색어 둘다 일 경우
        else {
            blogService.saveKeywordAndDomain(domainAndKeyword[0], domainAndKeyword[1]);
        }

    }

    public MultiValueMap<String, String> setParams(String query, String sort, Integer page, Integer size) {
        MultiValueMap<String, String> returnParams = new LinkedMultiValueMap<>();

        if (query != null) {
            returnParams.add("query", query);
        }
        if (sort != null) {
            returnParams.add("sort", sort);
        }
        if (page != null) {
            returnParams.add("page", String.valueOf(page));
        }
        if (size != null) {
            returnParams.add("size", String.valueOf(size));
        }
        return returnParams;

    }

    public MultiValueMap<String, String> setParamKeyChange(MultiValueMap<String, String> params) {

        if (params.get("sort") != null) {
            String sort;
            if (params.get("sort").toString().equals("accuracy")) {
                sort = "sim";
            } else {
                sort = "date";
            }
            params.remove("sort");
            params.add("sort", sort);
        }

        if (params.get("page") != null) {
            String start = params.get("page").get(0);
            params.remove("page");
            params.add("start", start);
        }

        if (params.get("size") != null) {
            String display = params.get("size").get(0);
            params.remove("size");
            params.add("display", display);
        }

        return params;
    }


}
