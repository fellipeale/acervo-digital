package com.fellipe;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.fellipe.domain.Field;
import com.fellipe.domain.FieldType;
import com.fellipe.domain.Restriction;
import com.fellipe.domain.StringRestriction;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("scratch")
public class ApplicationFieldTest {

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
	public void testRetrieveAllFields() throws Exception {
		this.mvc.perform(get("/api/fields"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.containsString("fields")));
	}
	
	@Test
	public void testAddNewField() throws Exception {
		Field field = new Field();
		field.setTitle("Nome");
		field.setActive(true);
		field.setMultiple(false);
		field.setMandatory(true);
		field.setPresentation(true);
//		field.setFieldType(FieldType.STRING);
		this.mvc.perform(post("/api/fields")
				.content(asByteArray(field))
				.with(httpBasic("fellipe", "fellipe")))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void testAddNewFieldWithRestriction() throws Exception {
		Field field = new Field();
		field.setTitle("Nome");
		field.setActive(true);
		field.setMultiple(false);
		field.setMandatory(true);
		field.setPresentation(true);
//		field.setFieldType(FieldType.STRING);
		
		StringRestriction restriction1 = new StringRestriction();
		restriction1.setRestriction("Fellipe");
		StringRestriction restriction2 = new StringRestriction();
		restriction2.setRestriction("Bruna");
		List<Restriction> restrictions = new ArrayList<Restriction>();
		restrictions.add(restriction1);
		restrictions.add(restriction2);
		
		field.setRestrictions(restrictions);
		
		this.mvc.perform(post("/api/fields")
				.content(asByteArray(field))
				.with(httpBasic("fellipe", "fellipe")))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void testRejectFieldWithErrors() throws Exception {
		Field field = new Field();
		field.setTitle("Nome");
		this.mvc.perform(post("/api/fields")
				.content(asByteArray(field))
				.with(httpBasic("fellipe", "fellipe")))
				.andExpect(status().isInternalServerError());
	}
	
	private byte[] asByteArray(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(obj);
	}
	
}
