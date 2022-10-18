package hw3;

import java.util.LinkedList;
import java.util.List;

public class SimpleDimple {

    public static void main(String[] args) {

        List<double[][]> eqs = new LinkedList<>();
        eqs.add(
                new double[][]{{12, -3, -1, 3}, {5, 20, 9, 1}, {6, -3, -21, -7}, {8, -7, 3, -27}}
        );
        eqs.add(
                new double[][]{{28, 9, -3, -7}, {-5, 21, -5, -3}, {-8, 1, -16, 5}, {0, -2, 5, 8}}
        );
        eqs.add(
                new double[][]{{21, 1, -8, 4}, {-9, -23, -2, 4}, {7, -1, -17, 6}, {8, 8, -4, -26}}
        );
        eqs.add(
                new double[][]{{14, -4, -2, 3}, {-3, 23, -6, -9}, {-7, -8, 21, -5}, {-2, -2, 8, 18}}
        );
        List<double[]> value = new LinkedList<>();
        value.add(
                new double[]{-31, 90, 119, 71}
        );
        value.add(
                new double[]{-159, 63, -45, 24}
        );
        value.add(
                new double[]{-119, 79, -24, -52}
        );
        value.add(
                new double[]{38, -195, -27, 142}
        );

        double eps = 0.0000000001;
        int valueIndex = 0;
        for (double[][] mas : eqs) {
            double x1 = 0, x2 = 0, x3 = 0, x4 = 0;
            double[] x = new double[4];
            int count;
            double[] val = value.get(valueIndex);
            valueIndex++;

            count = 0;
            do {
                x[0] = (
                        val[0] - (mas[0][1] * x2 + mas[0][2] * x3 + mas[0][3] * x4) / mas[0][0]
                );
                x[1] = (
                        val[1] - (mas[1][0] * x1 + mas[1][2] * x3 + mas[1][3] * x4) / mas[1][1]
                );
                x[2] = (
                        val[2] - (mas[2][0] * x1 + mas[2][1] * x2 + mas[2][3] * x4) / mas[2][2]
                );
                x[3] = (
                        val[3] - (mas[3][0] * x1 + mas[3][1] * x2 + mas[3][2] * x3) / mas[3][3]
                );
                count++;
                if (
                        (Math.abs(x[0] - x1) < eps)
                                && (Math.abs(x[1] - x2) < eps)
                                && (Math.abs(x[2] - x3) < eps)
                                && (Math.abs(x[3] - x4) < eps)
                ) {
                    break;
                }
                x1 = x[0];
                x2 = x[1];
                x3 = x[2];
                x4 = x[3];
            } while (true);
            x1 = x[0];
            x2 = x[1];
            x3 = x[2];
            x4 = x[3];
            System.out.println(count + "    - count");
            System.out.printf("%.2f    %.2f    %.2f    %.2f", x1, x2, x3, x4);
            System.out.println("\n___________________");
        }
    }
}
