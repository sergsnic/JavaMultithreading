package Task25;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch cDownLatch = new CountDownLatch(5);
        ExecutorService eService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 20; i++) {
            eService.submit(new Process(cDownLatch, i));
        }
        eService.shutdown();
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            cDownLatch.countDown();

        }
        System.out.println("Защелка была открыта, теперь работает главный поток.");

    }
}
class Process implements Runnable{
    private int i;
    private CountDownLatch cDownLatch;

    public Process(CountDownLatch cDownLatch, int i) {
        this.cDownLatch = cDownLatch;
        this.i = i;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            cDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("i " + i);
    }
}
