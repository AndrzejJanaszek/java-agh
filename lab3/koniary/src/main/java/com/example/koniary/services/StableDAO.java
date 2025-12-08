package com.example.koniary.services;

import com.example.koniary.HibernateUtil;
import com.example.koniary.model.Stable;

import javax.persistence.EntityManager;
import java.util.List;

public class StableDAO {

    public void save(Stable stable) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(stable);
        em.getTransaction().commit();
        em.close();
    }

    public Stable findById(Long id) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        Stable stable = em.find(Stable.class, id);
        em.close();
        return stable;
    }

    public List<Stable> findAll() {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        List<Stable> list = em.createQuery("SELECT s FROM Stable s", Stable.class).getResultList();
        em.close();
        return list;
    }

    public void update(Stable stable) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.merge(stable);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Stable stable) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Stable attached = em.merge(stable); // merge → żeby był w kontekście
        em.remove(attached);
        em.getTransaction().commit();
        em.close();
    }
}
