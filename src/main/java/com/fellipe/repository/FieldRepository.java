package com.fellipe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fellipe.domain.Field;

@RepositoryRestResource(collectionResourceRel = "fields", path = "fields")
public interface FieldRepository extends CrudRepository<Field, Long> {

	List<Field> findByLibraryId(@Param("id") Long id);
	
	List<Field> findByPresentationTrueAndLibraryId(@Param("id") Long id);
	
}
