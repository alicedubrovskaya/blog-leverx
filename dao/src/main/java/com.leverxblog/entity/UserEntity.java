package com.leverxblog.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity {
    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL)
    private List<ArticleEntity> articles=new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<CommentEntity> comments=new ArrayList<>();

    @GeneratedValue(generator = "UUID")
    @Id
    @GenericGenerator(
          name = "UUID",
          strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "userEntity_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name="login")
    private String login;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "created_at")
    private Date createdAt;

}
