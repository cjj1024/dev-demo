import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Singleton {
    private  static Singleton singleton;

    private Singleton() {

    }

    public static Singleton getSingleton() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }

        return singleton;
    }

    public static void clear() {
        singleton = null;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            clear();
            test();
        }


    }

    public static void test() {
        Set<Integer> set = ConcurrentHashMap.newKeySet();
        int n = 10;
        CompletableFuture[] futures = new CompletableFuture[n];
        CountDownLatch start = new CountDownLatch(1);
        for (int i = 0; i < n; i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    start.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                set.add(System.identityHashCode(Singleton.getSingleton()));
            });
            futures[i] = future;
        }

        start.countDown();
        CompletableFuture.allOf(futures).join();
        if (set.size() != 1) {
            System.out.println(set.size());
        }
    }

    public static void test2() throws InterruptedException {
        Set<Integer> set = ConcurrentHashMap.newKeySet();
        int n = 10;
        CompletableFuture[] futures = new CompletableFuture[n];
        CountDownLatch start = new CountDownLatch(1);


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                10,
                20,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < n; i++) {
            threadPoolExecutor.submit(() -> {
                try {
                    start.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                set.add(System.identityHashCode(Singleton.getSingleton()));
            });
        }
        start.countDown();
        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS);

        if (set.size() != 1) {
            System.out.println(set.size());
        }

        threadPoolExecutor.setCorePoolSize(10);
    }
}
