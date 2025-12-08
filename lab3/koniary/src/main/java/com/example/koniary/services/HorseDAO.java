package com.example.koniary.services;

import com.example.koniary.model.Horse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class HorseDAO {

    private final EntityManagerFactory emf;

    public HorseDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Horse horse) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(horse);
        em.getTransaction().commit();
        em.close();
    }

    public void update(Horse horse) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(horse);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Horse horse) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Horse attached = em.contains(horse) ? horse : em.merge(horse);

        em.remove(attached);
        em.getTransaction().commit();
        em.close();
    }

    public Optional<Horse> findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Horse horse = em.find(Horse.class, id);
        em.close();
        return Optional.ofNullable(horse);
    }

    public List<Horse> getAll() {
        EntityManager em = emf.createEntityManager();
        List<Horse> list = em.createQuery(
                "SELECT h FROM Horse h", Horse.class
        ).getResultList();
        em.close();
        return list;
    }

    public List<Horse> findByStableId(Long stableId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT h FROM Horse h WHERE h.stable.id = :id",
                            Horse.class
                    )
                    .setParameter("id", stableId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
