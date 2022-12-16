package hw4;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class IterationMethodsMain {

    private static final int AMOUNT_OF_NORM = 3;
    private static final String[] NAME_NORM_LIST = new String[]{"||X||∞", "||X||₁", "||X||₂ₗ"};
    private static final int L_PARAM_VALUE = 2;
    private static final int DEGREE_VALUE_FOR_EPSILON = 9;

    public static void main(String[] args) {
        LinkedList<double[][]> listMainMatrixes = new LinkedList<>(); // Коллекция из массивов систем
        LinkedList<double[]> listFreeMembers = new LinkedList<>(); // Коллекция из столбцов свободных членов
        fileReading(listMainMatrixes, listFreeMembers);
        LinkedList<double[]> listOfSolutions = new LinkedList<>(); // Начальные приближения
        listOfSolutions.add(new double[]{1, 1.1, -1, -1});
        listOfSolutions.add(new double[]{-0.8, 0, 0.3, 0.7});
        listOfSolutions.add(new double[]{0.1, 0.2, -0.1, 0.3});
        listOfSolutions.add(new double[]{0.5, 0.7, 1.7, 1.7, 1.8});
        listOfSolutions.add(new double[]{0.9, 1.9, 0.9, 1.9, 0.9, 1.9});
        listOfSolutions.add(new double[]{0.4, 1.4, 0.4, 1.5, 0.2, 1.5});

        MinimumResiduals mr = new MinimumResiduals();
        SpeedyDescent sd = new SpeedyDescent();
        for (int i = 0; i < listMainMatrixes.size(); ++i) {
            System.out.println("№ " + (i + 1));
            RealMatrix a = MatrixUtils.createRealMatrix(listMainMatrixes.get(i));
            for (int j = 0; j < AMOUNT_OF_NORM; ++j) {
                //!checkSymmetric(listMainMatrixes.get(i), 4)
//                double[][] transposeMatrix = transposeMatrix(listMainMatrixes.get(i));
                double[][] transposeMatrix = a.transpose().getData();
                if (!Arrays.deepEquals(transposeMatrix, listMainMatrixes.get(i))) {
                    System.out.println("\nCAST TO SYMMETRIC");

                    double[][] bufferNewMatrix = multiplyMatrices(listMainMatrixes.get(i), transposeMatrix);
                    System.out.println("========== RESULT MATRIX ==========");
                    showMatrix(bufferNewMatrix, 4);
                    double[] bufferNewFreeMembers = multiply(transposeMatrix, listFreeMembers.get(i));
                    System.out.println("========== OLD FREE ==========");
                    showVector(listFreeMembers.get(i));
                    System.out.println("\n========== RESULT FREE ==========");
                    showVector(bufferNewFreeMembers);
                    listMainMatrixes.remove(i);
                    listFreeMembers.remove(i);
                    listMainMatrixes.add(i, bufferNewMatrix);
                    listFreeMembers.add(i, bufferNewFreeMembers);
                }
                System.out.println();
                System.out.println("Норма " + NAME_NORM_LIST[j]);
                // Вывод результатов
                System.out.println("Метод минимальных невязок");
                mr.findSolution(listMainMatrixes.get(i), listFreeMembers.get(i),
                        listOfSolutions.get(i), DEGREE_VALUE_FOR_EPSILON, (j + 1));
                System.out.println();
                System.out.println("Метод наискорейшего спуска");
                sd.findSolution(listMainMatrixes.get(i), listFreeMembers.get(i), listOfSolutions.get(i), DEGREE_VALUE_FOR_EPSILON, (j + 1));
                System.out.println();
            }
            System.out.println();
        }
    }

    // Чтение файла, содержащего системы уравнений
    public static void fileReading(LinkedList<double[][]> listMainMatrixes, LinkedList<double[]> listFreeMembers) {
        File file = new File("src/hw4/input.txt");
        if (file.length() == 0) {
            System.out.println("Файл пуст");
        } else {
            ArrayList<String[]> coefficients = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String str;
                while ((str = reader.readLine()) != null) {
                    if (!str.equals("")) {
                        coefficients.add(str.split(" "));
                    } else {
                        fillingListOfMatrices(coefficients, listMainMatrixes, listFreeMembers);
                        coefficients.clear();
                    }
                }
                // Заполнение последним массивов
                fillingListOfMatrices(coefficients, listMainMatrixes, listFreeMembers);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // Заполнение коллекции с массивами систем
    public static void fillingListOfMatrices(ArrayList<String[]> coefficients, LinkedList<double[][]> listMainMatrixes, LinkedList<double[]> listFreeMembers) {
        int columns = coefficients.get(0).length;
        double[][] system1 = new double[coefficients.size()][columns - 1];
        double[] system2 = new double[coefficients.size()];
        Iterator<String[]> iter = coefficients.iterator();
        for (int i = 0; i < system1.length; ++i) {
            String[] s = iter.next();
            for (int j = 0; j < columns; ++j) {
                if (j == columns - 1) {
                    system2[i] = Double.parseDouble(s[j]);
                    continue;
                }
                system1[i][j] = Double.parseDouble(s[j]);
            }
        }
        listMainMatrixes.add(system1);
        listFreeMembers.add(system2);
    }

    // Поиск нормы исходя из текущей рассчитываемой нормы
    public static double findNorm(int numOfNorm, int size, double[] currentVariableValues, double[] previousVariableValues) {
        double norm = 0;
        switch (numOfNorm) {
            // Для нормы ||X||∞
            case (1) -> {
                double[] values = new double[size];
                for (int i = 0; i < size; ++i) {
                    values[i] = Math.abs(currentVariableValues[i] - previousVariableValues[i]);
                }
                DoubleSummaryStatistics stat = Arrays.stream(values).summaryStatistics();
                return stat.getMax();
            }
            // Для нормы ||X||₁
            case (2) -> {
                for (int i = 0; i < size; ++i) {
                    norm += Math.abs(currentVariableValues[i] - previousVariableValues[i]);
                }
                return norm;
            }
            // Для нормы ||X||₂ₗ
            case (3) -> {
                for (int i = 0; i < size; ++i) {
                    norm += Math.pow(currentVariableValues[i] - previousVariableValues[i], 2 * L_PARAM_VALUE);
                }
                return Math.pow(norm, 1. / (2 * L_PARAM_VALUE));
            }
        }
        return 0;
    }

    private static double[] multiply(double[][] matrix, double[] x) {
        double[] f = new double[matrix.length];
        for (int j = 0; j < x.length; j++) {
            for (int i = 0; i < matrix.length; i++) {
                f[j] += matrix[i][j] * x[i];
            }
        }
        return f;
    }

    private static boolean checkSymmetric(double[][] mat, int LENGTH) {
        int i, j, flag = 1;
        double[][] transpose = new double[LENGTH][LENGTH];
        for (i = 0; i < LENGTH; i++) {
            for (j = 0; j < LENGTH; j++) {
                transpose[j][i] = mat[i][j];
            }
        }
        for (i = 0; i < LENGTH; i++) {
            for (j = 0; j < LENGTH; j++) {
                if (mat[i][j] != transpose[i][j]) {
                    flag = 0;
                    break;
                }
            }
            if (flag == 0) {
                System.out.print(
                        "\nThe matrix is not symmetric");
                return false;
            }
        }
        // Display message
        System.out.print(
                "\nThe matrix is symmetric");
        return true;
    }

    private static double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];

        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        return result;
    }

    private static double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    private static double[][] transposeMatrix(double[][] m) {
        double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
//        System.out.println("=========== TRANSPOSE ==========");
//        showMatrix(temp, 4);
        return temp;
    }

    private static void showMatrix(double[][] mat, int LENGTH) {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void showVector(double[] mat) {
        for (int i = 0; i < mat.length; i++) {
            System.out.print(mat[i] + " ");
        }
    }
}
