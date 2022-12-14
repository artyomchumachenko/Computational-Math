package hw2;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class HomeWork2 {
    public static final int A = 2;
    public static final int epsDegree = 4;
    public static final double EPS = Math.pow(10, -epsDegree);

    public static final int START_POINT_OF_SEGMENT = -10;
    public static final int FINISH_POINT_OF_SEGMENT = 10;
    public static final double SEARCH_SEGMENT_STEP = 0.5;
    public static final int AMOUNT_CH_PROIZVODNIH = 4;

    public static void main(String[] args) {

        LinkedList<Operationable1> equations1 = new LinkedList<>(); // Исходные уравнения задания 1
        LinkedList<Operationable21> equations21 = new LinkedList<>(); // Система уравнений задания 2.1
        LinkedList<Operationable22> equations22 = new LinkedList<>(); // Система уравнений задания 2.2
        LinkedList<Operationable1> derivatives1 = new LinkedList<>(); // Первые производные уравненений задания 1
        LinkedList<Operationable21> derivatives21 = new LinkedList<>(); // Частные производные задания 2.1
        LinkedList<Operationable22> derivatives22 = new LinkedList<>(); // Частные производные задания 2.2
        // Набор уравнений задания 1.1 в формате String для вывода
        String[] equationsStr1 = new String[]{
                "x^3+x^2-x+1/2=0",
                "(e^x)/A=x+1",
                "x^3-20x+1=0",
                "2^x+x^2-2=0",
                "xln(x+2)-1+x^2=0",
                "(x^3)/A=Acos(x)"
        };

        // Уравнения из задания 1
        equations1.add(x -> Math.pow(x, 3) + Math.pow(x, 2) - x + 1. / 2);
        equations1.add(x -> Math.pow(Math.E, x) / A - x - 1);
        equations1.add(x -> Math.pow(x, 3) - 20 * x + 1);
        equations1.add(x -> Math.pow(2, x) + Math.pow(x, 2) - 2);
        equations1.add(x -> x * Math.log(x + 2) - 1 + Math.pow(x, 2));
        equations1.add(x -> Math.pow(x, 3) / A - A * Math.cos(x));

        // Производные уравнений из задания 1
        derivatives1.add(x -> 3 * Math.pow(x, 2) + 2 * x - 1);
        derivatives1.add(x -> Math.pow(Math.E, x) / A - 1);
        derivatives1.add(x -> 3 * Math.pow(x, 2) - 20);
        derivatives1.add(x -> Math.pow(2, x) * Math.log(2) + 2 * x);
        derivatives1.add(x -> Math.log(x + 2) + x / (x + 2) + 2 * x);
        derivatives1.add(x -> 3 * Math.pow(x, 2) / A + A * Math.sin(x));

        // Списки точек для каждого уровнения для локализации
        LinkedList<Point> listOfPoints1 = new LinkedList<>();
        LinkedList<Point> listOfPoints2 = new LinkedList<>();
        LinkedList<Point> listOfPoints3 = new LinkedList<>();
        LinkedList<Point> listOfPoints4 = new LinkedList<>();
        LinkedList<Point> listOfPoints5 = new LinkedList<>();
        LinkedList<Point> listOfPoints6 = new LinkedList<>();

        // Общий список для взятия точек
        LinkedList<LinkedList<Point>> listOfListsPoints = new LinkedList<>();
        listOfListsPoints.add(listOfPoints1);
        listOfListsPoints.add(listOfPoints2);
        listOfListsPoints.add(listOfPoints3);
        listOfListsPoints.add(listOfPoints4);
        listOfListsPoints.add(listOfPoints5);
        listOfListsPoints.add(listOfPoints6);

        // Приближенные значения X и Y
        double[] pointsX = new double[]{1.1, 1, 1, 0.6, -1};
        double[] PointsY = new double[]{0.4, 0.4, 0.5, 0.6, 1};

        // Заполнение мапы, ключ - уравнение; значение - список с точками для него
        LinkedHashMap<String, LinkedList<Point>> mapOfEquations = new LinkedHashMap<>();
        for (int i = 0; i < listOfListsPoints.size(); i++) {
            mapOfEquations.put(equationsStr1[i], listOfListsPoints.get(i));
        }

        // Заполнение списка параметров для второго задания
        LinkedList<Parameter> parametersList = new LinkedList<>();
        parametersList.add(new Parameter(0.2, 1 / 0.6, 1. / 2));
        parametersList.add(new Parameter(0.4, 1 / 0.8, 1. / 2));
        parametersList.add(new Parameter(0.3, 1 / 0.2, 1. / 3));
        parametersList.add(new Parameter(0, 1 / 0.6, 1. / 2));

        equations21.add((x, y, a, alpha, betta) -> Math.tan(x * y + a) - Math.pow(x, 2));
        equations21.add((x, y, a, alpha, betta) -> Math.pow(x, 2) / alpha + Math.pow(y, 2) / betta - 1);
        derivatives21.add((x, y, a, alpha, betta) -> y * (Math.pow(Math.tan(x * y + a), 2) + 1) - 2 * x);
        derivatives21.add((x, y, a, alpha, betta) -> x * (Math.pow(Math.tan(x * y + a), 2)) + 1);
        derivatives21.add((x, y, a, alpha, betta) -> 1 / alpha * 2 * x);
        derivatives21.add((x, y, a, alpha, betta) -> 1 / betta * 2 * x);

        equations22.add((x, y) -> Math.pow(x, 2) + Math.pow(y, 2) - 2);
        equations22.add((x, y) -> Math.exp(x - 1) + Math.pow(y, 3) - 2);
        derivatives22.add((x, y) -> 2 * x);
        derivatives22.add((x, y) -> 2 * y);
        derivatives22.add((x, y) -> Math.exp(x - 1));
        derivatives22.add((x, y) -> 3 * Math.pow(y, 2));

        System.out.println("----ЗАДАНИЕ 1----");
        findSegments(equations1, listOfListsPoints);
        simpleIterationMethod(mapOfEquations, equations1, derivatives1);

        System.out.println("\n----ЗАДАНИЕ 2.1----\n");
        System.out.println("tg(xy + A) = x^2");
        System.out.println("x^2 / a^2 + y^2 / b^2 = 1");
        newtonMethod(pointsX, PointsY, derivatives21, equations21, parametersList);

        System.out.println("\n----ЗАДАНИЕ 2.2----\n");
        System.out.println("x1^2 + x2^2 - 2 = 0");
        System.out.println("e^(x1 - 2) + x2^3 - 2 = 0\n");
        newtonMethodMod2(pointsX, PointsY, derivatives22, equations22);
    }

    // Метод простой итерации (для задания 1)
    public static void simpleIterationMethod(
            LinkedHashMap<String, LinkedList<Point>> mapOfEquations,
            LinkedList<Operationable1> equations1,
            LinkedList<Operationable1> derivatives1
    ) {
        // номер текущего уравнения
        int equationNumber = 0;
        for (String key : mapOfEquations.keySet()) {
            System.out.println();
            System.out.println(key);
            for (int i = 0; i < mapOfEquations.get(key).size(); ++i) {
                System.out.println("\nРешение " + (i + 1) + "\n");
                double x1, d;
                int iterationNum = 1;
                double x0 = mapOfEquations.get(key).get(i).getX();
                double lambda = 1 / derivatives1.get(equationNumber).calculate(x0);
                do {
                    x1 = x0;
                    x0 = x1 - lambda * equations1.get(equationNumber).calculate(x1);
                    d = Math.abs(x0 - x1);
                    System.out.println("Итерация №" + iterationNum);
                    System.out.println("x = " + String.format("%.8f", x0));
                    System.out.println("d = " + String.format("%.8f", d));
                    ++iterationNum;
                } while (d > EPS);
            }
            ++equationNumber;
        }
    }

    // Локализация корней для МПИ
    public static void findSegments(
            LinkedList<Operationable1> equations1, LinkedList<LinkedList<Point>> listOfListsPoints
    ) {
        for (int i = 0; i < equations1.size(); ++i) {
            for (double j = START_POINT_OF_SEGMENT; j <= FINISH_POINT_OF_SEGMENT; j += SEARCH_SEGMENT_STEP) {
                if (equations1.get(i).calculate(j) * equations1.get(i).calculate(j + SEARCH_SEGMENT_STEP) < 0) {
                    listOfListsPoints.get(i).add(new Point((j + j + SEARCH_SEGMENT_STEP) / 2));
                } else if (equations1.get(i).calculate(j) == 0) {
                    listOfListsPoints.get(i).add(new Point((j)));
                }
            }
        }
    }

    // Поиск обратной матрицы для метода Ньютона
    public static void obrMatrix(
            double[][] a, int N
    ) {
        double temp;
        double[][] E = new double[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                E[i][j] = 0f;
                if (i == j)
                    E[i][j] = 1f;
            }
        for (int k = 0; k < N; k++) {
            temp = a[k][k];
            for (int j = 0; j < N; j++) {
                a[k][j] /= temp;
                E[k][j] /= temp;
            }
            for (int i = k + 1; i < N; i++) {
                temp = a[i][k];
                for (int j = 0; j < N; j++) {
                    a[i][j] -= a[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }
        for (int k = N - 1; k > 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                temp = a[i][k];
                for (int j = 0; j < N; j++) {
                    a[i][j] -= a[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }
        for (int i = 0; i < N; i++)
            System.arraycopy(E[i], 0, a[i], 0, N);
    }

    // Метод Ньютона (для задания 2.1)
    public static void newtonMethod(
            double[] pointsX, double[] PointsY,
            LinkedList<Operationable21> derivatives21,
            LinkedList<Operationable21> equations21,
            LinkedList<Parameter> parametersList
    ) {
        for (int i = 0; i < AMOUNT_CH_PROIZVODNIH; ++i) {
            System.out.println("\nПараметры " + (i + 1));
            double x = pointsX[i];
            double y = PointsY[i];
            int iterationNum = 1;
            double[][] a = new double[2][2];
            double dx, dy, norm1, norm2;
            double[] b = new double[2];
            int derivativeNumber;
            do {
                derivativeNumber = 0;
                for (int j = 0; j < a.length; ++j) {
                    for (int k = 0; k < a[j].length; ++k) {
                        a[j][k] = derivatives21.get(derivativeNumber).calculate(x, y, parametersList.get(i).getAValue(), parametersList.get(i)
                                .getAlphaSquaredValue(), parametersList.get(i).getBetaSquaredValue());
                        ++derivativeNumber;
                    }
                }

                obrMatrix(a, 2);
                dx = a[0][0] * equations21.get(0).calculate(x, y, parametersList.get(i).getAValue(), parametersList.get(i)
                        .getAlphaSquaredValue(), parametersList.get(i).getBetaSquaredValue())
                        + a[0][1] * equations21.get(1).calculate(x, y, parametersList.get(i).getAValue(), parametersList
                        .get(i).getAlphaSquaredValue(), parametersList.get(i).getBetaSquaredValue());
                dy = a[1][0] * equations21.get(0).calculate(x, y, parametersList.get(i).getAValue(), parametersList.get(i)
                        .getAlphaSquaredValue(), parametersList.get(i).getBetaSquaredValue())
                        + a[1][1] * equations21.get(1).calculate(x, y, parametersList.get(i).getAValue(), parametersList
                        .get(i).getAlphaSquaredValue(), parametersList.get(i).getBetaSquaredValue());
                x = x - dx;
                y = y - dy;
                b[0] = equations21.get(0).calculate(x, y, parametersList.get(i).getAValue(), parametersList.get(i)
                        .getAlphaSquaredValue(), parametersList.get(i).getBetaSquaredValue());
                b[1] = equations21.get(1).calculate(x, y, parametersList.get(i).getAValue(), parametersList.get(i)
                        .getAlphaSquaredValue(), parametersList.get(i).getBetaSquaredValue());
                norm1 = Math.sqrt(b[0] * b[0] + b[1] * b[1]);
                norm2 = Math.sqrt(x * x + y * y);
                System.out.println("Итерация №" + iterationNum);
                System.out.println("x = " + String.format("%.8f", x));
                System.out.println("y = " + String.format("%.8f", y));
                iterationNum++;
            } while (norm1 >= EPS && norm2 >= EPS);
        }
    }

    // Метод Ньютона (для задания 2.2)
    public static void newtonMethodMod2(
            double[] pointsX, double[] PointsY,
            LinkedList<Operationable22> derivatives22,
            LinkedList<Operationable22> equations22
    ) {
        double x = pointsX[AMOUNT_CH_PROIZVODNIH];
        double y = PointsY[AMOUNT_CH_PROIZVODNIH];
        int iterationNum = 1;
        double[][] a = new double[2][2];
        double dx, dy, norm1, norm2;
        double[] b = new double[2];
        do {
            a[0][0] = derivatives22.get(0).calculate(x, y);
            a[0][1] = derivatives22.get(0).calculate(x, y);
            a[1][0] = derivatives22.get(2).calculate(x, y);
            a[1][1] = derivatives22.get(3).calculate(x, y);
            obrMatrix(a, 2);
            dx = a[0][0] * equations22.get(0).calculate(x, y) + a[0][1] * equations22.get(1).calculate(x, y);
            dy = a[1][0] * equations22.get(0).calculate(x, y) + a[1][1] * equations22.get(1).calculate(x, y);
            x = x - dx;
            y = y - dy;
            b[0] = equations22.get(0).calculate(x, y);
            b[1] = equations22.get(1).calculate(x, y);
            norm1 = Math.sqrt(b[0] * b[0] + b[1] * b[1]);
            norm2 = Math.sqrt(x * x + y * y);
            System.out.println("Итерация №" + iterationNum);
            System.out.println("x1 = " + String.format("%.8f", x));
            System.out.println("x2 = " + String.format("%.8f", y));
            iterationNum++;
        }
        while (norm1 >= EPS && norm2 >= EPS);
    }

    // Интерфейс с методом calculate для задания 1
    interface Operationable1 {
        double calculate(double x);
    }

    // Интерфейс с методом calculate для задания 2.1
    interface Operationable21 {
        double calculate(double x, double y, double a, double alpha, double betta);
    }

    // Интерфейс с методом calculate для задания 2.2
    interface Operationable22 {
        double calculate(double x, double y);
    }
}