package hw3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Zeidel {

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

        for (double[][] matrix : eqs) {
            double[] results = findSolution(matrix);
            for (double res : results) {
                System.out.printf("%.2f\t", res);
            }
            System.out.println("\n______________________________________-");
        }
    }

    static double EPS = 0.0000001;

    public static double[] findSolutionReq(double[][] matrix) {
        return findSolution(matrix);
    }

    public static double[] findSolution(double[][] matrix) {
        int size = matrix.length;
        double[] previousVariableValues = new double[size];
        for (int i = 0; i < size; i++) {
            previousVariableValues[i] = 0.0;
        }
        // Будем выполнять итерационный процесс до тех пор,
        // пока не будет достигнута необходимая точность
        while (true) {
            // Введем вектор значений неизвестных на текущем шаге
            double[] currentVariableValues = new double[size];

            // Посчитаем значения неизвестных на текущей итерации
            // в соответствии с теоретическими формулами
            for (int i = 0; i < size; i++) {
                // Инициализируем i-ую неизвестную значением
                // свободного члена i-ой строки матрицы
                currentVariableValues[i] = matrix[i][size];

                // Вычитаем сумму по всем отличным от i-ой неизвестным
                for (int j = 0; j < size; j++) {
                    // При j < i можем использовать уже посчитанные
                    // на этой итерации значения неизвестных
                    if (j < i) {
                        currentVariableValues[i] -= matrix[i][j] * currentVariableValues[j];
                    }

                    // При j > i используем значения с прошлой итерации
                    if (j > i) {
                        currentVariableValues[i] -= matrix[i][j] * previousVariableValues[j];
                    }
                }

                // Делим на коэффициент при i-ой неизвестной
                currentVariableValues[i] /= matrix[i][i];
            }

            // Посчитаем текущую погрешность относительно предыдущей итерации
            double error = 0.0;

            for (int i = 0; i < size; i++) {
                error += Math.abs(currentVariableValues[i] - previousVariableValues[i]);
            }

            // Если необходимая точность достигнута, то завершаем процесс
            if (error < Zeidel.EPS) {
                break;
            }

            // Переходим к следующей итерации, так
            // что текущие значения неизвестных
            // становятся значениями на предыдущей итерации
            previousVariableValues = currentVariableValues;
        }

        return previousVariableValues;
    }
}