package billiards;

import java.util.ArrayList;
import java.util.List;

public class CircularBilliardSimulation {
    private static final double EPSILON = 1e-6; // Small value to check for path deviation
    private static final double DELTA_ANGLE = 0.01; // Angle increment for initial momentum calculation


    public static void main(String[] args) {
        int nReflections = 10; // Number of reflections
        double delta = 0.1; // Small delta value for path deviation check

        Point initPosition = getRandomPositionInsideCircle();
        Point initMomentum = getRandomUnitMomentum();
        List<Point> reflectionPoints = simulateCircularBilliard(nReflections, initPosition, initMomentum);
        System.out.println("Coordinates of Reflection Points:");
        for (Point point : reflectionPoints) {
            System.out.println("(" + point.getX() + ", " + point.getY() + ")");
        }

        boolean isReversible = checkReversibility(reflectionPoints, nReflections, delta, initPosition, initMomentum);
        System.out.println("Is the motion reversible? " + isReversible);
    }

    private static List<Point> simulateCircularBilliard(int nReflections,  Point initPosition, Point initMomentum) {
        List<Point> reflectionPoints = new ArrayList<>();

        for (int i = 0; i < nReflections; i++) {
            Point nextPosition = calculateNextPosition(initPosition, initMomentum);
            Point nextMomentum = calculateNextMomentum(nextPosition, initMomentum);
            reflectionPoints.add(nextPosition);
            initPosition = nextPosition;
            initMomentum = nextMomentum;
        }

        return reflectionPoints;
    }

    private static Point getRandomPositionInsideCircle() {
        double angle = Math.random() * 2 * Math.PI;
        double radius = Math.sqrt(Math.random());
        double x = Math.cos(angle) * radius;
        double y = Math.sin(angle) * radius;
        return new Point(x, y);
    }

    private static Point getRandomUnitMomentum() {
        double angle = Math.random() * 2 * Math.PI;
        double x = Math.cos(angle) * DELTA_ANGLE;
        double y = Math.sin(angle) * DELTA_ANGLE;
        return new Point(x, y);
    }

    public static Point calculateNextPosition(Point initialPosition, Point momentum){
        double x = initialPosition.getX();
        double y = initialPosition.getY();
        double px = momentum.getX();
        double py = momentum.getY();
        double k = py/px;

        double b = y - (k * x);

        // solve y^2 + x^2 = 1 and y = kx+b to find intersections
        double discriminant = 4 * Math.pow(k*b, 2) - 4 * ((1+Math.pow(k, 2)) * (Math.pow(b, 2) - 1));
        double x_1 = (-2 * k * b - Math.sqrt(discriminant)) / (2 * (1 + Math.pow(k,2)));
        double x_2 = (-2 * k * b + Math.sqrt(discriminant)) / (2 * (1 + Math.pow(k,2)));
        double next_x, next_y;
        if(px < 0) {
            next_x = Math.min(x_1, x_2);
        } else {
            next_x = Math.max(x_1, x_2);
        }
        next_y = k * next_x + b;


        return new Point(next_x, next_y);
    }

    public static Point calculateNextMomentum(Point position, Point momentum) {
        double x = position.getX();
        double y = position.getY();
        double px = momentum.getX();
        double py = momentum.getY();

        double px_prime = (y*y-x*x)*px - 2 * x * y * py;
        double py_prime = - 2 * x * y * px + (x * x - y * y) * py;

        return new Point(px_prime, py_prime);
    }

    private static boolean checkReversibility(List<Point> reflectionPoints, int nReflections, double delta, Point initPosition, Point initMomentum) {
        List<Point> positions = new ArrayList<>();
        for(int i = 0; i < nReflections; i++) {
            Point nextPosition = calculateNextPosition(initPosition, initMomentum);
            Point nextMomentum = calculateNextMomentum(nextPosition, initMomentum);
            positions.add(nextPosition);
            initPosition = nextPosition;
            initMomentum = nextMomentum;
        }
        initMomentum = new Point(-initMomentum.getX(), -initMomentum.getY());
        List<Point> positionsReversed = new ArrayList<>();
        for(int i = 0; i < nReflections; i++) {
            Point nextPosition = calculateNextPosition(initPosition, initMomentum);
            Point nextMomentum = calculateNextMomentum(nextPosition, initMomentum);
            positionsReversed.add(nextPosition);
            initPosition = nextPosition;
            initMomentum = nextMomentum;
        }

        // Check path deviation
        for (int i = 0; i < reflectionPoints.size(); i++) {
            double deltaX = Math.abs(positions.get(i).getX() - positionsReversed.get(nReflections-i-1).getX());
            double deltaY = Math.abs(positions.get(i).getY() - positionsReversed.get(nReflections-i-1).getY());
            if (deltaX > delta || deltaY > delta) {
                return false;
            }
        }

        return true;
    }
}
