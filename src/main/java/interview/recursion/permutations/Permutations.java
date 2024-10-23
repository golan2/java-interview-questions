package interview.recursion.permutations;

import golan.utils.MyLog;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Permutations {

  private static final boolean DEBUG = false;

  public static void main(String[] args) {
    Integer[] arr = { 1, 2, 3 , 4};
    final Integer[][] allPermutations = allPermutations(arr);
    System.out.println(
            "All Permutations: \n" +
            Arrays.stream(allPermutations)
                    .map(Arrays::toString)
                    .collect(Collectors.joining("\n"))
    );
  }

  public static Integer[][] allPermutations(Integer[] arr) {
    return allPermutations(arr, 0, arr.length);
  }

  private static String tabify(int count){
    StringBuilder result = new StringBuilder();
    for (int i=0 ; i<count ; i++)
      result.append("\t");
    return result.toString();
  }

  private static Integer[][] allPermutations(Integer[] arr, int beginIndex, int size) {

    if (DEBUG) {
      MyLog.log(MyLog.LogLevel.DEBUG, tabify(beginIndex) + "allPermutations - BEGIN - beginIndex=["+beginIndex+"] size=["+size+"] arr=["+Arrays.toString(arr)+"]");
    }

    Integer[][] result = new Integer[factorial(size)][size];
    if (size==1) {
      result[0] = new Integer[1];
      result[0][0] = arr[beginIndex];

      if (DEBUG) {
        MyLog.log(MyLog.LogLevel.DEBUG, tabify(beginIndex) + "allPermutations - END - beginIndex=["+beginIndex+"] size=["+size+"] result=["+Arrays.deepToString(result)+"]");
      }

      return result;
    }

    int permutation = 0;

    //Who is the "first"?
    // whenever we say "fist" we mean the one on the arr[beginIndex]
    // because every recursive call needs a smaller array
    // but we don't copy to new array.
    // instead, we use beginIndex to represent where the small array begins

    for (int i = beginIndex; i < arr.length; i++) {

      if (DEBUG) {
        MyLog.log(MyLog.LogLevel.DEBUG, tabify(beginIndex) + "In the loop - [BeforeSwap] - beginIndex=["+beginIndex+"] size=["+size+"] i=["+i+"] current=["+arr[i]+"] arr=["+ Arrays.deepToString(arr) +"] ");
      }

      //move "current" number to the beginning of the array (i.e. to be the first)
      int temp = arr[beginIndex];
      arr[beginIndex] = arr[i];
      arr[i] = temp;


      if (DEBUG) {
        MyLog.log(MyLog.LogLevel.DEBUG, tabify(beginIndex) + "In the loop - [After_Swap] - beginIndex=["+beginIndex+"] size=["+size+"] i=["+i+"] current=["+arr[i]+"] arr=["+ Arrays.deepToString(arr) +"] ");
      }



      //calculate the permutations of all the rest (without "current" which is now the first)
      Integer[][] rec = allPermutations(arr, beginIndex + 1, size - 1);

      if (DEBUG) {
        MyLog.log(MyLog.LogLevel.DEBUG, tabify(beginIndex) + "In the loop - [AfterRecur] - beginIndex=["+beginIndex+"] size=["+size+"] i=["+i+"] current=["+arr[i]+"] rec=["+ Arrays.deepToString(rec) +"] ");
      }


      //copy recursive solution back into the result ( adding "current" in the beginning )
      for (int p = 0; p < rec.length; p++) {
        Integer[] currentPermutation = new Integer[size];
        currentPermutation[0] = arr[beginIndex];
        for (int j = 0; j < rec[p].length; j++) {
          currentPermutation[j+1] = rec[p][j];
        }
        result[permutation++] = currentPermutation;

        if (DEBUG) {
          MyLog.log(MyLog.LogLevel.DEBUG, tabify(beginIndex) + "In the loop - [AddPermuta] - beginIndex=["+beginIndex+"] size=["+size+"] i=["+i+"] current=["+arr[i]+"] result=["+ Arrays.deepToString(result) +"] ");
        }

      }

      //swap them back to not ruin it in the recursive calls
      temp = arr[beginIndex];
      arr[beginIndex] = arr[i];
      arr[i] = temp;

      if (DEBUG) {
        MyLog.log(MyLog.LogLevel.DEBUG, tabify(beginIndex) + "In the loop - [Swap__Back] - beginIndex=["+beginIndex+"] size=["+size+"] i=["+i+"] current=["+arr[i]+"] arr=["+ Arrays.deepToString(arr) +"] ");
      }


    }

    if (DEBUG) {
      MyLog.log(MyLog.LogLevel.DEBUG, tabify(beginIndex) + "allPermutations - END - beginIndex=["+beginIndex+"] size=["+size+"] result=["+Arrays.deepToString(result)+"]");
    }

    return result;
  }

  private static int factorial(int a) {
    int result = 1;
    for (int i = 2; i <= a; i++) {
      result *= i;
    }
    return result;
  }
}
