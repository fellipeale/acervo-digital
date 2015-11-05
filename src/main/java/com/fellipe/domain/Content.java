package com.fellipe.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fellipe.validator.ValidValues;

@Entity
@ValidValues
public class Content {

	private Long id;
	@NotNull
	private Field field;
	@Valid
	private List<Value> values;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "fieldId")
	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "content_id")
	public List<Value> getValues() {
		return values;
	}

	public void setValues(List<Value> values) {
		this.values = values;
	}

}
