package com.example.salonmanage.repository;

import com.example.salonmanage.Entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {
    @Query("SELECT b FROM Branch b WHERE b.status = 1")
    List<Branch> findAllWithNotRemove();

    @Query("SELECT b FROM Branch b WHERE b.status != 3")
    List<Branch> findAllWithNotRemoveAdmin();

    @Query("SELECT COUNT(*) FROM Branch b WHERE b.status != 3")
    int countAllWithNotRemove();
}
