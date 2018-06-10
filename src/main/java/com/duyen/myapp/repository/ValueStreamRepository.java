package com.duyen.myapp.repository;

import com.duyen.myapp.domain.ValueStream;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the ValueStream entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValueStreamRepository extends JpaRepository<ValueStream, Long> {

	@Query("select value_stream from ValueStream value_stream left join fetch value_stream.valueStreamTags where value_stream.id=:id ")
	ValueStream findOneWithEagerRelationships(@Param("id") Long id);
	
}
