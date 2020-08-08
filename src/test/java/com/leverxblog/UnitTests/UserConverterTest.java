package com.leverxblog.UnitTests;

import com.leverxblog.services.converters.UserConverter;
import com.leverxblog.services.dto.UserDto;
import com.leverxblog.dao.entity.Role;
import com.leverxblog.dao.entity.UserEntity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserConverterTest {
    @Autowired
    private UserConverter userConverter;

    private UserEntity userEntityExpected;
    private UserDto userDto;

    @Before
    public void setUp() {
        UUID userId = UUID.randomUUID();
        Date date = new Date(System.currentTimeMillis());

        userDto = UserDto.builder()
                .id(userId)
                .firstName("Alisa")
                .lastName("Dubrovskaya")
                .password("123")
                .email("lvrx@gmail.com")
                .createdAt(date)
                .login("test")
                .role(Role.USER)
                .verificationTokenEntity(null)
                .enabled(true)
                .build();

        userEntityExpected = UserEntity.builder()
                .id(userId)
                .firstName("Alisa")
                .lastName("Dubrovskaya")
                .password("123")
                .email("lvrx@gmail.com")
                .createdAt(date)
                .login("test")
                .role(Role.USER)
                .verificationTokenEntity(null)
                .enabled(true)
                .build();
    }

    @After
    public void tearDown() {
        userDto = null;
        userEntityExpected = null;
    }

    @Test
    public void fromDto() {
        UserEntity userEntityActual = userConverter.convert(userDto);
        Assert.assertEquals(userEntityActual, userEntityExpected);
    }

}
