package com.leverxblog;

import com.leverxblog.controllers.controllers.ArticleController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private ArticleController articleController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/articles/all"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "alisa", password = "123", roles = "USER")
    public void getArticles() throws Exception {
        this.mockMvc.perform(delete("articles/delete","a75a771a-aaa6-4d07-a124-95db421c3aa5"))
                .andExpect(status().isOk());
    }
}
