package com.example.koniary.services;

import com.example.koniary.model.Horse;
import com.example.koniary.model.HorseCondition;
import com.example.koniary.model.HorseType;
import com.example.koniary.model.Stable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVService {

    private final EntityManagerFactory emf;

    public CSVService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /* ============================================================
       EXPORT STABLE → CSV
       ============================================================ */
    public void exportStableToCSV(Long stableId, File file) throws Exception {
        EntityManager em = emf.createEntityManager();

        try {
            Stable stable = em.find(Stable.class, stableId);
            if (stable == null) {
                throw new Exception("Stable with ID " + stableId + " not found.");
            }

            List<Horse> horses = stable.getHorseList(); // lazy OK, jesteśmy w sesji

            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                pw.println("name,breed,type,condition,age,price,weight");

                for (Horse h : horses) {
                    pw.printf("%s,%s,%s,%s,%d,%.2f,%.2f%n",
                            h.getName(),
                            h.getBreed(),
                            h.getType(),
                            h.getStatus(),
                            h.getAge(),
                            h.getPrice(),
                            h.getWeight()
                    );
                }
            }

        } finally {
            em.close();
        }
    }

    /* ============================================================
       IMPORT CSV → STABLE
       ============================================================ */
    public void importToStable(Long stableId, File file) throws Exception {

        List<Horse> horsesToAdd = new ArrayList<>();

        // === READ CSV ===
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine(); // skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] t = line.split(",");

                if (t.length != 7) continue;

                Horse h = new Horse(
                        t[0],               // name
                        t[1],               // breed
                        Enum.valueOf(HorseType.class, t[2]),
                        Enum.valueOf(HorseCondition.class, t[3]),
                        Integer.parseInt(t[4]),
                        Double.parseDouble(t[5]),
                        Double.parseDouble(t[6])
                );

                horsesToAdd.add(h);
            }
        }

        // === SAVE TO DATABASE ===
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Stable stable = em.find(Stable.class, stableId);
            if (stable == null) {
                throw new Exception("Stable with ID " + stableId + " not found.");
            }

            for (Horse h : horsesToAdd) {
                h.setStable(stable);
                em.persist(h);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;

        } finally {
            em.close();
        }
    }
}
