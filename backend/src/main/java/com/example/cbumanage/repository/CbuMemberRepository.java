package com.example.cbumanage.repository;

import com.example.cbumanage.model.CbuMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CbuMemberRepository extends JpaRepository<CbuMember, Long> {
    @Override
    void deleteAll();

    @Query("SELECT m FROM CbuMember m WHERE m.cbuMemberId NOT IN (SELECT d.memberId FROM Dues d WHERE d.term = :term)")
    List<CbuMember> findAllWithoutDues(@Param("term") String term);


}
