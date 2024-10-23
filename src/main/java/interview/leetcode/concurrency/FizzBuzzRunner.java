package interview.leetcode.concurrency;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;

/**
 * https://leetcode.com/problems/fizz-buzz-multithreaded/
 *
 * You have the four functions:
 *
 * printFizz that prints the word "fizz" to the console,
 * printBuzz that prints the word "buzz" to the console,
 * printFizzBuzz that prints the word "fizzbuzz" to the console, and
 * printNumber that prints a given integer to the console.
 * You are given an instance of the class FizzBuzz that has four functions: fizz, buzz, fizzbuzz and number. The same instance of FizzBuzz will be passed to four different threads:
 *
 * Thread A: calls fizz() that should output the word "fizz".
 * Thread B: calls buzz() that should output the word "buzz".
 * Thread C: calls fizzbuzz() that should output the word "fizzbuzz".
 * Thread D: calls number() that should only output the integers.
 * Modify the given class to output the series [1, 2, "fizz", 4, "buzz", ...] where the ith token (1-indexed) of the series is:
 *
 * "fizzbuzz" if i is divisible by 3 and 5,
 * "fizz" if i is divisible by 3 and not 5,
 * "buzz" if i is divisible by 5 and not 3, or
 * i if i is not divisible by 3 or 5.
 * Implement the FizzBuzz class:
 *
 * FizzBuzz(int n) Initializes the object with the number n that represents the length of the sequence that should be printed.
 * void fizz(printFizz) Calls printFizz to output "fizz".
 * void buzz(printBuzz) Calls printBuzz to output "buzz".
 * void fizzbuzz(printFizzBuzz) Calls printFizzBuzz to output "fizzbuzz".
 * void number(printNumber) Calls printnumber to output the numbers.
 *
 *
 * Example 1:
 *
 * Input: n = 15
 * Output: [1,2,"fizz",4,"buzz","fizz",7,8,"fizz","buzz",11,"fizz",13,14,"fizzbuzz"]
 * Example 2:
 *
 * Input: n = 5
 * Output: [1,2,"fizz",4,"buzz"]
 *
 *
 * Constraints:
 *
 * 1 <= n <= 50
 *
 *
 *
 * =========================================================================================
 *
 * FizzBuzzRunner
 * ==============
 * FizzBuzzRunner is a class I wrote to run the {@link FizzBuzz}
 * I need 4 threads; each calls one method of {@link FizzBuzz}
 * I use a fixed ExecutorService with 4 threads and submit 4 {@link Runnable} instances.
 * The {@link FizzBuzz} output is not written to console it is stored in "result" so we can track what happened.
 *
 * =========================================================================================
 * Implementation Notes
 * ====================
 *
 *
 *
 */
@RequiredArgsConstructor
@SuppressWarnings({"JavadocLinkAsPlainText", "JavadocBlankLines", "Convert2Lambda", "RedundantThrows"})
public class FizzBuzzRunner {
    private final int n;
    private static final int TIMES = 50;

    /**
     * Run the {@link #go()} methods several times (with different values of "n")
     */
    public static void main(String[] args) {
        for (int i = 1; i <= TIMES; i++) {
            System.out.println(new FizzBuzzRunner(i).go());
        }
    }

    @SneakyThrows
    private ArrayList<String> go() {
        final FizzBuzz f = new FizzBuzz(n);
        final ExecutorService pool = Executors.newFixedThreadPool(4);
        final ArrayList<String> result = new ArrayList<>(n);


        pool.submit(
                new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        f.fizz(new RunnableIntConsumer("fizz", result));
                    }
                }
        );
        pool.submit(
                new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        f.buzz(new RunnableIntConsumer("buzz", result));
                    }
                }
        );
        pool.submit(
                new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        f.fizzbuzz(new RunnableIntConsumer("fizzbuzz", result));
                    }
                }
        );
        pool.submit(
                new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        f.number(new RunnableIntConsumer("", result));
                    }
                }
        );


        pool.shutdown();
        //noinspection ResultOfMethodCallIgnored
        pool.awaitTermination(20, TimeUnit.SECONDS);

        return result;
    }


    /**
     * We will pass an instance of this class to the fizz/buzz methods to see what they call so we know what the code does
     */
    @RequiredArgsConstructor
    private static class RunnableIntConsumer implements Runnable, IntConsumer{
        private final String output;
        private final ArrayList<String> result;

        @Override
        public void run() {
            result.add(output);
        }

        @Override
        public void accept(int value) {
            result.add(""+value);
        }

    }


    private static class FizzBuzz {
        private final int n;
        private int counter = 1;

        public FizzBuzz(int n) {
            this.n = n;
        }

        // printFizz.run() outputs "fizz".
        public void fizz(Runnable printFizz) throws InterruptedException {
            while (counter <= n) {
                synchronized(this) {
                    if (counter <= n && counter % 3 == 0  && counter % 5 != 0) {
                        printFizz.run();
                        counter++;
                    }
                    this.notifyAll();
                }
            }
        }

        // printBuzz.run() outputs "buzz".
        public void buzz(Runnable printBuzz) throws InterruptedException {
            while (counter <= n) {
                synchronized(this) {
                    if (counter % 5 == 0  && counter <= n && counter % 3 != 0) {
                        printBuzz.run();
                        counter++;
                    }
                    this.notifyAll();
                }
            }
        }

        // printFizzBuzz.run() outputs "fizzbuzz".
        public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            while (counter <= n) {
                synchronized(this) {
                    if (counter % 5 == 0  && counter <= n && counter % 3 == 0) {
                        printFizzBuzz.run();
                        counter++;
                    }
                    this.notifyAll();
                }
            }
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void number(IntConsumer printNumber) throws InterruptedException {
            while (counter <= n) {
                synchronized(this) {
                    if (counter % 5 != 0  && counter <= n && counter % 3 != 0) {
                        printNumber.accept(counter);
                        counter++;
                    }
                    this.notifyAll();
                }
            }
        }

    }

}
