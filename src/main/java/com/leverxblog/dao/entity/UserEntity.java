package com.leverxblog.dao.entity;

import com.leverxblog.dao.entity.security.VerificationTokenEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

    @GeneratedValue(generator = "UUID")
    @Id
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "userEntity_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "login")
    private String login;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "created_at")
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<ArticleEntity> articles = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToOne(mappedBy = "userEntity")
    private VerificationTokenEntity verificationTokenEntity;

}
