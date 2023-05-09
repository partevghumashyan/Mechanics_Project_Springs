import java.util.ArrayList;
import java.util.List;

public class ConverterFloat extends Converter{
    @Override
    public Spring toSpringExpression(String binarySequence) {
        StringBuilder parallels = new StringBuilder();
        StringBuilder series = new StringBuilder();

        char[] bits = binarySequence.toCharArray();
        int powerOfTwo = 1;

        List<Character> integerPart = new ArrayList<>();
        List<Character> floatPart = new ArrayList<>();

        int floatingPart = 0;
        while (floatingPart < bits.length && bits[floatingPart] != '.') {
            integerPart.add(bits[floatingPart++]);
        }

        floatingPart++;

        while (floatingPart < bits.length) {
            floatPart.add(bits[floatingPart++]);
        }

        for (int i = integerPart.size() - 1; i >= 0; i--) {
            if (integerPart.get(i) == '1') {
                parallels.append("[]".repeat(Math.max(0, powerOfTwo)));
            }
            powerOfTwo *= 2;
        }
        powerOfTwo = 1;

        for (int i = floatPart.size() - 1; i >= 0; i--) {
            if (floatPart.get(i) == '1') {
                series.append("[]".repeat(Math.max(0, powerOfTwo)));
            }
            powerOfTwo *= 2;
        }

        return SpringArray.equivalentSpring("[" + parallels + "{" + series + "}]");
    }

    @Override
    public double computeDecimalFromFT(Spring spring, int N) {
        return FT.findMaxAmplitudeIndex(computeAmplitudesOfOscillations(spring, N));
    }
}
