package com.fellipe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.fellipe.domain.Content;
import com.fellipe.domain.Field;
import com.fellipe.domain.FieldType;
import com.fellipe.domain.Library;
import com.fellipe.domain.Record;

@Configuration
public class AppConfiguration extends RepositoryRestMvcConfiguration {

	@Override
	public RepositoryRestConfiguration config() {
		return super.config();
	}
	
	@Override
	protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Library.class, Record.class, Field.class, FieldType.class, Content.class);
		config.setBaseUri("/api");
	}
	
}
