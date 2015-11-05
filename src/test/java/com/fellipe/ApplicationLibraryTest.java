package com.fellipe;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fellipe.domain.Library;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("scratch")
public class ApplicationLibraryTest {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@After
	public void close() {
		SecurityContextHolder.clearContext();
	}
	
	@Test
	public void testHome() throws Exception {
		this.mvc.perform(get("/api"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testRetrieveAllLibraries() throws Exception {
		this.mvc.perform(get("/api/libraries"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.containsString("libraries")));
	}
	
	@Test
	public void testAddNewLibrary() throws Exception {
		Library library = new Library();
		library.setTitle("Test");
		library.setDescription("Test description");
		library.setActive(true);
		this.mvc.perform(post("/api/libraries")
			.content(asByteArray(library))
			.with(httpBasic("fellipe", "fellipe")))
			.andExpect(status().isCreated());
	}
		
	@Test
	public void testRejectLibraryWithErrors() throws Exception {
		Library library = new Library();
		library.setTitle("Test");
		library.setDescription("Test description");
		this.mvc.perform(post("/api/libraries")
			.content(asByteArray(library))
			.with(httpBasic("fellipe", "fellipe")))
			.andExpect(status().isInternalServerError()); //change to a better error
	}
	
	
	
	private byte[] asByteArray(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(obj);
	}
	
}
