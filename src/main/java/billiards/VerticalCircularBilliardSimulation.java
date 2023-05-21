package billiards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerticalCircularBilliardSimulation {
    private static final double GRAVITY = 10.0; // Gravitational acceleration
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
        double minNorm = 5.0; // Minimum norm value
        double maxNorm = 10.0; // Maximum norm value
        Random random = new Random();

        double norm = Math.sqrt(minNorm + (maxNorm - minNorm) * random.nextDouble()); // Random norm within the range

        double angle = random.nextDouble() * 2 * Math.PI; // Random angle between 0 and 2pi
        double x = norm * Math.cos(angle); // x-coordinate based on norm and angle
        double y = norm * Math.sin(angle); // y-coordinate based on norm and angle

        return new Point(x, y);
    }

    public static Point calculateNextPosition(Point initialPosition, Point initialMomentum){
        double x1 = initialPosition.getX();
        double y1 = initialPosition.getY();

        double x2 = initialMomentum.getX();
        double y2 = initialMomentum.getY() - 10;

        double a = (y2 - y1)/(2*(x2-x1));
        double b = y1 - (2*a*x1);
        double c = y1 - (a*Math.pow(x1,2)) - (b*x1);

        double x=-1;
        double y=-1;
        if(x2 > x1) {
            for(x = x1; x <= 1; x+=0.01) {
                y = a*Math.pow(x,2)+(b*x)+c;
                if(x*x+y*y > 1){
                    x -= 0.01;
                    y = a*Math.pow(x,2)+(b*x)+c;
                    break;
                }
            }
        } else {
            for(x = x1; x>=-1; x-=0.01) {
                y = a*Math.pow(x,2)+(b*x)+c;
                if(x*x+y*y > 1){
                    x += 0.01;
                    y = a*Math.pow(x,2)+(b*x)+c;
                    break;
                }
            }
        }
        return new Point(x,y);
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
