public class FuncRunTime {
    public static void main(String[] args) throws InterruptedException {
        /**
         * jcmd 8932 JFR.start name=profile settings=profile
         * jcmd 8932 JFR.stop name=profile filename=profile.jfr
         */
        for (int i = 0; i < 1000000000; i++) {
            fun1(i);
            fun2(i);
        }
    }

    public static void fun1(int k ) throws InterruptedException {
        for (int i = 0; i < 10_000_000; i++) {
            Math.sqrt(i);
            Math.log(i+1);
        }
        System.out.println("f1" + k);
    }

    public static void fun2(int k ) throws InterruptedException {
        for (int i = 0; i < 10_000_000; i++) {
            Math.sqrt(i);
            Math.log(i+1);
        }
        System.out.println("f2" + k);
    }

}
