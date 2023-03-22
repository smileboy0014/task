package org.example.multimodule.search.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Meta {
    boolean is_end;

    int pageable_count;

    int total_count;


}
