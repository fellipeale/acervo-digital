package com.fellipe.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fellipe.domain.Library;

@RepositoryRestResource(collectionResourceRel = "libraries", path = "libraries")
public interface LibraryRepository extends CrudRepository<Library, Long> {

	@Override
	@Modifying
	@Query(value = "update library set active = 0 where id = :id", nativeQuery = true)
	public void delete(@Param("id") Long id);
	
}
