package springs;

public class FT {

    public static double[] findAmplitudes(double[] coordinates, double dt, int N) {
        double[] amplitudes = new double[N];
        for(int j = 0; j < N; j++) {
            double a_coordinate = 0;
            double b_coordinate = 0;
            double t_starting = 0;
            for(int n = 0; n < N; n++) {
                a_coordinate += coordinates[n]*Math.cos(j*t_starting);
                b_coordinate += coordinates[n]*Math.sin(j*t_starting);
                t_starting += dt;
            }
            a_coordinate = 2*a_coordinate/N;
            b_coordinate = 2*b_coordinate/N;
            amplitudes[j] = (a_coordinate*a_coordinate) + (b_coordinate*b_coordinate);
        }

        return amplitudes;
    }

    public static int findMaxAmplitudeIndex(double[] amplitudes) {
        double max = amplitudes[0];
        int maxIndex = 0;
        for (int i = 1; i < amplitudes.length; i++) {
            if(amplitudes[i] > max) {
                max = amplitudes[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
