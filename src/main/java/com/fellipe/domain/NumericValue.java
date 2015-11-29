package com.fellipe.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class NumericValue extends Value {

	private Long value;
	
	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	@Override
	public List<String> validate(Content content) {
		List<String> errors = new ArrayList<String>();
		
		return errors;
	}

}
