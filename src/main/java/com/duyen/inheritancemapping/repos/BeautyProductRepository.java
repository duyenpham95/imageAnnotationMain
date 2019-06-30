package com.duyen.inheritancemapping.repos;

import com.duyen.inheritancemapping.entities.singletable.BeautyProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeautyProductRepository extends JpaRepository<BeautyProduct, Integer> {
}
