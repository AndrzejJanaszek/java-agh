public class Circle extends Figure{
    private double r;

    public Circle(double r) {
        if (r <= 0) {
            throw new IllegalArgumentException("Promień musi być większy od zera.");
        }
        this.r = r;
    }

    @Override
    public double calculateArea() {
        return Math.PI * r * r;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * r;
    }

    @Override
    public void print() {
        System.out.println("Koło: r=" + r);
        System.out.println("Pole: " + calculateArea());
        System.out.println("Obwód: " + calculatePerimeter());
    }
}
