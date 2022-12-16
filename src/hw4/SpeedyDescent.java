package hw4;

public class SpeedyDescent {

    private static final int MAX_AMOUNT_OPERATION = 50000;

    public void findSolution(double[][] initialMatrix, double[] freeMembers,
                             double[] solution, int n, int numOfNorm) {

        String epsilon = "10^(-" + n + ")";
        double[] prevXValues; // Предыдущие приближения
        double[] rValues; // Вектор невязки
        double paramT; // Длина шага вдоль направления градиента
        int iterationsNumber = 0; // Количество итераций
        double norm; // Норма
        int size = initialMatrix.length; // Размерность исходной матрицы
        double[] currentXValues = solution;  // Текущие приближения

        do {
            rValues = subtracting(multiply(initialMatrix, currentXValues), freeMembers);
            paramT = getTauForGradient(initialMatrix, rValues);
            prevXValues = currentXValues;
            currentXValues = subtracting(currentXValues, findResidualVector(paramT, rValues));
            norm = IterationMethodsMain.findNorm(numOfNorm, size, currentXValues, prevXValues);
            ++iterationsNumber;
        } while (norm > Math.pow(10, -n) && iterationsNumber < MAX_AMOUNT_OPERATION);

        // Вывод результатов вычислений
        System.out.println(printResult(currentXValues, epsilon, iterationsNumber));
    }

    private double getTauForGradient(double[][] matrix, double[] residual) {
        double[] Ar = multiply(matrix, residual);
        double Arr = multiply(Ar, residual);
        double rr = multiply(residual, residual);
        return rr / Arr;
    }

    // Произведение двух матриц
    private double[] multiply(double[][] matrix, double[] x) {
        double[] f = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                f[i] += matrix[i][j] * x[j];
            }
        }
        return f;
    }

    // Скалярное произведение
    private double multiply(double[] vector, double[] x) {
        double f = 0;
        for (int i = 0; i < vector.length; i++) {
            f += vector[i] * x[i];
        }
        return f;
    }

    // Вычисление разности двух матриц
    private double[] subtracting(double[] first, double[] second) {
        double[] result = new double[first.length];
        for (int i = 0; i < first.length; ++i) {
            result[i] = first[i] - second[i];
        }
        return result;
    }

    // Вычисление вектора невязки путем умножения матрицы на вектор
    private double[] findResidualVector(double param, double[] actual) {
        double[] residualVector = new double[actual.length];
        for (int i = 0; i < actual.length; ++i) {
            residualVector[i] = param * actual[i];
        }
        return residualVector;
    }

    // Вывод полученных результатов
    public String printResult(double[] previousVariableValues, String epsilon, int iterationsNumber) {
        StringBuilder result = new StringBuilder();
        for (double qst : previousVariableValues) {
//            result.append("[").append(Math.round(qst)).append("]; ");
            result.append("[").append(qst).append("]; ");
        }
        if (iterationsNumber != 500) {
            return result + " ε = " + epsilon + " итераций: " + iterationsNumber;
        }
        return null;
    }
}