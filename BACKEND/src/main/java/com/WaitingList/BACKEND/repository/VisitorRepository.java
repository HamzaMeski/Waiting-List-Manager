package com.WaitingList.BACKEND.repository;

import com.WaitingList.BACKEND.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    // Basic CRUD operations inherited from JpaRepository are sufficient
}