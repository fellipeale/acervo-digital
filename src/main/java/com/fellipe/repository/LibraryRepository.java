package com.fellipe.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fellipe.domain.Library;

@RepositoryRestResource(collectionResourceRel = "libraries", path = "libraries")
public interface LibraryRepository extends CrudRepository<Library, Long> {

}
