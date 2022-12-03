package me.dicorndl.testtransactional.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParentRepository extends JpaRepository<Parent, Integer> {

    @Query("SELECT p FROM Parent p JOIN FETCH p.children WHERE p.id = :parentId")
    Optional<Parent> findByIdWithFetch(Integer parentId);
}
