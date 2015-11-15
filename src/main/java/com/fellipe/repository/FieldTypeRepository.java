package com.fellipe.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fellipe.domain.FieldType;

@RepositoryRestResource(collectionResourceRel = "field-types", path = "field-types")
public interface FieldTypeRepository extends CrudRepository<FieldType, String> {
	
}
