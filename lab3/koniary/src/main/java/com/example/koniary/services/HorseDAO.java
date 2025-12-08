package com.example.koniary.services;

import com.example.koniary.HibernateUtil;
import com.example.koniary.model.Horse;

import javax.persistence.EntityManager;
import java.util.List;

public class HorseDAO {

    public void save(Horse horse) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(horse);
        em.getTransaction().commit();
        em.close();
    }

    public Horse findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        Horse horse = em.find(Horse.class, id);
        em.close();
        return horse;
    }

    public List<Horse> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<Horse> list = em.createQuery("SELECT h FROM Horse h", Horse.class).getResultList();
        em.close();
        return list;
    }

    public void update(Horse horse) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(horse);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Horse horse) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Horse attached = em.merge(horse); // w razie gdyby obiekt był "detached"
        em.remove(attached);
        em.getTransaction().commit();
        em.close();
    }
}
