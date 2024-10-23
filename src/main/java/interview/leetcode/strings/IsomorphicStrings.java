package interview.leetcode.strings;

/**
 * https://leetcode.com/problems/isomorphic-strings/
 *
 * Given two strings [s] and [t], determine if they are isomorphic.
 *
 * Two strings [s] and [t] are isomorphic if the characters in [s] can be replaced to get [t].
 */
public class IsomorphicStrings {

    public static void main(String[] args) {
        System.out.println(  new IsomorphicStrings().isIsomorphic("adadad", "12a456")  );
    }

    public boolean isIsomorphic(String source, String target) {
        final BijectionMap<Character, Character> map = new BijectionMap<>();

        for (int i = 0; i < source.length(); i++) {
            final char cs = source.charAt(i);
            final char ct = target.charAt(i);

            final Character mapped = map.get(cs);
            if (mapped == null) {
                if (map.containsValue(ct)) {
                    System.out.println("DOUBLE MAP  i=["+i+"] cs=["+cs+"] ct=["+ct+"] but we already have ["+map.getKey(ct)+"] mapped to ["+ct+"]");
                    return false;
                }
                map.put(cs, ct);
            }
            else {
                if (!mapped.equals(ct)) {
                    System.out.println("MISMATCH  i=["+i+"] cs=["+cs+"] ct=["+ct+"] but ["+cs+"] is already mapped to ["+mapped+"]");
                    return false;
                }
            }
        }

        return true;
    }
}
