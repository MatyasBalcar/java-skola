import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InternalRevenueSoftware {
    private final List<TaxStatement> statements;

    public InternalRevenueSoftware(List<TaxStatement> statements) {
        this.statements = statements;
    }

    public void printTaxes(TaxPayer t) {
        statements.stream()
                .filter(statement -> statement.taxPayer.equals(t))
                .forEach(statement -> {
                    System.out.println(statement.year);
                    System.out.println(statement.taxes);
                });
    }

    public int totalTaxIncomes(int year) {
        return statements.stream()
                .filter(statement -> statement.year == year)
                .mapToInt(statement -> statement.taxes)
                .sum();
    }

    public List<TaxPayer> topTaxPayers(int n, int year){
        return statements.stream()
                .filter(statement ->  statement.year == year)
                .sorted((a, b) -> Integer.compare(b.taxes, a.taxes))
                .map(statement -> statement.taxPayer)
                .limit(n)
                .toList();
    }
}
