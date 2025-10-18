public class Prism implements Printable {
    private Figure base;
    private double height;

    public Prism(Figure base, double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Wysokość musi być większa od zera.");
        }
        this.base = base;
        this.height = height;
    }

    public double calculateVolume() {
        return base.calculateArea() * height;
    }

    public double calculateSurfaceArea() {
        return 2 * base.calculateArea() + base.calculatePerimeter() * height;
    }

    @Override
    public void print() {
        System.out.println("Graniastosłup o podstawie:");
        base.print();
        System.out.println("Wysokość: " + height);
        System.out.println("Pole powierzchni: " + calculateSurfaceArea());
        System.out.println("Objętość: " + calculateVolume());
    }
}
