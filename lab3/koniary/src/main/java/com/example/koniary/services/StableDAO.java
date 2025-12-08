package com.example.koniary.services;

import com.example.koniary.model.Stable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

public class StableDAO {

    private final EntityManagerFactory emf;

    public StableDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Stable stable) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(stable);
        em.getTransaction().commit();
        em.close();
    }

    public void update(Stable stable) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(stable);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Stable stable) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Stable attached = em.contains(stable) ? stable : em.merge(stable);

        em.remove(attached);
        em.getTransaction().commit();
        em.close();
    }

    public Optional<Stable> findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Stable stable = em.find(Stable.class, id);
        em.close();
        return Optional.ofNullable(stable);
    }

    public Optional<Stable> findByName(String name) {
        EntityManager em = emf.createEntityManager();
        List<Stable> list = em.createQuery(
                "SELECT s FROM Stable s WHERE s.stableName = :name", Stable.class
        ).setParameter("name", name).getResultList();
        em.close();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public List<Stable> getAll() {
        EntityManager em = emf.createEntityManager();
        List<Stable> list = em.createQuery("SELECT s FROM Stable s", Stable.class).getResultList();
        em.close();
        return list;
    }

    public List<Stable> searchByNameFragment(String fragment) {
        EntityManager em = emf.createEntityManager();
        List<Stable> list = em.createQuery(
                        "SELECT s FROM Stable s WHERE LOWER(s.stableName) LIKE :frag", Stable.class
                ).setParameter("frag", "%" + fragment.toLowerCase() + "%")
                .getResultList();
        em.close();
        return list;
    }

    public List<Object[]> getHorseCountPerStable() {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();

            // SELECT s.name, COUNT(h)
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<Stable> stableRoot = cq.from(Stable.class);
            Join<Object, Object> horsesJoin = stableRoot.join("horseList", JoinType.LEFT);

            cq.multiselect(
                    stableRoot.get("stableName"),
                    cb.count(horsesJoin)
            );

            cq.groupBy(stableRoot.get("stableName"));

            return em.createQuery(cq).getResultList();

        } finally {
            em.close();
        }
    }

}
