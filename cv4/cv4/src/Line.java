public class Line extends Geometry{
    Point start;
    Point end;

    public Line(Point start, Point end){
        this.start = start;
        this.end = end;
    }

    public double get_length() {
        return start.distance(end);
    }

    public Point getStart() {
        return this.start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return this.end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    @Override
    public double distance(Point p) {
        double x0 = p.getX();
        double y0 = p.getY();
        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = end.getX();
        double y2 = end.getY();

        double numerator = Math.abs((y2 - y1) * x0 - (x2 - x1) * y0 + x2 * y1 - y2 * x1);
        double denominator = Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
        double distanceToLine = numerator / denominator;

        double dotProduct1 = (x0 - x1) * (x2 - x1) + (y0 - y1) * (y2 - y1);
        double dotProduct2 = (x0 - x2) * (x1 - x2) + (y0 - y2) * (y1 - y2);

        if (dotProduct1 >= 0 && dotProduct2 >= 0) {
            return distanceToLine;
        } else {
            return Math.min(p.distance(start), p.distance(end));
        }
    }
    public boolean equals(Line l){
        return this.start.equals(l.getStart()) && this.end.equals(l.getEnd());
    }

    public String toString(){
        return "Start: " + start.toString() + " End: " + end.toString();
    }
}
