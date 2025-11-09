import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumer2 {
    private ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);
    private AtomicInteger atomicInteger = new AtomicInteger();

    Thread producer = new Thread(() -> {
        while (true) {
            try {
                blockingQueue.put(atomicInteger.incrementAndGet());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    });
    Thread consumer = new Thread(() -> {
        while (true) {
            try {
                System.out.println(blockingQueue.take());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    });

    public static void main(String[] args) {
        ProducerConsumer2 producerConsumer2 = new ProducerConsumer2();
        producerConsumer2.consumer.start();
        producerConsumer2.producer.start();
    }
}
