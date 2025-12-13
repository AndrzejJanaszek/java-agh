package com.example.springapp.services;

import com.example.springapp.dto.StableRequest;
import com.example.springapp.exceptions.NotFoundException;
import com.example.springapp.model.Horse;
import com.example.springapp.model.Stable;
import com.example.springapp.repositories.StableRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class StableService {

    private final StableRepository stableRepository;

    public StableService(StableRepository stableRepository) {
        this.stableRepository = stableRepository;
    }

    public List<Stable> getAllStables() {
        return stableRepository.findAll();
    }

    public List<Horse> getHorsesInStable(Long id) {
        Stable stable = stableRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stable not found"));

        return stable.getHorses();
    }

    public void addStable(StableRequest request) {
        Stable stable = new Stable(
                request.getName(),
                request.getCapacity()
        );

        stableRepository.save(stable);
    }

    public void deleteStable(Long id) {
        Stable stable = stableRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stable not found"));

        stableRepository.delete(stable);
    }

    public double getFill(Long id) {
        Stable stable = stableRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stable not found"));

        return (double) stable.getHorses().size() / stable.getCapacity();
    }

    public byte[] getStableCsv(Long id) {
        Stable stable = stableRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Stable not found"));

        StringBuilder csv = new StringBuilder();
        csv.append("id,name,breed,age,price,weight,status,type\n");

        for (Horse h : stable.getHorses()) {
            csv.append(h.getId()).append(",")
                    .append(h.getName()).append(",")
                    .append(h.getBreed()).append(",")
                    .append(h.getAge()).append(",")
                    .append(h.getPrice()).append(",")
                    .append(h.getWeight()).append(",")
                    .append(h.getStatus()).append(",")
                    .append(h.getType())
                    .append("\n");
        }

        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }
}
