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
import org.junit.Ignore;
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
import com.fellipe.domain.Content;
import com.fellipe.domain.Field;
import com.fellipe.domain.FieldType;
import com.fellipe.domain.Library;
import com.fellipe.domain.Record;
import com.fellipe.domain.StringValue;
import com.fellipe.domain.Value;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("scratch")
public class ApplicationRecordTest {

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
	public void testRetrieveAllRecords() throws Exception {
		this.mvc.perform(get("/api/records"))
			.andExpect(status().isOk())
			.andExpect(content().string(Matchers.containsString("records")));
	}
	
	@Test
	@Ignore
	public void testAddNewRecord() throws Exception {
		
		Library library = new Library();
		library.setTitle("Test");
		library.setActive(true);
		
		this.mvc.perform(post("/api/libraries")
				.content(asByteArray(library))
				.with(httpBasic("zeca", "zeca")))
				.andExpect(status().isCreated());
		
		Field field = new Field();
		field.setTitle("Test");
		field.setActive(true);
		field.setMultiple(false);
		field.setMandatory(true);
		field.setPresentation(true);
//		field.setFieldType(FieldType.STRING);
		
		this.mvc.perform(post("/api/fields")
				.content(asByteArray(field))
				.with(httpBasic("zeca", "zeca")))
				.andExpect(status().isCreated());
		
		List<Value> values = new ArrayList<Value>();
		StringValue value = new StringValue();
		value.setValue("Test1");
		values.add(value);
		
		List<Content> contents = new ArrayList<Content>();
		Content content = new Content();
		content.setField(field);
		content.setValues(values);
//		value.setContent(content);
		contents.add(content);
		
		Record record = new Record();
		record.setLibrary(library);
		record.setContents(contents);
		
		this.mvc.perform(post("/api/records")
				.content(asByteArray(record))
				.with(httpBasic("zeca", "zeca")))
				.andExpect(status().isCreated());
	}
		
	@Test
	public void testRejectRecordWithErrors() throws Exception {
		
//		this.mvc.perform(post("/api/records")
//				.content(asByteArray())
//				.with(httpBasic("fellipe", "fellipe")))
//				.andExpect(status().isInternalServerError());
	}
	
	private byte[] asByteArray(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(obj);
	}
	
}
