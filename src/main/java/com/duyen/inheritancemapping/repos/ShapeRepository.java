package com.duyen.inheritancemapping.repos;

import com.duyen.inheritancemapping.entities.tableperclass.Shape;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShapeRepository extends JpaRepository<Shape, Integer> {
}
