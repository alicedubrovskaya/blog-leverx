package com.leverxblog.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

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

    @Column(name="enabled")
    private boolean enabled;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "created_at")
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL)
    private List<ArticleEntity> articles=new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<CommentEntity> comments=new ArrayList<>();

    @OneToOne(mappedBy = "userEntity")
    private VerificationToken verificationToken;

}
