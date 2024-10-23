package interview.leetcode.trie;

import java.util.*;

/**
 * https://leetcode.com/problems/implement-trie-prefix-tree/
 *
 * A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.
 *
 * Implement the Trie class:
 *
 * Trie() Initializes the trie object.
 * void insert(String word) Inserts the string word into the trie.
 * boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise.
 * boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix, and false otherwise.
 *
 *
 */
public class Trie {
    private final TrieNode root = new TrieNode(Character.MIN_VALUE);

    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }
        insert(root, word, 0);
    }

    public boolean search(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        final TrieNode lastLetter = startsWith(root, word, 0);
        return lastLetter != null && lastLetter.end;
    }

    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return true;
        }
        return startsWith(root, prefix, 0) != null;
    }

    /**
     * Returns the node where this prefix ends
     * For example for the prefix "xyz" it will return the node of "z"
     * (assuming this prefix indeed exists... haha  otherwise you get null)
     */
    private TrieNode startsWith(TrieNode node, String prefix, int index) {
        final char letter = prefix.charAt(index);
        final TrieNode nextNode = node.children.get(letter);
        if (nextNode == null) {
            return null;
        }
        if (index == prefix.length() - 1) {
            return nextNode;
        }
        return startsWith(nextNode, prefix, index + 1);
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
        final Trie t = new Trie();

        final List<String> words = new ArrayList<>(200);
        for (int i = 0; i < 200; i++) {
            words.add(UUID.randomUUID().toString());
        }

        words.forEach(t::insert);

        final Boolean searchOk = words
                .stream()
                .map(t::search)
                .reduce((a, b) -> a && b)
                .orElse(false);
        System.out.println("search OK: " + searchOk);

        final Random random = new Random();
        final Boolean prefixOk = words
                .stream()
                .map(word -> {
                    final int length = 1 + random.nextInt(word.length()-2);
                    final String prefix = word.substring(0, length);
                    return t.startsWith(prefix);
                })
                .reduce((a, b) -> a && b)
                .orElse(false);
        System.out.println("prefix OK: " + prefixOk);



    }
}
