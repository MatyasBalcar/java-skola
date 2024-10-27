public class App {
    public static boolean isCheapDevice(Switchable light) {
        for (int i = 0; i < 20; i++) {
            light.turnOn();
            light.turnOff();
        }
        return !light.turnOn();
    }

    public static int sum(IntSequence seq) {
        int total = 0;
        for (int i = 0; i < seq.length(); i++) {
            total += seq.get(i);
        }
        return total;
    }

    public static void testLine() {
        Point bod1 = new Point(0, 0);
        Point bod2 = new Point(3, 0);
        Point bod3 = new Point(4, 0);

        Line linka = new Line(bod1, bod2);

        System.out.println("LINKA TESTY spravne -> 3 a 1");
        System.out.println(linka.get_length());
        System.out.println(linka.distance(bod3));
    }

    public static void testRectangle() {
        Point obdelnikovy_bodik = new Point(0, 0);
        Point obdelnikovy_bodik2 = new Point(10, 20);

        Rectangle obdelnik = new Rectangle(obdelnikovy_bodik, obdelnikovy_bodik2);

        System.out.println("OBDELNIK TESTY -> 200 a 1");
        System.out.println(obdelnik.get_area());

        Point bod4 = new Point(10, 21);
        System.out.println(obdelnik.get_distance(bod4));
    }

    public static void testSquare() {
        Point ctvercovy_bod = new Point(0, 0);
        Square ctverec = new Square(ctvercovy_bod, 10);

        System.out.println("CTVEREC TESTY spravne ->100 a 20");
        System.out.println(ctverec.get_area());
        System.out.println(ctverec.get_distance(new Point(10, 20)));
    }

    public static void testCircle() {
        Point kruzikovy_bodicek = new Point(0, 0);
        Circle kruh = new Circle(kruzikovy_bodicek, 5);
        Point kruhovy_bod = new Point(-10, 0);

        System.out.println("KRUZNICE TESTY -> 314 a 5");
        System.out.println(kruh.get_area());
        System.out.println(kruh.distance(kruhovy_bod));
    }

    public static void testIntList() {
        IntList test1 = new IntList();

        test1.append(1);
        test1.append(2);
        test1.append(3);

        System.out.println("LIST TESTY -> 1 2 3 2 6 (dvakrat)");
        test1.printList();
        System.out.println(test1.get(1));
        System.out.println(sum(test1));
    }

    public static void testIntArray() {
        IntArray test2 = new IntArray(1,2,3);

        test2.printList();
        System.out.println(test2.get(1));
        System.out.println(sum(test2));
    }

    public static void testLights() {
        CheapLight svetlo = new CheapLight();
        Light dobre_svetlo = new Light();

        System.out.println("TEST SVETLA true, false");
        System.out.println(isCheapDevice(svetlo));
        System.out.println(isCheapDevice(dobre_svetlo));
    }   

    public static void main(String[] args)  {
        System.out.println(" *** STARTING THE THINGIES *** ");
//        stare testy!!!
//        testLine();
//        testRectangle();
//        testSquare();
//        testCircle();
//        testIntList();
//        testIntArray();
//        testLights();
        //TEST CTVERCE
        Square ctverec1 = new Square(new Point(0, 0), 5);
        Square ctverec2 = new Square(new Point(0, 0), 5);
        Square ctverec3 = new Square(new Point(1, 1), 5);
        System.out.printf("Testing square equals \n Expecting true, false\n");
        System.out.println(ctverec1.equals(ctverec2));
        System.out.println(ctverec1.equals(ctverec3));
        System.out.println(ctverec1);


        //TEST OBDELNIKY
        Rectangle rec1 = new Rectangle(new Point(0,0), new Point(10,10));
        Rectangle rec2 = new Rectangle(new Point(0,0), new Point(10,10));
        Rectangle rec3 = new Rectangle(new Point(0,0), new Point(20,20));
        System.out.printf("Testing rectangle equals \n Expecting true, false\n");
        System.out.println(rec1.equals(rec2));
        System.out.println(rec1.equals(rec3));
        System.out.println(rec1);

        //TEST BODY
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,0);
        Point p3 = new Point(1,1);
        System.out.printf("Testing points equals \n expecting true, false\n");
        System.out.println(p1.equals(p2));
        System.out.println(p1.equals(p3));
        System.out.println(p1);

        //TEST LINKY
        Line l1 = new Line(p1, p2);
        Line l2 = new Line(p1, p2);
        Line l3 = new Line(p1, p3);
        System.out.printf("Testing lines equals \n expecting true, false\n");
        System.out.println(l1.equals(l2));
        System.out.println(l1.equals(l3));
        System.out.println(l1);

        //TEST KRUZNICE
        Circle c1 = new Circle(p1,5);
        Circle c2 = new Circle(p1,5);
        Circle c3 = new Circle(p3,5);
        System.out.printf("Testing circle equals \n expecting true, false\n");
        System.out.println(c1.equals(c2));
        System.out.println(c1.equals(c3));
        System.out.println(c1);

        //TEST INTARRAY
        IntArray arr1 = new IntArray(1,2,3);
        IntArray arr2 = new IntArray(1,2,3);
        IntArray arr3 = new IntArray(1,2,3,4);
        System.out.printf("Testing integer array equals \n expecting true, false\n");
        System.out.println(arr1.equals(arr2));
        System.out.println(arr1.equals(arr3));
        System.out.println(arr1);

        //TEST INTLIST
        IntList list1 = new IntList();
        list1.append(1);
        list1.append(2);
        list1.append(3);

        IntList list2 = new IntList();
        list2.append(1);
        list2.append(2);
        list2.append(3);

        IntList list3 = new IntList();
        list3.append(1);
        list3.append(2);
        list3.append(3);
        list3.append(4);

        System.out.printf("Testing integer list equals \n expecting true, false\n");
        System.out.println(list1.equals(list2));
        System.out.println(list1.equals(list3));
        System.out.println(list1);
    }
}