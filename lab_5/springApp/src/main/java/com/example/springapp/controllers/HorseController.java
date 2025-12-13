package com.example.springapp.controllers;

import com.example.springapp.dto.HorseRequest;
import com.example.springapp.dto.RatingRequest;
import com.example.springapp.services.HorseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/horse")
public class HorseController {

    private final HorseService horseService;

    public HorseController(HorseService horseService) {
        this.horseService = horseService;
    }

    // 1. POST /api/horse
    @PostMapping
    public ResponseEntity<Void> addHorse(@RequestBody HorseRequest request) {
        horseService.addHorse(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 2. DELETE /api/horse/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHorse(@PathVariable Long id) {
        horseService.deleteHorse(id);
        return ResponseEntity.noContent().build();
    }

    // 3. GET /api/horse/rating/{id}
    @GetMapping("/rating/{id}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long id) {
        return ResponseEntity.ok(horseService.getAverageRating(id));
    }

    // 4. POST /api/horse/rating
    @PostMapping("/rating")
    public ResponseEntity<Void> addRating(@RequestBody RatingRequest request) {
        horseService.addRating(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
