package hw3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SimpleDimple {

    public static void main(String[] args) {

        List<double[][]> eqs = new LinkedList<>();
        eqs.add(
                new double[][]{{12, -3, -1, 3, -31}, {5, 20, 9, 1, 90}, {6, -3, -21, -7, 119}, {8, -7, 3, -27, 71}}
        );
        eqs.add(
                new double[][]{{28, 9, -3, -7, -159}, {-5, 21, -5, -3, 63}, {-8, 1, -16, 5, -45}, {0, -2, 5, 8, 24}}
        );
        eqs.add(
                new double[][]{{21, 1, -8, 4, -119}, {-9, -23, -2, 4, 79}, {7, -1, -17, 6, -24}, {8, 8, -4, -26, -52}}
        );
        eqs.add(
                new double[][]{{14, -4, -2, 3, 38}, {-3, 23, -6, -9, -195}, {-7, -8, 21, -5, -27}, {-2, -2, 8, 18, 142}}
        );

        // Solution
        for (double[][] matx : eqs) {
            simpleDimple(matx);
        }
    }

    private static void simpleDimple(double[][] fullMatrix) {
        double[][] a; //fullMatrix;
        double[] y; //freeChlens;
        double[] x; // xSolutions;
        int n = 4; // numOfEqs
        int i, j;
        y = new double[n];
        a = new double[n][n];
        for (i = 0; i < n; i++) {
            for (j = 0; j <= n; j++) {
                if (j != n) {
                    a[i][j] = fullMatrix[i][j];
                } else {
                    y[i] = fullMatrix[i][j];
                }
            }
        }
//        for (i = 0; i < n; i++) {
//            for (j = 0; j <= n; j++) {
//                if (j != n) {
//                    System.out.print(a[i][j] + "\t");
//                } else {
//                    System.out.print(y[i] + "\t");
//                }
//            }
//            System.out.println();
//        }
        x = iter(a, y, n);
        for (i = 0; i < n; i++) {
            System.out.printf("%.2f\t", x[i]);
        }
        System.out.println("END");
    }

    private static double[] iter(double[][] a, double[] y, int n) {
        double[] res = new double[n];
        int i, j;
        for (i = 0; i < n; i++) {
            res[i] = y[i] / a[i][i];
        }
        double eps = 0.0000001;
        double[] Xn = new double[n];
        do {
            for (i = 0; i < n; i++) {
                Xn[i] = y[i] / a[i][i];
                for (j = 0; j < n; j++) {
                    if (i != j) {
                        Xn[i] -= a[i][j] / a[i][i] * res[j];
                    }
                }
            }

            boolean flag = true;
            for (i = 0; i < n - 1; i++) {
                if (Math.abs(Xn[i] - res[i]) > eps) {
                    flag = false;
                    break;
                }
            }
            for (i = 0; i < n; i++) {
                res[i] = Xn[i];
            }
            if (flag) {
                break;
            }
        } while (true);
        return res;
    }
}
