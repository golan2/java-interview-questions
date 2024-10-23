package interview.leetcode.concurrency.h2o;

import java.util.concurrent.Semaphore;

/**
 * Can work ONLY with "the constraint"
 * @noinspection unused
 */
public class BuildH2oWithSemaphore implements BuildH2o {
    private final Semaphore hydrogen = new Semaphore(2, true);
    private final Semaphore oxygen = new Semaphore(0, true);

    @Override
    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        oxygen.acquire(); // wait for Oxygen that is not "full" (has room for another hydrogen)
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        hydrogen.release();
    }

    @Override
    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        hydrogen.acquire(2); // Wait for 2 hydrogen can be accumulated
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        oxygen.release(2); // we now have oxygen with room for 2 hydrogen elements
    }
}
