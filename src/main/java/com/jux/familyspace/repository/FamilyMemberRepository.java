package com.jux.familyspace.repository;

import com.jux.familyspace.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
   Optional<FamilyMember> getByUsername(String username);
}
