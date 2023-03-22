package org.example.multimodule.search.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.multimodule.core.common.enums.ErrorCode;
import org.example.multimodule.core.common.exceptions.CustomException;
import org.example.multimodule.search.api.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/blog")
    public ResponseEntity<?> getBlogs(String query, String sort, Integer page, Integer size) {
        if (query != null && !query.equals("")) {
            log.debug("get parameter is that :: query = {}, sort = {}, page = {}, size = {}", query, sort, page, size);
            return ResponseEntity.ok().body(searchService.getBlogs(query, sort, page, size));
        } else {
            log.error("ERROR :: query parameter required");
            throw new CustomException(ErrorCode.BAD_REQUIRED_PARAMETER, "query parameter required");
        }

    }

}
