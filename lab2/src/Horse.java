public class Horse implements Comparable<Horse> {
    private String name;
    private String breed;
    private HorseType type; // np. enum: COLD_BLOODED, HOT_BLOODED
    private HorseCondition status;
    private int age;
    private double price;
    private double weight;

    public Horse(String name, String breed, HorseType type, HorseCondition status, int age, double price, double weight) {
        this.name = name;
        this.breed = breed;
        this.type = type;
        this.status = status;
        this.age = age;
        this.price = price;
        this.weight = weight;
    }

    public void print() {
        System.out.printf("%s (%s, %s) - %d lat, %.2f zł, %.1f kg, status: %s%n",
                name, breed, type, age, price, weight, status);
    }

    @Override
    public int compareTo(Horse o) {
        int nameCmp = this.name.compareToIgnoreCase(o.name);
        if (nameCmp != 0) return nameCmp;

        int breedCmp = this.breed.compareToIgnoreCase(o.breed);
        if (breedCmp != 0) return breedCmp;

        return Integer.compare(this.age, o.age);
    }

    /* --- GETTERY & SETTER --- */

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public HorseType getType() {
        return type;
    }

    public HorseCondition getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(HorseCondition status) {
        this.status = status;
    }

    public void setType(HorseType type) {
        this.type = type;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
