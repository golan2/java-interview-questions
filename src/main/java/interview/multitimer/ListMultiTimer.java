package interview.multitimer;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    20/02/14 09:09
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class ListMultiTimer extends AbsMultiTimer {
    private final Collection<MultiTask> tasks = new LinkedList<>();


    @Override
    protected void innerAddTask(MultiTask multiTask) {
        tasks.add(multiTask);
    }

    protected boolean innerRemoveTask(MultiTask task) {
        return tasks.remove(task);
    }

    @Override
    protected boolean emptyTasksList() {
        return tasks.isEmpty();
    }

    @Override
    protected MultiTask findNextTask() {
        Iterator<MultiTask> iterator = tasks.iterator();
        MultiTask minTask = iterator.next();

        while (iterator.hasNext()) {
            MultiTask current = iterator.next();
            if (current.when < minTask.when) {
                minTask = current;
            }
        }
        return minTask;
    }

}
