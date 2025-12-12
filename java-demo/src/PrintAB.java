import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintAB {
    ReentrantLock lock = new ReentrantLock();
    Condition a = lock.newCondition();
    Condition b = lock.newCondition();
    boolean isA = true;

    public void printA(int n) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            while (!isA) a.await();
            System.out.println("a");
            isA = false;
            b.signal();
            lock.unlock();
        }
    }

    public void printB(int n) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            lock.lock();
            while (isA) b.await();
            System.out.println("b");
            isA = true;
            a.signal();
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        PrintAB printAB = new PrintAB();
        new Thread(() -> {
            try {
                printAB.printA(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();


        new Thread(() -> {
            try {
                printAB.printB(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
}
