package interview.leetcode.concurrency.h2o;

import lombok.SneakyThrows;

import java.util.ArrayList;


/**
 * https://leetcode.com/problems/building-h2o/
 *
 * Building H2O
 * =============
 * There are two kinds of threads: oxygen and hydrogen. Your goal is to group these threads to form water molecules.
 * There is a barrier where each thread has to wait until a complete molecule can be formed. Hydrogen and oxygen threads will be given releaseHydrogen and releaseOxygen methods respectively, which will allow them to pass the barrier. These threads should pass the barrier in groups of three, and they must immediately bond with each other to form a water molecule. You must guarantee that all the threads from one molecule bond before any other threads from the next molecule do.
 * In other words:
 * If an oxygen thread arrives at the barrier when no hydrogen threads are present, it must wait for two hydrogen threads.
 * If a hydrogen thread arrives at the barrier when no other threads are present, it must wait for an oxygen thread and another hydrogen thread.
 * We do not have to worry about matching the threads up explicitly; the threads do not necessarily know which other threads they are paired up with. The key is that threads pass the barriers in complete sets; thus, if we examine the sequence of threads that bind and divide them into groups of three, each group should contain one oxygen and two hydrogen threads.
 * Write synchronization code for oxygen and hydrogen molecules that enforces these constraints.
 *
 * Constraints:
 * 3 * n == water.length
 * 1 <= n <= 20
 * water[i] is either 'H' or 'O'.
 * There will be exactly 2 * n 'H' in water.
 * There will be exactly n 'O' in water.
 * (( We refer to the last 2 constraints here above as "the constraint" ))
 *
 * @noinspection JavadocLinkAsPlainText, JavadocBlankLines
 */
public class H2oMain {
/** @noinspection CommentedOutCode*/
//    private static final BuildH2o b = new BuildH2oWithoutSemaphore();
//    private static final BuildH2o b = new BuildH2oWithSemaphore();
    private static final BuildH2o b = new BuildH2oWithBlockingQueue();

    @SneakyThrows

    public static void main(String[] args) {
        final String water = removeSpaces("HOH OHH HHO  OOH HHH  HHH HOO"); // the spaces here are for readability
        final int size = water.length();

        final ArrayList<Thread> threads = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            char element = water.charAt(i);
            if (element == 'H') {
                threads.add(
                        new Thread(
                                createRunnableForHydrogen()
                        )
                );
            } else if (element == 'O') {
                threads.add(
                        new Thread(
                                createRunnableForOxygen()
                        )
                );
            } else {
                throw new RuntimeException("Unexpected token: " + element);
            }
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    /** @noinspection SameParameterValue*/
    private static String removeSpaces(String s) {
        return s.replaceAll(" ","");
    }

    private static Runnable createRunnableForOxygen() {
        //noinspection Convert2Lambda
        return new Runnable() {
            @SneakyThrows
            public void run() {
                b.oxygen(() -> Log.log("O"));
            }
        };
    }

    private static Runnable createRunnableForHydrogen() {
        //noinspection Convert2Lambda
        return new Runnable() {
            @SneakyThrows
            public void run() {
                b.hydrogen(() -> Log.log("H"));
            }
        };
    }

    private static class Log {
        static String s = "";
        static synchronized void log(String c) {
            s = s + c;
            if (s.length()==3) {
                System.out.printf(s + " ");
                s = "";
            }
        }
    }

}
