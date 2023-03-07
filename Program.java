import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("----------- Independent Linear system calculator -----------");

        System.out.print("\nEnter the order (number of variables) of the system: ");
        int order = sc.nextInt();

        System.out.println("\nEnter the coeficients of the equations (Ex.: Ax + By + Cz = D => [A B C D])");

        double[] equation = new double[order + 1];

        for (int i = 0; i < order; i++) {

            System.out.println("\n-------- Equation" + (i + 1) + " -------------\n");

            for (int j = 0; j <= order; j++) {

                equation[j] = sc.nextDouble();

            }

        }

        sc.close();
    }

}