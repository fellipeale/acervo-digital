package com.fellipe.domain;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@Entity
@JsonTypeInfo(property = "type", use = NAME, include = PROPERTY)
@JsonSubTypes({ @JsonSubTypes.Type(value = StringValue.class, name = "STRING"),
				@JsonSubTypes.Type(value = DateValue.class, name = "DATE"),
				@JsonSubTypes.Type(value = NumericValue.class, name = "NUMERIC"),
				@JsonSubTypes.Type(value = ImageValue.class, name = "IMAGE"),
				@JsonSubTypes.Type(value = VideoValue.class, name = "VIDEO"),
				@JsonSubTypes.Type(value = AudioValue.class, name = "AUDIO"),
				@JsonSubTypes.Type(value = DocumentValue.class, name = "DOCUMENT")})
public abstract class Value {

	private Long id;

	public abstract List<String> validate(Content content);
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
