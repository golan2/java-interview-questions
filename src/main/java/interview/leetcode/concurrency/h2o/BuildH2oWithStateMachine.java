package interview.leetcode.concurrency.h2o;

/**
 * Can work with "the constraint" or without
 * @noinspection unused
 */
public class BuildH2oWithStateMachine implements BuildH2o {
    private enum State {EMPTY, O, OH}

    private State state = State.EMPTY;

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        synchronized (this) {
            while (state == State.EMPTY) {
                this.wait();
            }

            // releaseHydrogen.run() outputs "H". Do not change or remove this line.
            releaseHydrogen.run();

            if (state == State.O) {
                state = State.OH;
            } else {
                state = State.EMPTY;
            }
            this.notifyAll();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        synchronized (this) {
            while (state != State.EMPTY) {
                this.wait();
            }

            // releaseOxygen.run() outputs "O". Do not change or remove this line.
            releaseOxygen.run();
            state = State.O;
            this.notifyAll();
        }
    }

}
