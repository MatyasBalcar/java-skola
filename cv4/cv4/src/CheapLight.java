public class CheapLight extends Switchable {
    int lives;
    boolean state;

    public CheapLight(){
        this.lives = 20;
        this.state = false;
    }

    @Override
    public boolean turnOn() {
        if (lives <= 0 || state) { // Check if lives are exhausted or already on
            return false;
        }

        lives--; // Decrease lives only if turning on is successful
        return super.turnOn(); // Call parent method to set state to true
    }

}
