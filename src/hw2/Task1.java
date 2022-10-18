package hw2;

import java.util.LinkedList;

public class Task1 {

    private static final double EXPO = 2.718281828;
    private static final double a = 5;

    public static void main(String[] args) {
        LinkedList<Operational> exes = new LinkedList<>();
        LinkedList<Points> points = new LinkedList<>();
        exes.add(x -> Math.pow(x, 3) + Math.pow(x, 2) + 0.5);
        exes.add(x -> Math.pow(EXPO, x) / a - 1);
        exes.add(x -> (Math.pow(x, 3) + 1) / 20);
        exes.add(x -> Math.sqrt(2 - Math.pow(2, x)));
        exes.add(x -> (-1 * Math.pow(x, 2) + 1) / Math.log(x + 2));
        exes.add(x -> Math.pow((Math.pow(a, 2) * Math.cos(x)), (1 / 3)));

        points.add(new Points(-2));
        points.add(new Points(1));
        points.add(new Points(4));
        points.add(new Points(1));
        points.add(new Points(0));
        points.add(new Points(1));

        for (int i = 0; i < exes.size(); i++) {
            simpleIteration(exes.get(i), points.get(i).firstX);
        }
    }

    private static void simpleIteration(Operational func, double firstX) {
        double x = firstX;
        double xLast = 0;
        while (x - xLast > Math.pow(10, -30)) {
            xLast = x;
            x = func.calc(xLast);
        }
        System.out.println("x = " + x);
    }

    private static class Points {
        double firstX;

        public Points(double firstX) {
            this.firstX = firstX;
        }
    }

    interface Operational {
        double calc(double x);
    }
}
