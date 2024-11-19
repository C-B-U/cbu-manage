package com.example.cbumanage.repository;

import com.example.cbumanage.model.CbuMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CbuMemberRepository extends JpaRepository<CbuMember, Long> {
    @Override
    void deleteAll();
}
