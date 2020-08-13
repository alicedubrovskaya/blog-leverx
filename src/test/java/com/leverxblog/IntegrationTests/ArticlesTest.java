package com.leverxblog.IntegrationTests;

import com.leverxblog.controllers.controllers.ArticleController;
import com.leverxblog.services.dto.ArticleDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticlesTest {

    @Autowired
    private ArticleController articleController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getArticles() throws Exception {
        this.mockMvc.perform(get("/articles/all"))
                .andReturn().getModelAndView().getModel().get("/7articles/all");
    }

   /* @Test
    @WithMockUser(username = "alisa", password = "$2a$10$YMjJZuva0MohdRMQMFaV7ucX.uEKBcDpIS3JoMXoRmeggf2XhRJ7e", roles = {"USER"})
    public void addArticle() throws Exception {

        ArticleDto articleDto = ArticleDto.builder()
                .title("Important project")
                .build();

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/articles", articleDto))
                .andExpect(status().isOk());
    }

    */
}
