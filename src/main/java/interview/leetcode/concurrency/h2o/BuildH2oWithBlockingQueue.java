package interview.leetcode.concurrency.h2o;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Can work with "the constraint" or without
 */
public class BuildH2oWithBlockingQueue implements BuildH2o {
    private final BlockingQueue<Runnable> hydrogenQ = new LinkedBlockingQueue<>();

    public void hydrogen(Runnable releaseHydrogen) {
        hydrogenQ.add(releaseHydrogen);
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        // if we want to work without "the constraint" then we need to synchronize here below
        // note: program will block waiting for the missing element (H or O)

//        synchronized (this) {
            Runnable h1 = hydrogenQ.take();
            Runnable h2 = hydrogenQ.take();
            releaseOxygen.run();
            h1.run();
            h2.run();
//        }
    }

}
