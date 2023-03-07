import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("----------- Independent Linear system calculator -----------");

        System.out.print("\nEnter the order (number of variables) of the system: ");
        int order = sc.nextInt();

        System.out.println("\nEnter the coeficients of the equations (Ex.: Av1 + Bv2 + Cv3 = D => A B C D)");

        double[] resultsCoeficients = new double[order];
        SquareMatrix coeficientsMatrix = new SquareMatrix(order);
        double[] results = new double[order];

        for (int i = 0; i < order; i++) {

            System.out.println("\n-------- Equation" + (i + 1) + " -------------\n");

            double[] coeficients = new double[order];

            for (int j = 0; j <= order; j++) {

                if (j < order)
                    coeficients[j] = sc.nextDouble();
                else
                    resultsCoeficients[i] = sc.nextDouble();

            }

            coeficientsMatrix.setRow(coeficients, i + 1);

        }
        
        for (int i = 0; i < order; i++) {

            SquareMatrix variableMatrix = coeficientsMatrix.clone();

            variableMatrix.setColumn(resultsCoeficients, i + 1);
            results[i] = variableMatrix.det() / coeficientsMatrix.det();

        }

        for (int i = 0; i < results.length; i++) {
            System.out.printf("\nVar%d = %.2f", (i+1), results[i]);
        }

        sc.close();

    }

}