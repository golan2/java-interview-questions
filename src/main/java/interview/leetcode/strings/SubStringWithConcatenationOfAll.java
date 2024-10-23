package interview.leetcode.strings;

import java.util.*;

/**
 * https://leetcode.com/problems/substring-with-concatenation-of-all-words/
 *
 * You are given a string [s] and an array of strings [words].
 * All the strings of words are of the same length.
 * A concatenated substring in [s] is a substring that contains all the strings of any permutation of [words] concatenated.
 *
 * For example, if words = ["ab","cd","ef"], then "abcdef", "abefcd", "cdabef", "cdefab", "efabcd", and "efcdab" are all concatenated strings.
 * "acdbef" is not a concatenated substring because it is not the concatenation of any permutation of words.
 *
 * Return the starting indices of all the concatenated substrings in [s].
 * You can return the answer in any order.
 */
@SuppressWarnings("JavadocLinkAsPlainText")
public class SubStringWithConcatenationOfAll {


    public static void main(String[] args) {
        System.out.println(
                new SubStringWithConcatenationOfAll()
                        .findSubstring("aaaaaaxyaa", new String[]{"aa", "aa", "aa", "xy"})
        );
    }

    public List<Integer> findSubstring(String s, String[] words) {
        final int lengthOfConcatenatedSubstring = words.length * words[0].length();
        final List<Integer> result = new ArrayList<>();

        final WordTree t = new WordTree();
        for (int i = 0, wordsLength = words.length; i < wordsLength; i++) {
            String word = words[i];
            t.addWord(word, i);
        }

        //why [i++] and not [i+=wordLength]  ??    ==> if words=["aa","aa"]  and  s="aaaaa"  then we have concatenated substring at [0] and at [1]
        for (int i = 0; i <= s.length() - lengthOfConcatenatedSubstring; i++) { //no need to look for concatenated substring that begins too close to the end
            final boolean match = findConcatenatedSubstring(words, t, s, i);
            if (match) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * Search [s] for a concatenated substring that begins at index [beginIndex]
     * No need to traverse till the end of the string.
     * We know the expected [lengthOfConcatenatedSubstring] because all words must appear and only once.
     */
    private boolean findConcatenatedSubstring(String[] words, WordTree t, String s, int beginIndex) {
        final int lengthOfConcatenatedSubstring = words.length * words[0].length();
        final Boolean[] indicesOfWordsThatWeFound = new Boolean[words.length];

        LetterNode current = t.root;

        for (int i = 0; i < lengthOfConcatenatedSubstring; i++) {
            final Character c = s.charAt(beginIndex + i);
            final LetterNode next = current.getNext(c);
            if (next == null) {
                //no such word
                return false;
            }
            else if (next.isFinal()) {
                final boolean ok = markAsFound(indicesOfWordsThatWeFound, next);
                if (!ok) {
                    //we found a word but it appears too many times
                    return false;
                }
                //after marking the word as found we go back to the root of the tree to find the next familiar word
                current = t.root;
            }
            else {
                //if this is not the end of a word then we continue traversing the tree for the next letter
                current = next;
            }
        }

        //Now let's see if we encountered all the words we need
        return Arrays.stream(indicesOfWordsThatWeFound)
                .reduce((a, b) -> a && b)
                .orElse(false);
    }

    /**
     * We've just found that a familiar word that ends in this letter.
     * We need to go to the [indicesOfWordsThatWeFound] array and mark it as found.
     * Each word has an index, and it has a matching item in array (having the same index).
     * So we set it to true.
     *
     * BUT
     *
     * The list of familiar words is not distinct.
     * We might have the same word 3 times.
     * It might be the first time we find this word or the second or the third...
     * The {@link LetterNode#getIndicesOfWordsThatEndHere()} will contain indices of all three words.
     * We need to get the values of the 3 indices and check to see which is already true and which is not.
     * And mark only one of the false ones.
     * And... if they are all true... it means that we found this word 4 times instead of 3.
     * And we return false.
     * @return false if we see that this word was found too many times; true otherwise
     */
    private boolean markAsFound(Boolean[] indicesOfWordsThatWeFound, LetterNode letterNode) {
        final Set<Integer> indices = letterNode.getIndicesOfWordsThatEndHere();
        for (Integer index : indices) {
            if (indicesOfWordsThatWeFound[index] == null) {
                indicesOfWordsThatWeFound[index] = true;
                return true;
            }
        }
        //if they are all true then we found this word too many times...
        return false;
    }

    /**
     * The tree holds all the familiar words.
     * Each node in the tree is a letter.
     * As you traverse the tree you "collect" letters from familiar words.
     * If a node represents a letter that is the last letter of a familiar word then its {@link LetterNode#isFinal()} will be true.
     * Each familiar word as an index (its index in the original array)
     * So if we have the same word twice we can still differentiate between them.
     */
    private static class WordTree {
        private final LetterNode root = new LetterNode();

        public void addWord(String word, int wordIndex) {
            LetterNode current = root;
            for (int i = 0; i < word.length(); i++) {
                final Character c = word.charAt(i);
                final LetterNode next = current.getNext(c);
                if (next == null) {
                    current = current.addNext(c);
                }
                else {
                    current = next;
                }
            }
            current.markAsEndOfWord(wordIndex);
        }
    }

    private static class LetterNode {
        final Map<Character, LetterNode> nextLetters = new HashMap<>();
        final Set<Integer> indicesOfWordsThatEndHere = new HashSet<>();     //each familiar word has an index and if this letter ends more than one word we will have all their indices in this set

        public LetterNode addNext(Character c) {
            final LetterNode next = new LetterNode();
            nextLetters.put(c, next);
            return next;
        }

        public LetterNode getNext(Character c) {
            return nextLetters.get(c);
        }

        public void markAsEndOfWord(int wordIndex) {
            indicesOfWordsThatEndHere.add(wordIndex);
        }

        public Set<Integer> getIndicesOfWordsThatEndHere() {
            return indicesOfWordsThatEndHere;
        }

        public boolean isFinal() {
            return nextLetters.isEmpty();
        }
    }

}
