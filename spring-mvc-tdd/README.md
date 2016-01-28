### Exemplo de um projeto Spring MVC com testes por TDD   
*************************************************************************************

**pom properties:**   
```xml
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java-version>1.8</java-version>
		<org.springframework-version>4.2.3.RELEASE</org.springframework-version>
		<org.aspectj-version>1.6.10</org.aspectj-version>
		<org.slf4j-version>1.6.6</org.slf4j-version>
	</properties>
```   


**dependencies to test:**   
```xml
		<!-- Test -->
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.12</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-core</artifactId>
			<version>2.2.1</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.2.1</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.hamcrest</groupId>
			<artifactId>hamcrest-all</artifactId>
			<version>1.3</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.11</version>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<artifactId>hamcrest-core</artifactId>
					<groupId>org.hamcrest</groupId>
				</exclusion>
			</exclusions>
		</dependency>
		<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-core</artifactId>
			<version>1.9.5</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-test</artifactId>
			<!-- OBS.: não é a mesma versão do sprimg core, mvc, etc -->
			<version>3.2.3.RELEASE</version>
			<scope>test</scope>
		</dependency>
```   

*************************************************************************************

**Class Test Controller Home:**   
```java
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
@ContextConfiguration(locations = { "classpath*:spring-context-test.xml", "classpath*:persistence-test.xml"})
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
```   

*************************************************************************************

**Example Mockito Tests:**   
+ andExpect(view().name("..."))
+ andExpect(model().attributeExists("..."))
+ andExpect(model().attribute("...", "..."));
+ andExpect(status().isOk()) // HTTP Status = 200
+ andExpect(status().isCreated()) // HTTP Status = 201
+ andExpect(status().isBadRequest()) // HTTP Status = 400
+ andExpect(status().isUnauthorized()) // HTTP Status = 401
+ andExpect(status().isInternalServerError()) // HTTP Status = 500
+ ResultActions perform = mockMvc.perform(post("/post").param("param1", "valuePram1").param("param2", "valuePram2")); 
+ andExpect(content().string("{\"status\":\"E\",\"errorMessages\":{\"coletaCiclo\":\"Coleta/ciclo nao pode ser Nulo\"}}"));
+ andExpect(content().contentType("application/json"));
+ String json = mockMvc.perform(get("/get/"+coleta+"/"+ciclo))
	.andExpect(status().isOk())
	.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	.andReturn().getResponse().getContentAsString();
+ andExpect(status().isMovedTemporarily()) // testa se foi redirecionada
+ andExpect(view().name("redirect:pessoaList")) // testa a url de redirecionamento

*************************************************************************************

**Test Mock DAO**   
```java
	@Mock
	private PessoaDAO mockPessoaDAO;
	
	@Test
	public void testPessoaSave() throws Exception{
		...

		ArgumentCaptor<Pessoa> formObjectArgument = ArgumentCaptor.forClass(Pessoa.class);
		verify(mockPessoaDAO).persist(formObjectArgument.capture());
		verifyNoMoreInteractions(mockPessoaDAO);
		
		...
	}	
```   


**Referencias:**   
http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-configuration/
http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/
https://github.com/pkainulainen/spring-mvc-test-examples/tree/master/controllers-unittest
http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-rest-api/


