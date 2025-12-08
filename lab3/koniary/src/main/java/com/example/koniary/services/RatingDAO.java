package com.example.koniary.services;

import com.example.koniary.HibernateUtil;
import com.example.koniary.model.Rating;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class RatingDAO {
    private final EntityManagerFactory emf;

    public RatingDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Rating rating) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(rating);
        em.getTransaction().commit();
        em.close();
    }

    public Rating findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        Rating rating = em.find(Rating.class, id);
        em.close();
        return rating;
    }

    public List<Rating> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<Rating> list = em.createQuery("SELECT r FROM Rating r", Rating.class).getResultList();
        em.close();
        return list;
    }

    public List<Rating> findByHorseId(Long horseId) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<Rating> list = em.createQuery(
                        "SELECT r FROM Rating r WHERE r.horse.id = :id", Rating.class)
                .setParameter("id", horseId)
                .getResultList();
        em.close();
        return list;
    }

    public void update(Rating rating) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(rating);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Rating rating) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Rating attached = em.merge(rating);
        em.remove(attached);
        em.getTransaction().commit();
        em.close();
    }

    public List<Rating> getRatingsForHorse(long horseId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Rating r WHERE r.horse.id = :id ORDER BY r.date DESC", Rating.class)
                    .setParameter("id", horseId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Double getAverageForHorse(long horseId) {
        EntityManager em = emf.createEntityManager();
        try {
            Double result = em.createQuery("SELECT AVG(r.value) FROM Rating r WHERE r.horse.id = :id", Double.class)
                    .setParameter("id", horseId)
                    .getSingleResult();
            return result == null ? 0.0 : result;
        } finally {
            em.close();
        }
    }

    public Long getRatingCountForHorse(long horseId) {
        EntityManager em = emf.createEntityManager();
        try {
            Long result = em.createQuery("SELECT COUNT(r) FROM Rating r WHERE r.horse.id = :id", Long.class)
                    .setParameter("id", horseId)
                    .getSingleResult();
            return result;
        } finally {
            em.close();
        }
    }

}
