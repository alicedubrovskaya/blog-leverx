package com.leverxblog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserRegisterDto extends UserDto {
 //   private String login;
    private boolean registration;
}
