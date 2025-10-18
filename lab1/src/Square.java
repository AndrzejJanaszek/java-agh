public class Square extends Figure{
    private double a;

    public Square(double a) {
        if (a <= 0) {
            throw new IllegalArgumentException("Długość boku musi być wieksza od zera.");
        }
        this.a = a;
    }

    @Override
    public double calculateArea() {
        return a * a;
    }

    @Override
    public double calculatePerimeter() {
        return 4 * a;
    }

    @Override
    public void print() {
        System.out.println("Kwadrat: bok=" + a);
        System.out.println("Pole: " + calculateArea());
        System.out.println("Obwód: " + calculatePerimeter());
    }
}
