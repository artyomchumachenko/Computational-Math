package hw3;

import java.util.LinkedList;
import java.util.List;

public class MethodGuess {

    public static void main(String[] args) {
        List<double[][]> eqs = new LinkedList<>();
        eqs.add(
                new double[][]{{2, 2, -1, 1}, {4, 3, -1, 2}, {8, 5, -3, 4}, {3, 3, -2, 2}}
        );
        eqs.add(
                new double[][]{{1, 7, -9, -8}, {-3, -18, 23, 28}, {0, -3, 6, -1}, {-1, -1, 1, 18}}
        );
        eqs.add(
                new double[][]{{3, -3, 7, -4}, {-6, 9, -21, 9}, {9, -12, 30, -22}, {6, 0, 6, -31}}
        );
        eqs.add(
                new double[][]{{9, -5, -6, 3}, {1, -7, 1, 0}, {3, -4, 9, 0}, {6, -1, 9, 8}}
        );
        eqs.add(
                new double[][]{{-6, -5, -3, -8}, {5, -1, -5, -4}, {-6, 0, 5, 5}, {-7, -2, 8, 5}}
        );
        List<double[]> rightSideEqs = new LinkedList<>();
        rightSideEqs.add(
                new double[]{4, 6, 12, 6}
        );
        rightSideEqs.add(
                new double[]{-7, 5, 8, -29}
        );
        rightSideEqs.add(
                new double[]{0, 9, -2, 37}
        );
        rightSideEqs.add(
                new double[]{-8, 38, 47, -8}
        );
        rightSideEqs.add(
                new double[]{101, 51, -53, -63}
        );

        /* Ввод данных */

        int numEquations = 4;
        int numUnknowns = 4;
        int valueIndex = 0;
        double[][] A = new double[numUnknowns][numUnknowns];
        double[] b = new double[numUnknowns];
        for (double[][] mainMatrix : eqs) {
            double[] rightSideOfMatrix = rightSideEqs.get(valueIndex);
            valueIndex++;
            for (int currI = 0; currI < numEquations; currI++) {
                System.arraycopy(mainMatrix[currI], 0, A[currI], 0, numUnknowns);
                b[currI] = rightSideOfMatrix[currI];
            }
            /* Метод Гаусса */

            for (int p = 0; p < numEquations; p++) {
                int max = p;
                for (int i = p + 1; i < numEquations; i++) {
                    if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                        max = i;
                    }
                }
                double[] temp = A[p];
                A[p] = A[max];
                A[max] = temp;
                double t = b[p];
                b[p] = b[max];
                b[max] = t;

                if (Math.abs(A[p][p]) <= 1e-10) {
                    System.out.println("NO");
                    return;
                }

                for (int i = p + 1; i < numEquations; i++) {
                    double alpha = A[i][p] / A[p][p];
                    b[i] -= alpha * b[p];
                    for (int j = p; j < numEquations; j++) {
                        A[i][j] -= alpha * A[p][j];
                    }
                }
            }

            // Обратный проход

            double[] x = new double[numEquations];
            for (int i = numEquations - 1; i >= 0; i--) {
                double sum = 0.0;
                for (int j = i + 1; j < numEquations; j++) {
                    sum += A[i][j] * x[j];
                }
                x[i] = (b[i] - sum) / A[i][i];
            }

            /* Вывод результатов */

            System.out.println("YES");
            for (int i = 0; i < numEquations; i++) {
                System.out.printf("%.2f    ", x[i]);
            }
            System.out.println("\n-----------------------------------------");
        }
    }
}
