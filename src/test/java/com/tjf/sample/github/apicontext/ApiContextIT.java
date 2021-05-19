package com.tjf.sample.github.apicontext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import javax.servlet.ServletException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.tjf.sample.github.apicontext.exception.JediNotFoundException;

@SpringBootTest(classes = ApiContextApplication.class)
@AutoConfigureMockMvc
public class ApiContextIT {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void beforeEach() {
		Locale.setDefault(new Locale("pt"));
	}

	@Test
	public void newJedi() throws Exception {
		String content = "{\"name\":\"Rey\",\"gender\":\"female\"}";

		mockMvc.perform(post("/api/v1/jedis").content(content).contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(5)));
	}

	@Test
	public void getJedi() throws Exception {
		mockMvc.perform(get("/api/v1/jedis/3").contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(3))).andExpect(jsonPath("$.name", is("Yoda")))
				.andExpect(jsonPath("$.gender", is("male")));
	}

	@Test
	public void getJediException() throws Exception {
		ServletException exception = Assertions.assertThrows(ServletException.class,
				() -> mockMvc.perform(get("/api/v1/jedis/99").contentType(APPLICATION_JSON)));

		Assertions.assertEquals(JediNotFoundException.class, exception.getRootCause().getClass());
	}

	@Test
	public void getAllJedis() throws Exception {
		String expectedResult = "[{\"id\":1,\"name\":\"Luke Skywalker\",\"gender\":\"male\"},{\"id\":2,\"name\":\"Obi-Wan Kenobi\",\"gender\":\"male\"},{\"id\":3,\"name\":\"Yoda\",\"gender\":\"male\"},{\"id\":4,\"name\":\"Anakin Skywalker\",\"gender\":\"male\"}]";

		mockMvc.perform(get("/api/v1/jedis").contentType(APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(expectedResult));
	}
}
