package interview.multithreading;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    25/03/14 16:10
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class Synchronized implements Runnable{

    private final String name;

    public Synchronized(String n) {
        this.name = n;
    }

    private synchronized void go(int i) {
        System.out.println(name + "_1 ("+i+")");
        System.out.println(name + "_1 ("+i+")");
        System.out.println(name + "_1 ("+i+")");
        System.out.println(name + "_2");
        System.out.println(name + "_3");

    }

    public void run() {
        for (int i = 0 ; i<100 ; i++) {
            go(i);
        }
    }

    public static void main(String[] args) {
        Synchronized t1 = new Synchronized("t1");
        Synchronized t2 = new Synchronized("t2");
        new Thread(t1).start();
        new Thread(t2).start();
    }


}