import java.util.Scanner;

public class FigureUI {
    private Figure fig;
    private boolean isFigureDataSet = false;

    private static final int TRIANGLE = 1;
    private static final int SQUARE = 2;
    private static final int CIRCLE = 3;
    private static final int NONE = -1;
    private int figureType = NONE;

    String getFigureName(){
        switch (figureType) {
            case TRIANGLE:
                return "TRÓJKĄT";
            case SQUARE:
                return "KWADRAT";
            case CIRCLE:
                return "KOŁO";
            default:
                return "BRAK";
        }
    }

    private int chooseFigure(){
        int choice = NONE;
        System.out.println("\n----------------------------------------");
        System.out.println("MENU >> WYBÓR FIGURY:");
        System.out.println("1) Trójkąt");
        System.out.println("2) Kwadrat");
        System.out.println("3) Koło");
        System.out.println("4) Wróć");
        System.out.print("Wybór: ");

        Scanner sc = new Scanner(System.in);
        choice = sc.nextInt();

        switch (choice) {
            case TRIANGLE:
                this.figureType = TRIANGLE;
                break;
            case SQUARE:
                this.figureType = SQUARE;
                break;
            case CIRCLE:
                this.figureType = CIRCLE;
                break;
            case 4:
                return -1;
        }

        this.inputFigureData();

        return choice;
    }

    private int inputFigureData(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\n----------------------------------------");
        System.out.println("MENU >> PODANIE DANYCH:");
        double a,b,c;
        switch(figureType){
            case 1:
                System.out.print("Podaj bok a: ");
                a = sc.nextDouble();
                System.out.print("Podaj bok b: ");
                b = sc.nextDouble();
                System.out.print("Podaj bok c: ");
                c = sc.nextDouble();

                this.fig = new Triangle(a,b,c);
                break;
            case 2:
                System.out.print("Podaj bok a: ");
                a = sc.nextDouble();
                this.fig = new Square(a);
                break;
            case 3:
                System.out.print("Podaj bok promień: ");
                a = sc.nextDouble();
                this.fig = new Circle(a);
                break;
            default:
                System.out.println("Brak wybranej figury\n Najpierw wybierz rodzaj figury");
                return -1;
        }

        this.isFigureDataSet = true;

        return 0;
    }

    private int printFigureData() {
        System.out.println("\n----------------------------------------");
        System.out.println("MENU >> DANE TWOJEJ FIGURY:");
        if(!isFigureDataSet){
            System.out.println("Figura nie ma danych do wyświetlenia lub jest nie wybrana.");
            return -1;
        }

        fig.print();
        return 0;
    }

    private void waitForEnter(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Naciśnij Enter, aby kontynuować...");
        sc.nextLine();
    }

    void start(){
        Scanner sc = new Scanner(System.in);
        int choice;

        while(true){
            System.out.println("\n----------------------------------------");
            System.out.println("MENU:");
            System.out.println("Wybrana figura: " + this.getFigureName());
            System.out.println("1) Wybór figury");
            System.out.println("2) Zmiana danych");
            System.out.println("3) Wyświetlenie danych");
            System.out.println("4) exit");
            System.out.print("Wybór: ");

            try {
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        this.chooseFigure();
                        break;
                    case 2:
                        this.inputFigureData();
                        break;
                    case 3:
                        this.printFigureData();
                        break;
                    case 4:
                        System.out.println("KONIEC PROGRAMU:");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Niepoprawny znak.\nPodaj liczbę całkowitą od 1 do 4\n");
                        break;
                }
            }catch (Exception e){
                System.out.println("[BŁĄD:]");
                System.out.println(e.getMessage());
            }

            this.waitForEnter();
        }
    }
}
