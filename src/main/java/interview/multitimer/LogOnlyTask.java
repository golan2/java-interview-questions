package interview.multitimer;

/**
* <pre>
* <B>Copyright:</B>   Izik Golan
* <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
* <B>Creation:</B>    20/03/14 22:29
* <B>Since:</B>       BSM 9.21
* <B>Description:</B>
*
* </pre>
*/
class LogOnlyTask extends Task{
    private final int index;

    LogOnlyTask(int index) {this.index = index;}

    @Override
    public void run() {
        System.out.println("LogOnlyTask - run [" + index + "]");
    }

    @Override
    public String toString() {
        return "LogOnlyTask{" +
            "index=" + index +
            '}';
    }
}
