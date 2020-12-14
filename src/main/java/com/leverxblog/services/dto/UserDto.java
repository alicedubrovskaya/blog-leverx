package com.leverxblog.services.dto;

import com.leverxblog.dao.entity.Role;
import com.leverxblog.dao.entity.security.VerificationTokenEntity;
import lombok.*;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private static final String MESSAGE_IF_LOGIN_IS_INCORRECT = "Login must start with an uppercase letter," +
            " may consist of latin letters, numbers, underscores, size from 3 to 15 symbols";

    private static final String MESSAGE_IF_PASSWORD_IS_INCORRECT = "Password must contain at least one" +
            " uppercase letter and one digit, at least 3 lowercase letters, size from 8 to 15 symbols";

    private static final String MESSAGE_IF_EMAIL_IS_INCORRECT = "Incorrect email";

    private UUID id;
    private String firstName;
    private String lastName;

    @Pattern(regexp = "^[A-Z][a-zA-Z0-9_]{3,15}$", message = MESSAGE_IF_LOGIN_IS_INCORRECT)
    private String login;

    @Pattern(regexp = "^(?=.*[a-z]{3,})(?=.*[A-Z])(?=.*[0-9]).{8,15}$", message = MESSAGE_IF_PASSWORD_IS_INCORRECT)
    private String password;

    @Pattern(regexp = "^\\S+@\\S+$", message = MESSAGE_IF_EMAIL_IS_INCORRECT)
    private String email;

    private Date createdAt;
    private Role role;
    private boolean enabled;
    private VerificationTokenEntity verificationTokenEntity;
    private List<ArticleDto> articles = new ArrayList<>();
    private List<CommentDto> comments = new ArrayList<>();

}
