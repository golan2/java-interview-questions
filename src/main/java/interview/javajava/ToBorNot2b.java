package interview.javajava;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    10/12/2014 07:15
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class ToBorNot2b {

  public static void main(String[] args) {
    ParentClass parentClass = new SubClass();
  }

  private static class ParentClass {
    int b = 4;

    public ParentClass(){
      print(b);
    }

    public void print(int value){
      System.out.println("["+value+"] I'm from the Parent "+ b);
    }
  }

  private static class SubClass extends ParentClass {
    int b = 5;

    private SubClass() {
      print(b);
    }

    public void print(int value) {
      System.out.println("["+value+"] I'm from the Sub "+ b);
    }
  }

}
