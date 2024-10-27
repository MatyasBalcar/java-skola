public class Bike extends Vehicle {
    double curDistance;

    public Bike(double x, double y) {
        super(x,y);
        double curDistance = 0;
    }

    public void driveForward(double distance){
        this.curDistance += distance;

        if (curDistance <= 200){
            super.driveForward(distance);
        }
        else{
            System.out.println("Kolo nejede :(");
        }
    }
}
