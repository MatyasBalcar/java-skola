import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RPNCalculatorTest {

    @Test
    public void testExampleExpressions(){
        Map<String, RPNCalculator.BinaryOperator<Integer>> operators = new HashMap<>();
        operators.put("+", (a, b) -> a + b);
        operators.put("-", (a, b) -> a - b);
        operators.put("*", (a, b) -> a * b);
        operators.put("/", (a, b) -> {
            if (b == 0) throw new ArithmeticException("Dělení nulou");
            return a / b;
        });

        RPNCalculator calculator = new RPNCalculator(operators);

        assertEquals(calculator.compute("3 5 + 2 -"), 6); // 6
        assertEquals(calculator.compute("10 2 * 3 /"), 6); // 6
        assertEquals(calculator.compute("4 2 + 3 *"), 18); // 18
    }

    @Test
    public void testIncorrectAmountErrors() throws IllegalArgumentException {
        Map<String, RPNCalculator.BinaryOperator<Integer>> operators = new HashMap<>();
        operators.put("+", (a, b) -> a + b);
        operators.put("-", (a, b) -> a - b);
        operators.put("*", (a, b) -> a * b);
        operators.put("/", (a, b) -> {
            if (b == 0) throw new ArithmeticException("Dělení nulou");
            return a / b;
        });

        RPNCalculator calculator = new RPNCalculator(operators);
        assertThrows(IllegalArgumentException.class, () -> calculator.compute("3 +")); //malo cisel
        assertThrows(IllegalArgumentException.class, () -> calculator.compute("3 5 ^")); //neznamy operator
        assertThrows(IllegalStateException.class, () -> calculator.compute("3 5")); //moc cisel na konci
    }


}
