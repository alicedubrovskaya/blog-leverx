package com.leverxblog.event;

import com.leverxblog.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Setter
@Getter
public class OnResetPasswordEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private UserEntity userEntity;
    private String password;

    public OnResetPasswordEvent(UserEntity userEntity, String password, String appUrl, Locale locale) {
        super(userEntity);

        this.appUrl = appUrl;
        this.locale = locale;
        this.userEntity = userEntity;
        this.password = password;
    }
}

