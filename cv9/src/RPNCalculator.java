import java.util.*;

public class RPNCalculator {
    private final Map<String, BinaryOperator<Integer>> operators;

    public RPNCalculator(Map<String, BinaryOperator<Integer>> operators) {
        this.operators = operators;
    }

    public int compute(String expression) {
        Stack<Integer> stack = new Stack<>();
        String[] tokens = expression.split("\\s+");

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else if (operators.containsKey(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Nedostatek operandů pro operátor: " + token);
                }
                int b = stack.pop();
                int a = stack.pop();
                int result = operators.get(token).apply(a, b);
                stack.push(result);
            } else {
                throw new IllegalArgumentException("Neznámý token: " + token);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalStateException("Výraz není správně sestaven");
        }
        return stack.pop();
    }

    private boolean isNumber(String token) {
        try {
            Integer.parseInt(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @FunctionalInterface
    public interface BinaryOperator<T> {
        T apply(T a, T b);
    }

    public static void main(String[] args) {
        Map<String, BinaryOperator<Integer>> operators = new HashMap<>();
        operators.put("+", (a, b) -> a + b);
        operators.put("-", (a, b) -> a - b);
        operators.put("*", (a, b) -> a * b);
        operators.put("/", (a, b) -> {
            if (b == 0) throw new ArithmeticException("Dělení nulou");
            return a / b;
        });

        RPNCalculator calculator = new RPNCalculator(operators);

        System.out.println(calculator.compute("3 5 + 2 -")); // 6
        System.out.println(calculator.compute("10 2 * 3 /")); // 6
        System.out.println(calculator.compute("4 2 + 3 *")); // 18
    }
}
