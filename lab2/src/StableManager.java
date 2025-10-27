import java.util.*;

public class StableManager {
    private Map<String, Stable> stables = new HashMap<>();

    public void addStable(String name, int capacity) {
        stables.put(name, new Stable(name, capacity));
    }

    public void removeStable(String name) {
        stables.remove(name);
    }

    public List<Stable> findEmpty() {
        return stables.values().stream().filter(Stable::isEmpty).toList();
    }

    public void summary() {
        stables.forEach((name, stable) -> {
            double fill = (double) stable.sortByName().size() / stable.getMaxCapacity() * 100;
            System.out.printf("%s – zapełnienie: %.1f%%%n", name, fill);
        });
    }

    public Optional<Stable> search(String name) {
        return Optional.ofNullable(stables.get(name));
    }

    public List<Stable> searchPartial(String fragment) {
        return stables.entrySet().stream()
                .filter(e -> e.getKey().toLowerCase().contains(fragment.toLowerCase()))
                .map(Map.Entry::getValue)
                .toList();
    }

    public Map<String, Stable> getStables() {
        return stables;
    }
}
