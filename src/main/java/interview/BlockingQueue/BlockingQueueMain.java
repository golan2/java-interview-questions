package interview.BlockingQueue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingQueueMain {

    private static final Random RND = new Random();

    private static final CountDownLatch waiter = new CountDownLatch(5);		//wait for all 5 consumers/producers to end

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {

        final FixedSizeBlockingQ<Integer> queue = new FixedSizeBlockingQ<>(5);
        try (ExecutorService executor = Executors.newFixedThreadPool(5, new ProConFactory())) {

            System.out.println("Consumer[1] Take[5] - wait for producers");
            executor.execute(new Consumer(1, 5, queue));
            Thread.sleep(1000);

            System.out.println("Producer[2][3][4] Add[11] - release Consumer[1] and last producer stuck");
            executor.execute(new Producer(2, 4, queue));
            executor.execute(new Producer(3, 4, queue));
            executor.execute(new Producer(4, 3, queue));
            Thread.sleep(1000);

            System.out.println("Consumer[5] Take[6] - end main");
            executor.execute(new Consumer(5, 6, queue));
        }

        System.out.println("Main Waiting...");
        waiter.await();
        System.out.println("Main Done!");

    }


    private static class Consumer extends Runner {
        private final int size;
        private final BlockingQueue<Integer> queue;

        Consumer(int index, int size, BlockingQueue<Integer> queue) {
            super("Consumer["+index+"]");
            this.size = size;
            this.queue = queue;
        }

        @Override
        void doit() {
            try {
                for (int round=0 ; round<size ; round++) {
                    println("polling round ["+round+"]...");
                    final Integer value = queue.poll(5, TimeUnit.SECONDS);
                    println("poll round ["+round+"] got value ["+value+"]");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                println("InterruptedException");
            }
        }

    }

    private static class Producer extends Runner {
        private final int size;
        private final BlockingQueue<Integer> queue;

        Producer(int index, int size, BlockingQueue<Integer> queue) {
            super("Producer["+index+"]");
            this.queue = queue;
            this.size = size;
        }

        @Override
        void doit() {
            try {
                for (int round=0 ; round<size ; round++) {
                    final int value = RND.nextInt(300);
                    println("offering [" + value + "] in round [" + round + "]...");
                    final boolean success = queue.offer(value, 5, TimeUnit.SECONDS);
                    println("offer round [" + round + "] success=[" + success + "]");
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                println("InterruptedException");
            }
        }

    }

    public abstract static class Runner implements Runnable {
        protected final String name;

        Runner(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            println("run - BEGIN");
            doit();
            waiter.countDown();
            println("run - END (CountDown="+waiter.getCount()+")");
        }

        abstract void doit();

        void println(String message) {
            BQLog.println(name + ": " + message);
        }

    }

    private static class BQLog {
        private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        private static void println(String message) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            //noinspection Since15
            System.out.printf("%s [%d] %s\n", simpleDateFormat.format(calendar.getTime()), Thread.currentThread().threadId(), message);
        }
    }

    private static class ProConFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final ThreadGroup group;

        ProConFactory() {
            this.group = Thread.currentThread().getThreadGroup();
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, "prodcon-" + threadNumber.getAndIncrement(), 0);
            t.setDaemon(true);
            t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
