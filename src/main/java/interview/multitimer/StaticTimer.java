package interview.multitimer;

import java.util.Timer;

/**
* <pre>
* <B>Copyright:</B>   Izik Golan
* <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
* <B>Creation:</B>    20/03/14 22:27
* <B>Since:</B>       BSM 9.21
* <B>Description:</B>
*
* </pre>
*/
class StaticTimer {

    private static Timer t = null;

    public static synchronized void execTask(Task task, long timeToWait){
        System.out.println("StaticTimer: Task was submitted. timeToWait=[" + timeToWait + "] task=" + task);
        if (t!=null) t.cancel();
        t = new Timer(true);
        t.schedule(new TaskWrapper(task), timeToWait);
    }

    private static class TaskWrapper extends Task {
        private final Task t;

        private TaskWrapper(Task t) {this.t = t;}

        @Override
        public void run() {
            t.run();
        }
    }

}
