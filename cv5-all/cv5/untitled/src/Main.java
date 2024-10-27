public class Main {
    public static void main(String[] args) {
//      test kolo
        Bike kolo = new Bike(0,0);

        kolo.driveForward(200);
        kolo.driveForward(1);
//      test vehicle
        Vehicle auto = new Vehicle(0,0);

        auto.driveForward(200);
        auto.driveForward(1);

    }
}