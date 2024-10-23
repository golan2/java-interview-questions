package interview.multitimer;

import java.util.PriorityQueue;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    20/05/2015 21:45
 * <B>Description:</B>
 *
 * </pre>
 * @noinspection unused
 */
public class PriorityQueueMultiTimer extends AbsMultiTimer {
  PriorityQueue<MultiTask> queue = new PriorityQueue<>(new MultiTaskComparator());

  @Override
  protected void innerAddTask(MultiTask multiTask) {
    queue.add(multiTask);
  }

  @Override
  protected MultiTask findNextTask() {
    return queue.peek();
  }

  @Override
  protected boolean innerRemoveTask(MultiTask task) {
    return queue.remove(task);
  }

  @Override
  protected boolean emptyTasksList() {
    return false;
  }

  private static class MultiTaskComparator implements java.util.Comparator<MultiTask> {
    @Override
    public int compare(MultiTask lhs, MultiTask rhs) {
      return Long.compare(lhs.when, rhs.when);
    }
  }
}
