package interview.multitimer;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    20/05/2015 21:32
 * <B>Since:</B>       Neptune 1.0
 * <B>Description:</B>
 *
 * </pre>
 */
public abstract class AbsMultiTimer implements MultiTimer {


    protected abstract void innerAddTask(MultiTask multiTask);

    protected abstract MultiTask findNextTask();

    protected abstract boolean innerRemoveTask(MultiTask task);

    protected abstract boolean emptyTasksList();


    @Override
    public final synchronized void addTask(Runnable task, long timeToWait) {
        System.out.println("addTask - Task was added. timeToWait=[" + timeToWait + "] task=" + task);

        MultiTask multiTask = new MultiTask(task, timeToWait);
        innerAddTask(multiTask);
        scheduleNextTask();
    }


    /**
     * Find the task with the smallest value of {@link MultiTask#when}
     * This is the one that we should put into the StaticTimer
     */
    private void scheduleNextTask() {
        if (emptyTasksList()) return;
        MultiTask minTask = findNextTask();

        System.out.println("scheduleNextTask - minTask=" + minTask);

        long timeToWait = minTask.when - System.currentTimeMillis();
        StaticTimer.execTask(minTask, timeToWait);
    }

    /**
     * The given task has finished its execution.
     * Now we need to remove it from list of tasks and schedule a new one
     * @param task the task that has just finished execution
     */
    private synchronized void taskRunEnded(MultiTask task) {
        innerRemoveTask(task);
        scheduleNextTask();
    }

    protected class MultiTask extends Task{
        public final Runnable task;
        public final long     when;

        public MultiTask(Runnable task, long timeToWait) {
            this.when = System.currentTimeMillis()+timeToWait;
            this.task = task;
        }

        @Override
        public void run() {
            task.run();
            AbsMultiTimer.this.taskRunEnded(this);
        }

        @Override
        public String toString() {
            return "MultiTask{" +
                "task=" + task +
                ", when=" + when +
                '}';
        }
    }
}
