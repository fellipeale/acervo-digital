package com.fellipe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fellipe.domain.Record;

@RepositoryRestResource(collectionResourceRel = "records", path = "records")
public interface RecordRepository extends CrudRepository<Record, Long> {

	List<Record> findByLibraryId(@Param("id") Long id);
	
  // List<Record> findByPresentationTrueAndLibraryId(@Param("id") Long id);

}
