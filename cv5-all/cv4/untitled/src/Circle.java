public class Circle extends Shape{
    Point stred;
    double polomer;

    public Circle(Point stred, double polomer){
        this.stred = stred;
        this.polomer = polomer;

    }

    public Point getStred() {
        return this.stred;
    }

    public void setStred(Point stred) {
        this.stred = stred;
    }

    public double getPolomer() {
        return this.polomer;
    }

    public void setPolomer(double polomer) {
        this.polomer = polomer;
    }
    @Override 
    public double get_area(){
        return 3.14 * (polomer * 2) * (polomer * 2);
    }

    public double distance(Point p){
        return stred.distance(p) - polomer;
    }

    public boolean equals(Circle c){
        return (this.stred.equals(c.getStred()) && this.polomer == c.getPolomer());
    }

    public String toString(){
        return "Center: "  + this.stred.toString() + "Polomer : " + this.polomer;
    }
}
