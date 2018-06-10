package com.duyen.myapp.repository;

import com.duyen.myapp.domain.ValueStreamElement;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ValueStreamElement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValueStreamElementRepository extends JpaRepository<ValueStreamElement, Long> {

}
