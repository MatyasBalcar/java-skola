public abstract class TaxPayer {
    String name;
    int getIncomeTax(int income){
        return 0;
    }

    public String getInfo(){
        return "Name: " + name;
    }
}
