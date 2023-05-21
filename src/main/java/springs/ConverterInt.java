package springs;

import java.util.ArrayList;
import java.util.List;

public class ConverterInt extends Converter {
    private String springExpr = "";
    private Spring[] springs;

    @Override
    public Spring toSpringExpression(String binarySequence) {
        StringBuilder expression = new StringBuilder("[");
        char[] bits = binarySequence.toCharArray();
        int powerOfTwo = 1;
        List<Spring> springs = new ArrayList<>();
        for (int i = bits.length - 1; i >= 0; i--) {
            if (bits[i] == '1') {
                springs.add(new Spring(powerOfTwo));
                expression.append("[]");
            }
            powerOfTwo *= 2;
        }
        expression.append("]");
        return SpringArray.equivalentSpring(expression.toString(), springs);
    }

    @Override
    public double computeDecimalFromFT(Spring spring, int N) {
        return FT.findMaxAmplitudeIndex(computeAmplitudesOfOscillations(spring, N));
    }

    public static void main(String[] args) {
        ConverterInt converterInt = new ConverterInt();
        converterInt.toSpringExpression("1000");
        System.out.println(converterInt.toSpringExpression("100").getStiffness());
    }
}
