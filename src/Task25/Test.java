package Task25;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 20; i++) {
            executorService.submit(new Processor(countDownLatch));
            System.out.println(i);
        }
        executorService.shutdown();
        countDownLatch.await();
        System.out.println("Защелка была открыта, теперь работает главный поток.");

    }
}
class Processor implements Runnable{
    CountDownLatch countDownLatch;

    public Processor(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        countDownLatch.countDown();
    }
}