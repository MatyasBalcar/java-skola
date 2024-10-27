public class Switchable {
    protected boolean state = false;

    public boolean turnOn() {
        state = true; // Set state to true when turning on
        return state;
    }

    public void turnOff() {
        state = false; // Set state to false when turning off
    }
}
