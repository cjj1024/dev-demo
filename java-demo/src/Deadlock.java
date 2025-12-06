public class Deadlock {

    static class Resource {
        final String name;
        Resource(String name) { this.name = name; }
    }

    static final Resource r1 = new Resource("R1");
    static final Resource r2 = new Resource("R2");

    public static void main(String[] args) {

        /**
         * jstask 3588
         * jmap -dump:format=b,file=heap.hprof 8496
         */

        Thread t1 = new Thread(() -> {
            synchronized (r1) {
                System.out.println(Thread.currentThread().getName() + " locked " + r1.name);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (r2) {
                    System.out.println(Thread.currentThread().getName() + " locked " + r2.name);
                }
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            synchronized (r2) {
                System.out.println(Thread.currentThread().getName() + " locked " + r2.name);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
                synchronized (r1) {
                    System.out.println(Thread.currentThread().getName() + " locked " + r1.name);
                }
            }
        }, "Thread-2");

        t1.start();
        t2.start();
    }
}
