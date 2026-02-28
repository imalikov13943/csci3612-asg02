public class Main {
    public static void main(String[] args) {

        RingBuffer<Integer> rb = new RingBuffer<>(5);

        Reader<Integer> r1 = rb.createReader();
        Reader<Integer> r2 = rb.createReader();

        Writer<Integer> w = rb.writer();

        w.write(1); w.write(2); w.write(3); w.write(4); w.write(5);
        System.out.println("Wrote 1-5");
        rb.printBuffer();

        System.out.println("r1: " + r1.read());
        System.out.println("r1: " + r1.read());
        System.out.println("r2: " + r2.read());

        w.write(6); w.write(7);
        System.out.println("Wrote 6-7");
        rb.printBuffer();

        System.out.println("r2: " + r2.read());
        System.out.println("r2: " + r2.read());

        w.write(8); w.write(9); w.write(10);
        System.out.println("Wrote 8-10");
        rb.printBuffer();

        System.out.println("r1: " + r1.read());
        System.out.println("r2: " + r2.read()); 
        System.out.println("r2: " + r2.read());
    }
}