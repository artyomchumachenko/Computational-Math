package hw1;

import java.util.LinkedList;

public class Task1 {

    public static double a = 2, n = 2;
    public static final double eps = Math.pow(10, -4);
    public static final double eps2 = Math.pow(10, -4);

    public static void main(String[] args) {
        LinkedList<Operationable> equations = new LinkedList<>();
        LinkedList<Points> points = new LinkedList<>();
        equations.add(x -> Math.sin(x) - 2 * Math.pow(x, 2) + 0.5);
        equations.add(x -> Math.pow(x, n) - a);
        equations.add(x -> Math.sqrt(1 - Math.pow(x, 2)) - Math.exp(x) + 0.1);
        equations.add(x -> Math.pow(x, 6) - 5 * Math.pow(x, 3) - 2);
        equations.add(x -> Math.log(x) - (1 / (1 + Math.pow(x, 2))));
        equations.add(x -> Math.pow(3, x) - 5 * Math.pow(x, 2) + 1);
        equations.add(x -> Math.sin(x));
        equations.add(x -> Math.log(x) - 1);
        points.add(new Points(0.5, 1, 0.8));
        points.add(new Points(1, 2, 1));
        points.add(new Points(0, 0.5, 0.1));
        points.add(new Points(-1, -0.5, -0.7));
        points.add(new Points(1, 2, 1.4));
        points.add(new Points(0, 1, 0.8));
        points.add(new Points(3));
        points.add(new Points(2));
        for (int i = 0; i < equations.size(); i++) {
            System.out.println((i + 1) + ")");
            if (i < 6) dichotomy(equations.get(0), points.get(i).x1, points.get(i).x2);
            newton(equations.get(i), points.get(i).newtonNumber);
            System.out.println("________________________________");
        }
        System.out.println("Спасибо за внимание! Всем пока!");
    }

    public static void dichotomy(Operationable func, double firstPoint, double secondPoint) {
        int iterationNumber = 0;
        double approximation = secondPoint - firstPoint;
        if ((func.calculate(firstPoint) * func.calculate(secondPoint)) > 0) {
            System.out.println("diffFuncValue(firstPoint) * diffFuncValue(secondPoint) > 0\n");
            return;
        }
        while (approximation > eps) {
            double currentX = (firstPoint + secondPoint) / 2;
            if (func.calculate(currentX) * func.calculate(firstPoint) < 0) {
                secondPoint = currentX;
            } else {
                firstPoint = currentX;
            }
            iterationNumber++;
            approximation = secondPoint - firstPoint; // !!!
        }
        System.out.println("Приближение = " + approximation);
        System.out.println("Номер итерации = " + iterationNumber);
        System.out.println("Ответ = " + (firstPoint + secondPoint) / 2);
    }

    public static void newton(Operationable func, double choosePoint) {
        int iterationNumber = 0;
        double currentX = choosePoint;
        double nextX;
        double approximation;
        do {
            double functionIncrement = currentX * 1e-8;
            double funcValue = func.calculate(currentX);
            double diffFuncValue = (func.calculate(currentX + functionIncrement) - funcValue) / functionIncrement;
            nextX = currentX - (funcValue / diffFuncValue);
            approximation = Math.abs(nextX - currentX);
            currentX = nextX;
            iterationNumber++;
        } while (approximation > eps2);
        System.out.println("Приближение = " + approximation);
        System.out.println("Номер итерации = " + iterationNumber);
        System.out.println("Ответ = " + nextX);
    }

    private static class Points {
        double x1;
        double x2;
        double newtonNumber;

        public Points(double x1, double x2, double newtonNumber) {
            this.x1 = x1;
            this.x2 = x2;
            this.newtonNumber = newtonNumber;
        }

        public Points(double newtonNumber) {
            this.newtonNumber = newtonNumber;
        }

    }

    interface Operationable {
        double calculate(double x);
    }
}
