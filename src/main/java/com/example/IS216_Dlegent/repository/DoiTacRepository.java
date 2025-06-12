package com.example.IS216_Dlegent.repository;

import java.util.Optional;

import org.apache.poi.sl.draw.geom.GuideIf.Op;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.Account;
import com.example.IS216_Dlegent.model.DoiTac;
import java.util.List;


@Repository
public interface DoiTacRepository extends JpaRepository<DoiTac, Long> {
    public Optional<DoiTac> findByAccount(Account account);
    public Optional<DoiTac> findById(Long id);
}
