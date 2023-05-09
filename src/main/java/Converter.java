public abstract class Converter {

    private static final double DT = 0.1;
    private static final double T = 10;

    public abstract Spring toSpringExpression(String binarySequence);

    private static double[] computeOscillations(Spring systemOfSprings) {
        return systemOfSprings.move(T, DT, 0, 1);
    }

    public static double[] computeAmplitudesOfOscillations(Spring systemOfSprings, int N) {
        double[] oscillations = computeOscillations(systemOfSprings);
        return FT.findAmplitudes(oscillations, DT, N);
    }

    public abstract double computeDecimalFromFT(Spring spring, int N);
}
