package com.tjf.sample.github.apicontext.model;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface JediRepository extends JpaRepository<Jedi, Integer> {
}