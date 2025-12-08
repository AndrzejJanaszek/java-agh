package com.example.koniary.model;

import com.example.koniary.exceptions.*;
import com.example.koniary.services.HorseDAO;
import com.example.koniary.services.StableDAO;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

public class StableManager {
    private final StableDAO stableDAO;
    private final HorseDAO horseDAO;

    public StableManager(EntityManagerFactory emf) {
        this.stableDAO = new StableDAO(emf);
        this.horseDAO = new HorseDAO(emf);
    }

    /* =====================================================
     *  STABLE OPERATIONS
     * ===================================================== */

    public void addStable(String name, int capacity) throws StableAlreadyExistsException {
        if (stableDAO.findByName(name).isPresent()) {
            throw new StableAlreadyExistsException("Stajnia \"" + name + "\" już istnieje!");
        }

        Stable stable = new Stable(name, capacity);
        stableDAO.save(stable);
    }

    public void removeStable(String name) throws StableNotFoundException {
        Optional<Stable> found = stableDAO.findByName(name);
        if (found.isEmpty()) {
            throw new StableNotFoundException("Nie znaleziono stajni: " + name);
        }

        stableDAO.delete(found.get());
    }

    public List<Stable> getAllStables() {
        return stableDAO.getAll();
    }

    public Optional<Stable> search(String name) {
        return stableDAO.findByName(name);
    }

    public List<Stable> searchPartial(String fragment) {
        return stableDAO.searchByNameFragment(fragment);
    }


    /* =====================================================
     *  HORSE OPERATIONS
     * ===================================================== */

    public void addHorseToStable(Long stableId, Horse horse)
            throws StableNotFoundException, StableFullException, HorseAlreadyExistsException {

        Stable stable = stableDAO.findById(stableId)
                .orElseThrow(() -> new StableNotFoundException("Stajnia o ID " + stableId + " nie istnieje"));

        // Walidacja pojemności
        if (stable.getHorseList().size() >= stable.getMaxCapacity()) {
            throw new StableFullException("Stajnia \"" + stable.getStableName() + "\" jest pełna!");
        }

        // Sprawdzenie duplikatu
        boolean exists = stable.getHorseList().stream()
                .anyMatch(h -> h.getName().equalsIgnoreCase(horse.getName()));

        if (exists) {
            throw new HorseAlreadyExistsException("Koń o imieniu " + horse.getName() + " już istnieje w tej stajni!");
        }

        // Połączenie
        stable.addHorse(horse);

        stableDAO.update(stable);
    }

    public void removeHorse(Long horseId) throws HorseNotFoundException {
        Horse horse = horseDAO.findById(horseId)
                .orElseThrow(() -> new HorseNotFoundException("Koń o ID " + horseId + " nie istnieje"));

        horseDAO.delete(horse);
    }

    /* =====================================================
     *  DEFAULT DATA INITIALIZER
     * ===================================================== */

    public void loadDefaultData() {

        // Jeśli chcesz — TU możesz zrobić insert defaultowych danych do bazy.
        // Ale NIE w pamięci!
        // To jest tylko przykład prostych danych startowych:

        if (!stableDAO.getAll().isEmpty()) return; // nie dodawaj duplikatów

        try {
            Stable s1 = new Stable("Stadnina Jednorożców", 8);
            Stable s2 = new Stable("Wiedźmińska Zagroda", 12);
            Stable s3 = new Stable("Zagroda Podlaska", 6);

            stableDAO.save(s1);
            stableDAO.save(s2);
            stableDAO.save(s3);

        } catch (Exception e) {
            System.err.println("Błąd inicjalizacji danych: " + e.getMessage());
        }
    }
}
