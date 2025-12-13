package com.example.springapp.repositories;

import com.example.springapp.model.Horse;
import com.example.springapp.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByHorse(Horse horse);
}
