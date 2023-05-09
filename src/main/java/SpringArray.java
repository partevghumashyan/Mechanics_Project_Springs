import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SpringArray {
    private static int index;

    public static Spring equivalentSpring(String springExpr) {
        List<Spring> defaultSprings = new ArrayList<>();

        for (int i = 0; i < springExpr.length() / 2; i++)
            defaultSprings.add(new Spring());

        return equivalentSpring(springExpr, defaultSprings);
    }


    public static Spring equivalentSpring(String springExpr, List<Spring> springs) {
        if (springExpr.isBlank()) {
            return new Spring(0);
        }

        Stack<Character> stack = new Stack<>();
        List<Character> springCharactersList = new ArrayList<>();

        for (char c : springExpr.toCharArray()) {
            springCharactersList.add(c);
        }

        index = 0;
        return findStiffnessRecursive(stack, springCharactersList, springs);
    }


    private static Spring findStiffnessRecursive(Stack<Character> stack, List<Character> list, List<Spring> springStiffnesses) {
        char current = list.get(0);
        char next = list.get(1);

        stack.push(current);

        list.remove(0);

        if (stack.peek() == '{' && next == '}'
                || (stack.peek() == '[' && next == ']')) {
            stack.pop();
            list.remove(0);
            return springStiffnesses.get(index++);
        }

        List<Spring> parallels = new ArrayList<>();
        List<Spring> series = new ArrayList<>();

        if (current == '[') {
            while (list.get(0) != ']') {
                parallels.add(findStiffnessRecursive(stack, list, springStiffnesses));
            }
            return Spring.inParallel(parallels);
        } else {
            while (list.get(0) != '}') {
                series.add(findStiffnessRecursive(stack, list, springStiffnesses));
            }
            return Spring.inSeries(series);
        }
    }
}