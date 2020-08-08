package com.leverxblog.dao.event;

import com.leverxblog.dao.entity.UserEntity;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Setter
@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private UserEntity userEntity;

    public OnRegistrationCompleteEvent(UserEntity userEntity, String appUrl, Locale locale) {
        super(userEntity);

        this.appUrl = appUrl;
        this.locale = locale;
        this.userEntity = userEntity;
    }
}
