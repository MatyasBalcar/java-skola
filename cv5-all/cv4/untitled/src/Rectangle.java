public class Rectangle extends Shape {
    Point ul;
    Point dr;

    public Rectangle(Point p1, Point p3){
        this.ul = p1;
        this.dr = p3;
        /*

         *  p4----c---p3
         *  |          |
         *  d          b
         *  p1-----a--p2
         
         */
    }

    public Rectangle(Point p1, float width, float height){
        this.ul = p1;
        this.dr = new Point(p1.getX() + width, p1.getY() - height);
    }
    @Override
    public double get_area(){
        return Math.abs(ul.getX() - dr.getX()) * Math.abs(ul.getY() - dr.getY());
    }

    public double get_distance(Point p){
        Point ur = new Point(dr.getX(), ul.getY());
        Point dl = new Point( ul.getX(),dr.getY());
        Line a = new Line(dl, dr);
        Line b = new Line(this.dr, ur);
        Line c = new Line(ur, this.ul);
        Line d = new Line(this.ul, dl);
        double delka0 = a.distance(p);
        double delka1 = b.distance(p);
        double delka2 = c.distance(p);
        double delka3 = d.distance(p);

        double min = delka0;
        if(delka1 < min){
            min = delka1;
        }
        if(delka2 < min){
            min = delka2;
        }
        if(delka3 < min){
            min = delka3;
        }
        return min;

    }

    public boolean equals(Rectangle r){
        return this.ul.equals(r.ul) && this.dr.equals(r.dr);
    }

    public String toString(){
        return String.format("Square with points b: %s, d: %s", this.ul.toString(), this.dr.toString());
    }
}
