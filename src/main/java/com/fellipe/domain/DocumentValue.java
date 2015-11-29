package com.fellipe.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class DocumentValue extends Value {

	private Byte[] value;
	
	public Byte[] getValue() {
		return value;
	}

	public void setValue(Byte[] value) {
		this.value = value;
	}

	@Override
	public List<String> validate(Content content) {
		List<String> errors = new ArrayList<String>();
		
		return errors;
	}

}
