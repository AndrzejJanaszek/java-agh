package com.example.koniary.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stars; // np. 1–5

    @Column(length = 500)
    private String comment;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "horse_id")
    private Horse horse;

    public Rating() {
        // Wymagany przez Hibernate
    }

    public Rating(int stars, String comment, Horse horse) {
        if (stars < 1 || stars > 5)
            throw new IllegalArgumentException("Rating must be between 1 and 5 stars.");

        this.stars = stars;
        this.comment = comment;
        this.horse = horse;
        this.createdAt = LocalDateTime.now();
    }

    // ======= GETTERY & SETTERY =======

    public Long getId() {
        return id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        if (stars < 1 || stars > 5)
            throw new IllegalArgumentException("Rating must be between 1 and 5 stars.");
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }
}
