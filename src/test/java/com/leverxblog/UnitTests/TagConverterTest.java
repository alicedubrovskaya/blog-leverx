package com.leverxblog.UnitTests;

import com.leverxblog.services.converters.TagConverter;
import com.leverxblog.services.dto.TagDto;
import com.leverxblog.dao.entity.TagEntity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TagConverterTest {

    @Autowired
    private TagConverter tagConverter;

    private TagDto tagDtoExpected;
    private TagEntity tagEntity;

    @Before
    public void setUp() {
        Long id = Long.valueOf(1);
        tagEntity = TagEntity.builder()
                .id(id)
                .name("tag1")
                .build();
        tagDtoExpected = TagDto.builder()
                .id(id)
                .name("tag1")
                .build();
    }


    @After
    public void tearDown() {
        tagEntity = null;
        tagDtoExpected = null;
    }

    @Test
    public void toDto() {
        TagDto tagDtoActual = tagConverter.convert(tagEntity);
        Assert.assertEquals(tagDtoActual, tagDtoExpected);
    }
}
