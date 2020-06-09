package com.leverxblog.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "comments")
public class CommentEntity {
    @ManyToOne
    @JoinColumn(name="userEntity_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity articleEntity;


    @GeneratedValue(generator = "UUID")
    @Id
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "commment_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name="message")
    private String message;

    @Column(name="created_at")
    private Date createdAt;

}
