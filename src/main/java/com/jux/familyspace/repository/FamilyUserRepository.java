package com.jux.familyspace.repository;

import com.jux.familyspace.model.family.FamilyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyUserRepository extends JpaRepository<FamilyUser, Long> {
}
