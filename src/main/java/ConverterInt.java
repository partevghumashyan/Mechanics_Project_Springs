public class ConverterInt extends Converter {
    @Override
    public String toSpringExpression(String binarySequence) {
        StringBuilder springExpr = new StringBuilder();
        int length = binarySequence.length();

        char[] sequence = binarySequence.toCharArray();
        for (int i = 0; i < length-1; i++) {
            if (sequence[i] == '1') {
                springExpr.append("[]");
            }
        }
        if (springExpr.length() > 2) {
            springExpr.append("]");
            springExpr.insert(0, "[");
        }
        return springExpr.toString();
    }

    @Override
    public double computeDecimalFromFT(Spring spring, int N) {
        return 0;
    }

    public static void main(String[] args) {

    }
}
