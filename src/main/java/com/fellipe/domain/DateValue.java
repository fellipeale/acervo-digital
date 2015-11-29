package com.fellipe.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class DateValue extends Value {

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date value;
	
	public Date getValue() {
		return value;
	}

	public void setValue(Date value) {
		this.value = value;
	}

	@Override
	public List<String> validate(Content content) {
		List<String> errors = new ArrayList<String>();
		
		return errors;
	}

}
