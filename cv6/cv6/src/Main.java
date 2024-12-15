import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Corporation albert = new Corporation("albik sro", Corporation.CorpType.LLC);
        TaxStatement taxovy_statement_2023 = new TaxStatement(albert, 2023, 1000000);
        TaxStatement taxovy_statement_2022 = new TaxStatement(albert, 2022, 750000);
        TaxStatement taxovy_statement_2021 = new TaxStatement(albert, 2022, 500000);

        List<TaxStatement> l = new LinkedList<TaxStatement>();
        l.add(taxovy_statement_2021);
        l.add(taxovy_statement_2022);
        l.add(taxovy_statement_2023);

        InternalRevenueSoftware software = new InternalRevenueSoftware(l);

        software.printTaxes(albert);
        System.out.println(software.totalTaxIncomes(2023));

        System.out.println(software.topTaxPayers(1,2023));

    }
}