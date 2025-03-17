package com.jux.familyspace.repository;

import com.jux.familyspace.model.family.FamilyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyUserRepository extends JpaRepository<FamilyUser, Long> {
    Optional<FamilyUser> getByUsername(String username);
}
