public class Person extends TaxPayer{
    int childrenCount;
    enum PersonType {
        STUDENT,
        EMPLOYEE,
        SELF_EMPLOYED
    }
    PersonType type;
    public Person(String name, int childrenCount, PersonType type) {
        this.name = name;
        this.childrenCount = childrenCount;
        this.type = type;
    }
    public static int taxes(int mzda_arg) {
        double taxes = 0;

        if (mzda_arg <= 10000) {
            taxes = mzda_arg * 0.1;
        } else if (mzda_arg <= 20000) {
            taxes = 10000 * 0.1 + (mzda_arg - 10000) * 0.2;
        } else {
            taxes = 10000 * 0.1 + 10000 * 0.2 + (mzda_arg - 20000) * 0.3;
        }

        return (int) taxes;
    }
    @Override
    public int getIncomeTax(int income){
        int taxes = taxes(income);
        if(type == PersonType.SELF_EMPLOYED){
            taxes -=5000;
        }
        else if(type == PersonType.STUDENT){
            taxes -=1000;
        }
        taxes -= this.childrenCount*1000;
        if(taxes<0){
            taxes=0;
        }
        return taxes;
    }
}
