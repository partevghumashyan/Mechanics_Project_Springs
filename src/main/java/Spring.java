import java.util.List;

public class Spring {
    private double stiffness;

    public Spring() {
        this.stiffness = 1;
    }

    public Spring(double k) {
        this.stiffness = k;
    }

    public double getStiffness() {
        return stiffness;
    }

    private void setStiffness(double k) {
        this.stiffness = k;
    }

    public double[] move(double t, double dt, double x0) {
        return move(t, dt, x0, 0);
    }

    public double[] move(double t, double dt, double x0, double v0) {
        return move(0, t, dt, x0, v0);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0) {
        return move(t0, t1, dt, x0, v0, 1);
    }

    public double[] move(double t0, double t1, double dt, double x0, double v0, double m) {
        int size = (int) ((t1 - t0) / dt);
        double[] coordinates = new double[size];
        double omega = Math.sqrt(stiffness / m);
        double x_current = x0;
        double v_current = v0;
        double t_current = t0;

        for (int i = 0; i < size; i++) {
            double a, b;
            if (Math.sin(omega * t_current) == 0) {
                a = v_current / (omega * Math.cos(omega * t_current));
                b = x_current / Math.cos(omega * t_current);
            } else if (Math.cos(omega * t_current) == 0) {
                a = x_current / Math.sin(omega * t_current);
                b = -(v_current / omega * Math.sin(omega * t_current));
            } else {
                a = (x_current / Math.cos(omega * t_current) + v_current / (omega * Math.sin(omega * t_current))) * Math.sin(2 * omega * t_current);
                b = (x_current - a * Math.sin(omega * t_current)) / Math.cos(omega * t_current);
            }
            x_current = a * Math.sin(omega * t_current) + b * Math.cos(omega * t_current);
            v_current = omega * a * Math.cos(omega * t_current) - omega * b * Math.sin(omega * t_current);
            t_current = t_current + dt;
            coordinates[i] = x_current;
        }

        return coordinates;
    }

    public Spring inSeries(Spring that) {
        double k1 = this.getStiffness();
        double k2 = that.getStiffness();
        return new Spring(k1 * k2 / (k1 + k2));
    }

    public Spring inParallel(Spring that) {
        double k1 = this.getStiffness();
        double k2 = that.getStiffness();
        return new Spring(k1 + k2);
    }

    public static Spring inParallel(List<Spring> springList) {
        double k = 0;
        for (Spring s : springList)
            k += s.getStiffness();
        return new Spring(k);
    }

    public static Spring inSeries(List<Spring> springList) {
        double k = 0;
        for (Spring s : springList)
            k += 1 / s.getStiffness();
        return new Spring(1 / k);
    }
}
