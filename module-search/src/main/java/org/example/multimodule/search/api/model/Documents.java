package org.example.multimodule.search.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Documents {
    String blogname;
    String contents;
    String datetime;
    String thumbnail;

    String title;

    String url;
}
