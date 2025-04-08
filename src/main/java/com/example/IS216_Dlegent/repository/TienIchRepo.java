package com.example.IS216_Dlegent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.TienIch;

@Repository
public interface TienIchRepo extends JpaRepository<TienIch, Long>{
    List<TienIch> findAllByIdIn(List<Long> ids);
}
