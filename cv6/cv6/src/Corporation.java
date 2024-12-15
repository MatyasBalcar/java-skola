public class Corporation extends TaxPayer{

    enum CorpType {
        INCORPORATED,
        LLC,
        NONPROFIT
    }
    CorpType type;

    public Corporation(String name, CorpType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public int getIncomeTax(int income){
        int taxes = 0;
        if(type == CorpType.LLC || type == CorpType.INCORPORATED){
            taxes = (int) (income * 0.2);
        }else {
            taxes = (int) (income * 0.05);
        }
        return taxes;
    }

}
