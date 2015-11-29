package com.fellipe.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class DocumentValue extends Value {

	private String name;
	private String originalName;
	private String fileType;
	private Long size;
	private String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public List<String> validate(Content content) {
		List<String> errors = new ArrayList<String>();

		return errors;
	}

}
