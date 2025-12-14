package com.example.springapp.services;

import com.example.springapp.dto.HorseRequest;
import com.example.springapp.dto.RatingRequest;
import com.example.springapp.exceptions.BadRequestException;
import com.example.springapp.exceptions.NotFoundException;
import com.example.springapp.model.Horse;
import com.example.springapp.model.Rating;
import com.example.springapp.model.Stable;
import com.example.springapp.repositories.HorseRepository;
import com.example.springapp.repositories.RatingRepository;
import com.example.springapp.repositories.StableRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HorseService {

    private final HorseRepository horseRepository;
    private final StableRepository stableRepository;
    private final RatingRepository ratingRepository;

    public HorseService(HorseRepository horseRepository,
                        StableRepository stableRepository,
                        RatingRepository ratingRepository) {
        this.horseRepository = horseRepository;
        this.stableRepository = stableRepository;
        this.ratingRepository = ratingRepository;
    }

    public void addHorse(HorseRequest request) {

        if (request.getName() == null || request.getName().isBlank()) {
            throw new BadRequestException("Horse name is required");
        }

        if (request.getStableId() == null) {
            throw new BadRequestException("Stable id is required");
        }

        if (request.getAge() < 0) {
            throw new BadRequestException("Age cannot be negative");
        }

        Stable stable = stableRepository.findById(request.getStableId())
                .orElseThrow(() -> new NotFoundException("Stable not found"));

        Horse horse = new Horse(
                request.getName(),
                request.getBreed(),
                request.getType(),
                request.getStatus(),
                request.getAge(),
                request.getPrice(),
                request.getWeight()
        );

        horse.setStable(stable);
        horseRepository.save(horse);
    }

    public void deleteHorse(Long id) {
        Horse horse = horseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Horse not found"));

        horseRepository.delete(horse);
    }

    public double getAverageRating(Long horseId) {
        Horse horse = horseRepository.findById(horseId)
                .orElseThrow(() -> new NotFoundException("Horse not found"));

        List<Rating> ratings = ratingRepository.findByHorse(horse);

        if (ratings.isEmpty()) {
            throw new BadRequestException("Horse has no ratings");
        }

        return ratings.stream()
                .mapToInt(Rating::getValue)
                .average()
                .orElse(0.0);
    }

    public void addRating(RatingRequest request) {
        if (request.getValue() < 1 || request.getValue() > 5) {
            throw new BadRequestException("Rating must be between 1 and 5");
        }

        Horse horse = horseRepository.findById(request.getHorseId())
                .orElseThrow(() -> new NotFoundException("Horse not found"));

        Rating rating = new Rating(
                request.getValue(),
                request.getDescription(),
                LocalDate.now()
        );

        rating.setHorse(horse);
        ratingRepository.save(rating);
    }

    public Horse getHorse(Long id) {
        return horseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Horse not found"));
    }

    public List<Horse> getAllHorses() {
        List<Horse> horses = horseRepository.findAll();

        if (horses.isEmpty()) {
            throw new NotFoundException("No horses found");
        }

        return horses;
    }
}
