package com.example.koniary;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("koniaryPU");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
