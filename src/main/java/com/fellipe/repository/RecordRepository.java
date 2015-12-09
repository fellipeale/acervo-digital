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
	
	@Query(value = "select distinct record.* from record" +
			" join content on content.record_id = record.id" +
			" join value on value.content_id = content.id" +
			" where value.value like %:query%", 
			nativeQuery = true)
	List<Record> findByValue(@Param("query") String query);
	
}
