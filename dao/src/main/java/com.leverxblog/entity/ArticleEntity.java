package com.leverxblog.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "articles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleEntity {

    @GeneratedValue(generator = "UUID")
    @Id
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "article_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToMany
    @JoinTable(name = "articles_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> tags;

    @ManyToOne
    @JoinColumn(name = "userEntity_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "articleEntity", cascade = CascadeType.ALL)
    private List<CommentEntity> comments = new ArrayList<>();
}
