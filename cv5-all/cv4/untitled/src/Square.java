public class Square extends Shape {

    float size;

    Point ul;
    Point dr;


    public Square(Point p, float size) {
        this.size = size;

        this.ul = p;
        this.dr = new Point(p.getX() + size, p.getY() - size);

        /*

         *  p4----c---p3
         *  |          |
         *  d          b
         *  p1-----a--p2

         */
    }


    public float getSize() {
        return this.size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    @Override
    public double get_area() {
        return size * size;
    }

    public double get_distance(Point p) {
        Point ur = new Point(dr.getX(), ul.getY());
        Point dl = new Point(ul.getX(), dr.getY());
        Line a = new Line(dl, dr);
        Line b = new Line(this.dr, ur);
        Line c = new Line(ur, this.ul);
        Line d = new Line(this.ul, dl);
        double delka0 = a.distance(p);
        double delka1 = b.distance(p);
        double delka2 = c.distance(p);
        double delka3 = d.distance(p);

        double min = delka0;
        if (delka1 < min) {
            min = delka1;
        }
        if (delka2 < min) {
            min = delka2;
        }
        if (delka3 < min) {
            min = delka3;
        }
        return min;

    }

    public boolean equals(Square s) {
        return this.ul.equals(s.ul) && this.dr.equals(s.dr);
    }

    public String toString(){
        return String.format("Square with points b: %s, d: %s", this.ul.toString(), this.dr.toString());
    }
}
