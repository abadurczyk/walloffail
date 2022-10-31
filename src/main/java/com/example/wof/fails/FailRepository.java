package com.example.wof.fails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FailRepository extends JpaRepository<FailEntity, Integer> {
}
