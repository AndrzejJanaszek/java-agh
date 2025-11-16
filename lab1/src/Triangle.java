public class Triangle extends Figure{
    private double a, b, c;

    public Triangle(double a, double b, double c) {
        if(a > 8){
            throw new IllegalArgumentException("Trójkąt jest za duży.");
        }
        if (a <= 0 || b <= 0 || c <= 0 || a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException("Niepoprawne długości boków trójkąta.");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculateArea() {
        double p = calculatePerimeter() / 2.0;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    @Override
    public double calculatePerimeter() {
        return a + b + c;
    }

    @Override
    public void print() {
        System.out.println("Trójkąt: a=" + a + ", b=" + b + ", c=" + c);
        System.out.println("Pole: " + calculateArea());
        System.out.println("Obwód: " + calculatePerimeter());
    }
}
