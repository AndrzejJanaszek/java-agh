import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n### METODY DLA STABLE MANAGERA ### ");
        StableManager stableManager = new StableManager();
        stableManager.addStable("Stadnina Koni w Żyrardowie", 12);
        stableManager.addStable("Stadnina Koni w Krakowie", 17);
        stableManager.addStable("Małopolska Stadnina Źrebaków", 7);
        stableManager.addStable("Intergalaktyczna Stadnina Jednorożców", 420);

        System.out.println("\n### STABLE MANAGER PO DODANIU 4 STADNIN ### ");
        stableManager.summary();

        System.out.println("\n### USUNIECIE STADNINY ZE ŹREBAKAMI ### ");
        stableManager.removeStable("Małopolska Stadnina Źrebaków");

        System.out.println("\n### WYPISANIE PUSTYCH ### ");
        stableManager.findEmpty().forEach(Stable::summary);

        System.out.println("\n### WYSZUKANIE ### ");
        Stable s1 = stableManager.search("Stadnina Koni w Żyrardowie").orElse(null);
        if(s1 != null){
            s1.summary();
        }
        else{
            System.out.println("Błąd");
        }
        List<Stable> s2 = stableManager.searchPartial("Jednorożców");

        s2.forEach(Stable::summary);


        System.out.println("\n### STADNINA ### ");
        s1.addHorse(new Horse("Płotka", "Koń Rasowy Wiedźminowy", HorseType.HOT_BLOODED, HorseCondition.HEALTHY, 18, 2137, 420.77));
        s1.addHorse(new Horse("Mirek", "Koń typu koń", HorseType.HOT_BLOODED, HorseCondition.HEALTHY, 21, 1133, 666));
        s1.addHorse(new Horse("Kage", "Mochizuke", HorseType.COLD_BLOODED, HorseCondition.HEALTHY, 37, 2222, 512));
        s1.summary();
        System.out.println("\n### PO USUNIECIU KONIA ### ");
//        s1.removeHorse(s1.getHorseList().get(1));
        s1.summary();

        System.out.println();
        s1.max().print();

        System.out.println("----------------------");
        s1.printHotBloodedHorses();

    }
}