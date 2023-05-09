public class Converter8Bit extends Converter {

    @Override
    public Spring toSpringExpression(String binarySequence) {
        char[] bits = binarySequence.toCharArray();
        StringBuilder expression = new StringBuilder("[");
        int powerOfTwo = 1;
        for (int i = bits.length - 1; i >= 0; i--) {
            if (bits[i] == '1') {
                expression.append("[]".repeat(Math.max(0, powerOfTwo)));
            }
            powerOfTwo *= 2;
        }
        expression.append("]");
        return SpringArray.equivalentSpring(expression.toString());
    }

    @Override
    public double computeDecimalFromFT(Spring spring, int N) {
        return FT.findMaxAmplitudeIndex(computeAmplitudesOfOscillations(spring, N));
    }

    public static void main(String[] args) {
//        Converter8Bit thisConverter8Bit = new Converter8Bit();
//        String binaryExample = "00000000"; //0
//        String binarySpring = thisConverter8Bit.toSpringExpression(binaryExample);
//        Spring spring = SpringArray.equivalentSpring(binarySpring);
//        double decimal = spring.getStiffness();
//        System.out.println(thisConverter8Bit.computeDecimalFromFT(spring, 30));
//        System.out.println(decimal);
//
//        binaryExample = "00001001"; //9
//        binarySpring = thisConverter8Bit.toSpringExpression(binaryExample);
//        Spring newspring = thisConverter8Bit.createSpring(binaryExample.toCharArray());
//        System.out.println(newspring.getStiffness() + "newspring");
//        System.out.println(thisConverter8Bit.evaluateByAmplitudes(computeAmplitudesOfOscillations(newspring,30)) + "sss");
//
//        spring = SpringArray.equivalentSpring(binarySpring);
//        decimal = spring.getStiffness();
//        System.out.println(thisConverter8Bit.computeDecimalFromFT(spring, 30));
//        System.out.println(decimal);

    }
}
