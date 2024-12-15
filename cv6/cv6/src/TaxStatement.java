public class TaxStatement {
    TaxPayer taxPayer;
    int year;
    int taxes;

    public TaxStatement(TaxPayer taxPayer, int year, int taxes) {
        this.taxPayer = taxPayer;
        this.year = year;
        this.taxes = taxes;
    }
}
