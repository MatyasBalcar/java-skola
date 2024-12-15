public class InternalRevenueSoftware {
    TaxStatement[] statements;

    public InternalRevenueSoftware(TaxStatement[] statements) {
        this.statements = statements;
    }

    public void printTaxes(TaxPayer t){
        for (TaxStatement statement : statements) {
            if (statement.taxPayer.equals(t)) {
                System.out.println(statement.year);
                System.out.println(statement.taxes);
            }
        }
    }
    public int totalIncomeTax(int year){
        int total = 0;
        for (TaxStatement statement : statements) {
            if (statement.year == year) {
                total += statement.taxes;
            }
        }
        return total;
    }
}
