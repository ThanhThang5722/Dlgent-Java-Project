package com.example.IS216_Dlegent.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long>
{
    Optional<Discount> findById(Long id);
}
