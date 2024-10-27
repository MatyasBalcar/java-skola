public class Light extends Switchable {
    boolean currentState;

    public Light(){
        this.currentState = false;
    }

    @Override
    public boolean turnOn() {
        boolean currentState = super.turnOn();
        return currentState;
    }
}
