package org.example.multimodule.core.blog.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String keyword;

    @Column
    private String domain;

    @Column
    private Integer count;

}
