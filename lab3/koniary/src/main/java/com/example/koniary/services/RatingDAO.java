package com.example.koniary.services;

import com.example.koniary.HibernateUtil;
import com.example.koniary.model.Rating;

import javax.persistence.EntityManager;
import java.util.List;

public class RatingDAO {

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
}
