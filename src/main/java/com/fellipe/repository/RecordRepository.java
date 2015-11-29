package com.fellipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fellipe.domain.Record;

@RepositoryRestResource(collectionResourceRel = "records", path = "records")
public interface RecordRepository extends CrudRepository<Record, Long> {

	List<Record> findByLibraryId(@Param("id") Long id);
	
	@Override
	@Modifying
	@Query(value = "update record set active=0 where id = :id", nativeQuery = true)
	public void delete(@Param("id") Long id);
	
}
