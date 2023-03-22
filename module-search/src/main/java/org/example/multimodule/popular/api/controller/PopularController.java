package org.example.multimodule.popular.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.multimodule.popular.api.service.PopularService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("popular")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class PopularController {
    private final PopularService popularService;

    @GetMapping("/keyword")
    public ResponseEntity<?> getPopularKeywords() {
        return ResponseEntity.ok().body(popularService.getPopularKeywords());
    }


    @GetMapping("/domain")
    public ResponseEntity<?> getPopularDomains() {

        return ResponseEntity.ok().body(popularService.getPopularDomains());

    }


}
