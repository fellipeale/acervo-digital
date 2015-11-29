package com.fellipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fellipe.domain.Field;

@RepositoryRestResource(collectionResourceRel = "fields", path = "fields")
public interface FieldRepository extends CrudRepository<Field, Long> {

	List<Field> findByLibraryIdOrderBySequenceAsc(@Param("id") Long id);
	
	List<Field> findByPresentationTrueAndLibraryId(@Param("id") Long id);

	@Override
	@Modifying
	@Query(value = "update field set active=0 where id = :id", nativeQuery = true)
	public void delete(@Param("id") Long id);
	
}
