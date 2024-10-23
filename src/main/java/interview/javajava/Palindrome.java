package interview.javajava;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    25/12/2014 22:45
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */


//http://www.programcreek.com/2013/12/leetcode-solution-of-longest-palindromic-substring-java/
//    http://www.programcreek.com/2013/12/leetcode-solution-of-longest-palindromic-substring-java/
//    http://www.programcreek.com/2013/12/leetcode-solution-of-longest-palindromic-substring-java/
//    http://www.programcreek.com/2013/12/leetcode-solution-of-longest-palindromic-substring-java/



public class Palindrome {

  //.................................0.........1.........2.........
  //.................................0123456789012345678901234567
  private static final String STR = "abcdef_gfedcba_abcdefgfedcba";

  public static void main(String[] args) {
    System.out.println("longestPalindrome_Naive    ==> STR=["+ STR +"] MaxPoly=["+ longestPalindrome_Naive(STR) +"]");
    System.out.println("longestPalindrome_Matrix   ==> STR=["+ STR +"] MaxPoly=["+ longestPalindrome_Matrix(STR) +"]");
    System.out.println("longestPalindrome_Middles  ==> STR=["+ STR +"] MaxPoly=["+ longestPalindrome_Middles(STR) +"]");
  }

  /**
   * Find the longest Palindrome in the given string
   * There are O(n^2) substring and each one we check on O(n)
   *
   * O(n^3)
   * @param str the given string
   * @return the longest Palindrome
   */
  private static String longestPalindrome_Naive(String str) {
    if (str==null || str.length()==0) {
      return "";
    }

    int max_left = 1;
    int max_right = 1;
    for (int left = 0 ; left<str.length() ; left++) {
      for (int right = left+1 ; right<str.length() ; right++) {
        if ( right-left>max_right-max_left && isPalindrome(str, left, right) ) {
          max_left = left;
          max_right = right;
        }
      }
    }
    return str.substring(max_left,max_right+1);
  }

  /**
   * For each 2 characters (i,j) we will check if substring(i,j) is a Palindrome.
   * We will store the Palindrome-length of each such substring in a matrix (zero means not a Palindrome).
   * The reason it is not O(n^3) is because we build the matrix in a way that each time when we calculate isPoly(i,j) we use the value of Matrix[i+1][j-1] so all we need to check is the edges and not the entire substring.
   *
   * O(n^3)   + Memory of O(N^2)
   * @param str the given string
   * @return the longest Palindrome
   */
  private static String longestPalindrome_Matrix(String str) {
    int[][] matrix = calculateMatrix(str);

    int max_left = 0;
    int max_right = 0;
    for (int left = 0; left < matrix.length; left++) {
      for (int right = 0; right < matrix[left].length; right++) {
        if ( matrix[left][right] > matrix[max_left][max_right] ) {
          max_left = left;
          max_right = right;
        }
      }
    }
    return str.substring(max_left, max_right+1);



  }

  /**
   * The idea here is the understanding that each Palindrome has a middle (the middle can be a char or no-char but there is a middle)
   * For each position in the given string we check the longest Palindrome that this char is in its middle.
   * We do te check twice:
   * [1] Check if the char itself is the middle of some Palindrome (odd length Palindrome)
   * [2] Check if the char itself is the left-middle in pair of chars that are the middle if some Palindrome (even length Palindrome)
   *
   * @param str
   * @return
   */
  private static String longestPalindrome_Middles(String str) {
    String result = "";
    for (int i = 0; i < str.length()-1; i++) {

      String a = longestPalindromeGivenMiddle(str, i, i);
      if (a.length()>result.length()) {
        result = a;

      }

      String b = longestPalindromeGivenMiddle(str, i, i + 1);
      if (b.length()>result.length()) {
        result = b;
      }

      System.out.println("["+i+"]"+result);
    }
    return result;
  }

  private static String longestPalindromeGivenMiddle(String str, int left, int right) {
    while (left>=0 && right<str.length() && str.charAt(left) == str.charAt(right)) {
      left--;
      right++;
    }
    return str.substring(left + 1, right);
  }


  //
  // Given a center, either one letter or two letter,
  // Find longest palindrome
  //public static String helper(String s, int begin, int end) {
  //  while (begin >= 0 && end <= s.length() - 1 && s.charAt(begin) == s.charAt(end)) {
  //    begin--;
  //    end++;
  //  }
  //  return s.substring(begin + 1, end);
  //}


  //}

  private static int[][] calculateMatrix(String str) {
    int length = str.length();

    int [][]matrix = new int[length][length];   //Matrix[from][to]  // Matrix[3,7]==1 iif the substring(3,7) is a Palindrome  //inclusive 3 and 7  // unlike java.util.String.substring !!

    for (int right = 0; right < length; right++) {
      for (int left = 0; left < length; left++) {
        if (left==right) {
          matrix[left][right] = 1;
        }

        //half of the matrix represents strings in reverse so it is not interesting.
        //for example m[3][0] is the substring(3,0) which is in revers
        else if (left>right) {
          matrix[left][right] = 0;
        }

        else { //(right-left>1)
          //if left and right are the same char so all we need is to "recursively" check the substring without left and right edges (length-2)
          if (str.charAt(right)==str.charAt(left)) {
            matrix[left][right] = matrix[left+1][right-1] + 2;
          }
          else {
            matrix[left][right] = 0;
          }
        }
      }
    }
    return matrix;
  }

  private static String matrixToString(int[][] matrix) {
    StringBuilder result = new StringBuilder();

    result.append("        ");

    for (int i = 0; i < matrix.length; i++) {
      result.append(pad(i));
    }
    result.append("\n");

    for (int i = 0; i < matrix.length; i++) {
      result.append("---");
    }
    result.append("\n");



    for (int left = 0; left < matrix.length; left++) {
      result.append(pad(left)).append(" | ");
      for (int right = 0; right < matrix.length; right++) {
        result.append(pad(matrix[left][right]));
      }
      result.append("\n");
    }
    return result.toString();
  }

  private static String pad(int i) {
    if (i==-1) {
      return " -1  ";
    }
    else if (0 <= i && i <= 9) {
      return "  " + i + "  ";
    }

    else if (10 <= i && i <= 99) {
      return "  " + i + " ";
    }

    else {
      return " "+i + " ";
    }
  }

  private static boolean isPalindrome(String str) {
    return isPalindrome(str, 0, str.length()-1);
  }

  /**
   * Check if a certain substring of "str" is a palindrome or not.
   * The substring we refer to is bounded by "left" and "right"
   *
   * O(n)
   */
  private static boolean isPalindrome(String str, int left, int right) {
    while (left<right) {
      if ( str.charAt(left) != str.charAt(right) ) {
        return false;
      }
      left++;
      right--;
    }
    return true;
  }

  //private static boolean isSubPalindrome(String str) {
  //  int left=0, right=str.length()-1;
  //  int center = (left+right)/2;
  //
  //}

}
