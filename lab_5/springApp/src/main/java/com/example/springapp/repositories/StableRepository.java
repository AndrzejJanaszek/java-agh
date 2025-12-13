package com.example.springapp.repositories;

import com.example.springapp.model.Stable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StableRepository extends JpaRepository<Stable, Long> {
}
