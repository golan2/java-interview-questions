package interview.leetcode.concurrency.h2o;

public interface BuildH2o {
    void hydrogen(Runnable releaseHydrogen) throws InterruptedException;
    void oxygen(Runnable releaseOxygen) throws InterruptedException;
}
