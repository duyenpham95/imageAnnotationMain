package com.duyen.inheritancemapping.repos;

import com.duyen.inheritancemapping.entities.joined.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
