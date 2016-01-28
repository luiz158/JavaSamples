package com.eprogramar.tdd;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableWebMvc
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:servlet-context.xml" })
public class HomeControllerTest {
	
	private MockMvc mockMvc;

    @InjectMocks
    private HomeController controller;

    @Before
    public void setUp() throws Exception { 
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testHomeRequest() throws Exception{
        ResultActions perform = this.mockMvc.perform(get("/"));
        perform.andExpect(status().isOk())
        	.andExpect(view().name("home"))
        	.andExpect(model().attributeExists("title"))
        	.andExpect(model().attribute("title", "Spring MVC com TDD"));
    }
    
}
