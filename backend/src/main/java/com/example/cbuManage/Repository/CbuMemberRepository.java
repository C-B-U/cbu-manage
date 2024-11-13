package com.example.cbuManage.Repository;

import com.example.cbuManage.Model.CbuMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CbuMemberRepository extends JpaRepository<CbuMember, Long> {
    @Override
    void deleteAll();
}
