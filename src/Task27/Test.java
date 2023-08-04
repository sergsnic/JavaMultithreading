package Task27;

import java.util.concurrent.*;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Connection connection = Connection.getConnection();
        for (int i = 0; i < 200; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        connection.work();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}

class Connection {
    private static Connection connection = new Connection();
    private Semaphore semaphore = new Semaphore(10);
    private int connectionCount;

    private Connection() {
    }

    public static Connection getConnection() {
        return connection;
    }

    private void doWork() throws InterruptedException {
        synchronized (this) {
            connectionCount++;
            System.out.println(connectionCount);
        }
        Thread.sleep(5000);
        synchronized (this) {
            connectionCount--;
        }
    }

    public void work() throws InterruptedException {
        semaphore.acquire();
        try {
            doWork();
        } finally {
            semaphore.release();
        }
    }
}
