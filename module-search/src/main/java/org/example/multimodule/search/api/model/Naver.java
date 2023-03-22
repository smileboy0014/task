package org.example.multimodule.search.api.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class Naver {

    String lastBuildDate;
    int total;
    int start;
    int display;
    List<Items> items;

}
