package com.example.IS216_Dlegent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.DoiTac;

@Repository
public interface DoiTacRepository extends JpaRepository<DoiTac, Long> {
    
}
