package interview.leetcode.dictionary;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * https://leetcode.com/problems/design-add-and-search-words-data-structure/
 *
 * Design a data structure that supports adding new words and finding if a string matches any previously added string.
 *
 * Implement the WordDictionary class:
 *
 * WordDictionary() Initializes the object.
 * void addWord(word) Adds word to the data structure, it can be matched later.
 * bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise. word may contain dots '.' where dots can be matched with any letter.
 */
public class WordDictionary {
    private final TrieNode root = new TrieNode(Character.MIN_VALUE);

    public void addWord(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        insert(root, word, 0);
    }

    public boolean search(String word) {
        return search(root, word, 0);
    }

    public boolean search(TrieNode node, String word, int index) {
        if (index >= word.length()) {
            return node.end;
        }
        final char letter = word.charAt(index);
        if ('.' == letter) {
            final Collection<TrieNode> children = node.children.values();
            for (TrieNode child : children) {
                if (search(child, word, index + 1)) {
                    return true;
                }
            }
            return false;
        }
        else {
            final TrieNode child = node.children.get(letter);
            if (child == null) {
                return false;
            }
            else {
                return search(child, word, index + 1);
            }
        }
    }

    private void insert(TrieNode node, String prefix, int index) {
        final char letter = prefix.charAt(index);
        final TrieNode child = getOrCreateChildNodeForLetter(node, letter);

        if (index == prefix.length()-1) {
            child.end = true;
        }
        else {
            insert(child, prefix, index + 1);
        }
    }

    private TrieNode getOrCreateChildNodeForLetter(TrieNode node, char letter) {
        final TrieNode nextNode;
        if (!node.children.containsKey(letter)) {
            nextNode = new TrieNode(letter);
            node.children.put(letter, nextNode);
        }
        else {
            nextNode = node.children.get(letter);
        }
        return nextNode;
    }


    private static class TrieNode {
        char letter;
        boolean end = false;
        Map<Character, TrieNode> children = new HashMap<>();

        public TrieNode(char letter) {
            this.letter = letter;
        }

        @Override
        public String toString() {
            return letter + (end ? "*" : "");
        }
    }


    public static void main(String[] args) {
        final WordDictionary d = new WordDictionary();
        IntStream
                .range(0, 10000)
                .forEach(a -> d.addWord( String.format("%04d", a) ));

        d.addWord("w24");

        System.out.println(  d.search("1...")  );
        System.out.println(  d.search(".1..")  );
        System.out.println(  d.search("...1")  );
        System.out.println(  d.search("....")  );
        System.out.println(  d.search("...")  );        //w24
        System.out.println(  d.search(".....")  );

    }
}
