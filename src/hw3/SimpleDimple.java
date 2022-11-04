package hw3;

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
        double[][] currFullMatrix;
        double[] freeMembers;
        double[] xSolutions;
        int amountUnknowns = 4;
        int i, j;
        freeMembers = new double[amountUnknowns];
        currFullMatrix = new double[amountUnknowns][amountUnknowns];
        for (i = 0; i < amountUnknowns; i++) {
            for (j = 0; j <= amountUnknowns; j++) {
                if (j != amountUnknowns) {
                    currFullMatrix[i][j] = fullMatrix[i][j];
                } else {
                    freeMembers[i] = fullMatrix[i][j];
                }
            }
        }
//        for (i = 0; i < amountUnknowns; i++) {
//            for (j = 0; j <= amountUnknowns; j++) {
//                if (j != amountUnknowns) {
//                    System.out.print(currFullMatrix[i][j] + "\t");
//                } else {
//                    System.out.print(freeMembers[i] + "\t");
//                }
//            }
//            System.out.println();
//        }
        xSolutions = iter(currFullMatrix, freeMembers, amountUnknowns);
        for (i = 0; i < amountUnknowns; i++) {
            System.out.printf("%.2f\t", xSolutions[i]);
        }
        System.out.println("Iter = " + amountIters);
        System.out.println("END");
    }

    private static int amountIters;
    private static double[] iter(double[][] currFullMatrix, double[] freeMembers, int amountUnknowns) {
        amountIters = 0;
        double[] res = new double[amountUnknowns];
        int i, j;
        // нулевое приближение
        for (i = 0; i < amountUnknowns; i++) {
            res[i] = freeMembers[i] / currFullMatrix[i][i];
        }
        double eps = 0.0000001;
        double[] Xn = new double[amountUnknowns];
        do {
            for (i = 0; i < amountUnknowns; i++) {
                Xn[i] = freeMembers[i] / currFullMatrix[i][i];
                for (j = 0; j < amountUnknowns; j++) {
                    if (i != j) {
                        Xn[i] -= currFullMatrix[i][j] / currFullMatrix[i][i] * res[j];
                    }
                }
            }

            boolean flag = true;
            for (i = 0; i < amountUnknowns - 1; i++) {
                if (Math.abs(Xn[i] - res[i]) > eps) {
                    amountIters++;
                    flag = false;
                    break;
                }
            }
            for (i = 0; i < amountUnknowns; i++) {
                res[i] = Xn[i];
            }
            if (flag) {
                break;
            }
        } while (true);
        return res;
    }
}
