package com.fellipe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fellipe.domain.Content;

@RepositoryRestResource(collectionResourceRel = "contents", path = "contents")
public interface ContentRepository extends CrudRepository<Content, Long>  {
	
	@Query(value = "select content.* from content join field on field.id = content.field_id where record_id = :id order by field.sequence ASC",
			nativeQuery = true)
	Iterable<Content> findByRecordId(@Param("id") Long id);
	
}
