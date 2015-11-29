package com.fellipe.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class StringValue extends Value {

	public String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public List<String> validate(Content content) {
		
		Field field = content.getField();
		List<String> errors = new ArrayList<String>();
		
		if (!emptyValue() && field.getDefaultValue() != null) {
			value = field.getDefaultValue();
		}
		if (!emptyValue()  && (field.getSize() != null && value.length() > field.getSize())) {
			errors.add("{value.size}");
		}
		if (!emptyValue() && (field.getRestrictions() != null && field.getRestrictions().size() > 0 && !containValue(field.getRestrictions()))) {
			errors.add("{value.restriction}");
		}
		if (!field.getFieldType().getKey().equals("STRING")) {
			errors.add("{value.type}");
		}
		
		return errors;
	}
	
	private boolean containValue(List<Restriction> restrictions) {
		for (Restriction restriction : restrictions) {
			if (((StringRestriction) restriction).getRestriction().equals(value)) {
				return true;
			}
		}
		return false;
	}

	private boolean emptyValue() {
		return value == null || value.length() == 0;
	}
	
}
