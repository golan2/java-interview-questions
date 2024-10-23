package interview.leetcode.concurrency;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FooBarRunner {

    public static void main(String[] args) {
        final var runner = new FooBarRunner();
        runner.run(10);
    }

    @SneakyThrows
    private void run(int n) {
        final var fb = new FooBar(n);
        final ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                fb.foo(new PrintFoo());
            }
        });

        executor.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                fb.bar(new PrintBar());
            }
        });

        executor.shutdown();
        executor.awaitTermination(20, TimeUnit.SECONDS);
    }

    private static class PrintFoo implements Runnable {
        @Override
        public void run() {
            System.out.println("foo");
        }
    }

    private static class PrintBar implements Runnable {
        @Override
        public void run() {
            System.out.println("bar");
        }
    }

    class FooBar {
        private char turn = 'F';
        private int n;

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                synchronized (this) {
                    while (turn == 'B') {
                        this.notify();
                        this.wait();
                    }
                    printFoo.run();
                    turn = 'B';
                }
            }
            synchronized (this) {
                this.notify();
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                synchronized (this) {
                    while (turn == 'F') {
                        this.notify();
                        this.wait();
                    }
                    printBar.run();
                    turn = 'F';
                }
            }
        }
    }
}
