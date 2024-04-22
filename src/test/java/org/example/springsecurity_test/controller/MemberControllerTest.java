package org.example.springsecurity_test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springsecurity_test.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class MemberControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void register() throws Exception {
        // 創建 Member 數據
        Member member = new Member();
        member.setEmail("test1@gmail.com");
        member.setPassword("111");
        member.setName("Test1");
        member.setAge(25);

        // json 轉換為字串
        String jsonString = objectMapper.writeValueAsString(member);

        // 註冊測試
        RequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                // 須加上 header
                .header("Content-Type", "application/json")
                .content(jsonString);

        mockMvc.perform(request)
                .andExpect(status().is(200));

        // 利用新創建的 member 登入測試
        RequestBuilder loginRequestBuilder = MockMvcRequestBuilders
                .post("/userLogin")
                .with(httpBasic("test1@gmail.com", "111"));

        mockMvc.perform(loginRequestBuilder)
                .andExpect(status().is(200));

    }

    @Test
    public void userLogin() throws Exception {
        // 登入測試
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/userLogin")
                .with(httpBasic("normal@gmail.com","normal"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }
}
