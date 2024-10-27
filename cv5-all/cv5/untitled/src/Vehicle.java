public class Vehicle {

    private int direction;
    private double x;
    private double y;

    public Vehicle(double x, double y) {
        this.x = x;
        this.y = y;
        this.direction = 0;
    }

    public void turn(int angle) {
        this.direction += angle;
    }

    public void driveForward(double distance) {
        System.out.println("JEDEME.....");
        double angle = Math.toRadians(direction);
        x += Math.cos(angle) * distance;
        y += Math.sin(angle) * distance;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public String toString() {
        return "Vehicle at " + x + ", " + y;
    }
}
