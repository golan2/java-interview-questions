package interview.javajava;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    03/01/2012 02:34:08
 * <B>Since:</B>       BSM 9.1
 * <B>Description:</B>
 * Given 2 arrays of chars you need to check if all characters in a[] exists in b[].
 * </pre>
 */
public class VerifyExistence {

  public static void main(String[] args) {
    System.out.println(verifyExistence(new char[] {'1','2','3','4','5','6','8','9'}, new char[] {'1','2','3','4','5','6','8','7','9'}));      //7 is missing
  }

  private static boolean verifyExistence(char[] checkme, char[] existing) {

    char max = findMax(existing);

    int[] bitset = createBitSet(max);

    markBitSet(bitset, existing);

    clearBitSet(bitset, checkme);

    return verifyBitSet(bitset);
  }

  private static char findMax(char[] arr) {
    char max = arr[0];
    for (int i=0 ; i<arr.length ; i++) {
      if (arr[i]>max) max = arr[i];
    }
    return max;
  }

  private static int[] createBitSet(char max) {
    int[] bitset = new int[max+1];
    for (int i = 0; i < bitset.length; i++) {
      bitset[i] = 0;
    }
    return bitset;
  }

  private static void markBitSet(int[] bitset, char[] arr) {
    for (int i = 0; i < arr.length; i++) {
      bitset[arr[i]] = 1;
    }
  }

  private static void clearBitSet(int[] bitset, char[] arr) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i]<bitset.length) {
        bitset[arr[i]] = 0;
      }
    }
  }

  private static boolean verifyBitSet(int[] bitset) {
    for (int i = 0; i < bitset.length; i++) {
      if (bitset[i]!=0) return false;
    }
    return true;
  }
}
