package com.example.springapp.controllers;

import com.example.springapp.dto.StableRequest;
import com.example.springapp.model.Horse;
import com.example.springapp.model.Stable;
import com.example.springapp.services.StableService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stable")
public class StableController {

    private final StableService stableService;

    public StableController(StableService stableService) {
        this.stableService = stableService;
    }

    // 5. GET /api/stable
    @GetMapping
    public ResponseEntity<List<Stable>> getAllStables() {
        return ResponseEntity.ok(stableService.getAllStables());
    }

    // 6. GET /api/stable/{id}
    @GetMapping("/{id}")
    public ResponseEntity<List<Horse>> getHorses(@PathVariable Long id) {
        return ResponseEntity.ok(stableService.getHorsesInStable(id));
    }

    // 7. GET /api/stable/{id}/csv
    @GetMapping("/{id}/csv")
    public ResponseEntity<byte[]> getCsv(@PathVariable Long id) {
        byte[] csv = stableService.getStableCsv(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"stable.csv\"")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv);
    }

    // 8. POST /api/stable
    @PostMapping
    public ResponseEntity<Void> addStable(@RequestBody StableRequest request) {
        stableService.addStable(request);
        return ResponseEntity.status(201).build();
    }

    // 9. DELETE /api/stable/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStable(@PathVariable Long id) {
        stableService.deleteStable(id);
        return ResponseEntity.noContent().build();
    }

    // 10. GET /api/stable/{id}/fill
    @GetMapping("/{id}/fill")
    public ResponseEntity<Double> getFill(@PathVariable Long id) {
        return ResponseEntity.ok(stableService.getFill(id));
    }
}
