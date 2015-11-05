package com.fellipe.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fellipe.domain.Content;

@RepositoryRestResource(collectionResourceRel = "contents", path = "contents")
public interface ContentRepository extends CrudRepository<Content, Long>  {

}
