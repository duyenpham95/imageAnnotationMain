package com.duyen.myapp.repository;

import com.duyen.myapp.domain.ValueStreamTag;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ValueStreamTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValueStreamTagRepository extends JpaRepository<ValueStreamTag, Long> {

}
