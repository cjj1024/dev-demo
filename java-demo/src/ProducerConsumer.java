import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {
    ReentrantLock lock = new ReentrantLock();
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();

    final int size = 10;
    final int[] items = new int[size];
    volatile int index = -1;

    AtomicInteger atomicInteger = new AtomicInteger();

    Thread consumer = new Thread(() -> {
        while (true) {
            lock.lock();
            try {
                while (index < 0)
                    notEmpty.await();
                System.out.println(items[index]);
                index--;
                notFull.signal();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    });

    Thread producer = new Thread(() -> {
        while (true) {
            lock.lock();
            try {
                while (index == size-1)
                    notFull.await();
                index++;
                items[index] = atomicInteger.incrementAndGet();;
                notEmpty.signal();
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    });


    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer();
        producerConsumer.producer.start();
        producerConsumer.consumer.start();
    }

}
