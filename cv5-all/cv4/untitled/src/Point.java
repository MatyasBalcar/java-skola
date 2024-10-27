public class Point extends Geometry{
    float x;
    float y;

    public Point(float x, float y ){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public double distance(Point p){
        return Math.sqrt(((this.x-p.x)*(this.x-p.x)) + ((this.y-p.y)*(this.y-p.y)));
    }

    public boolean equals(Point p){
        return this.getX() == p.getX() && this.getY() == p.getY();
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}



