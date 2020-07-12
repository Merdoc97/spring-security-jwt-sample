package com.example.jose.demo;

import com.example.jose.demo.config.PlainJWTSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.jose.demo.config.JwtHelper.generateJwt;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@Import(PlainJWTSecurityConfig.class)
@WebAppConfiguration
class DemoApplicationTests {


    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void before() {
        this.mvc = webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }


    @Test
    void contextLoads() throws Exception {
        final String jwt = generateJwt("test", Set.of("test", "admin"));
        String auth = mvc.perform(get("/user/me")
                .header("Authorization", jwt))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Map authenticationToken = objectMapper.readValue(auth, Map.class);
        Assertions.assertNotNull(authenticationToken);
        Assertions.assertEquals(authenticationToken.get("name"), "test");
        List<LinkedHashMap<List,String>> authoritiesMap = (List<LinkedHashMap<List, String>>) authenticationToken.get("authorities");
        List<String>authorities=authoritiesMap.stream().map(LinkedHashMap::values)
                .flatMap(Collection::stream)
                .map(Object::toString)
                .collect(Collectors.toList());
        org.assertj.core.api.Assertions.assertThat(authorities).contains("test","admin");
    }


}
