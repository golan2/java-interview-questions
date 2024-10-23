package interview.leetcode.trees;

import interview.leetcode.bst.common.TreeNode;

import java.util.Arrays;
import java.util.Iterator;

/**
 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
 *
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
 *
 * Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.
 *
 * Clarification: The input/output format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.
 *
 * Constraints:
 * The number of nodes in the tree is in the range [0, 104].
 * -1000 <= Node.val <= 1000
 */
@SuppressWarnings("JavadocLinkAsPlainText")
public class SerializeBinaryTree {

    private static final Codec CODEC = new Codec();

    public static void main(String[] args) {
        final TreeNode root = TreeFactory.sumTree(13);
        TreePrinter.print(root);
        System.out.println(" - - - - - - - - - - - ");
        final String data = CODEC.serialize(root);
        System.out.println(data);
        System.out.println(Arrays.toString(data.getBytes(java.nio.charset.StandardCharsets.US_ASCII)));
        System.out.println(" - - - - - - - - - - - ");
        final TreeNode deserialized = CODEC.deserialize(data);
        TreePrinter.print(deserialized);

        System.out.println(
                TreeComparator.equals(root, deserialized)
        );


        System.out.println(
                Codec.Converter.convert("[]")
        );
    }

    static class Codec {
        private static final String NULL = "[]";    //This converts to 10995 which is not in te range we need to handle

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) {
                return NULL;
            }
            final StringBuilder buf = new StringBuilder();
            serialize(root, buf);
            return buf.toString();
        }

        private void serialize(TreeNode node, StringBuilder buf) {
            if (node == null) {
                buf.append(NULL);
            }
            else {
                buf.append(Converter.convert(node.val));
                serialize(node.left, buf);
                serialize(node.right, buf);
            }
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            return deserialize(new TokenIterator(data));
        }

        private TreeNode deserialize(TokenIterator iterator) {
            final String a = iterator.next();
            if (NULL.equals(a)) return null;
            final TreeNode node = new TreeNode(Converter.convert(a));
            node.left = deserialize(iterator);
            node.right = deserialize(iterator);
            return node;
        }

        /**
         * Iterate the tokens in the provided string one by one.
         * Eac token is 2 chars so we read them in pairs
         */
        private static class TokenIterator implements Iterator<String> {
            private static final int TOKEN_LENGTH = 2;
            private final String data;
            private int index = 0;


            TokenIterator(String data) {
                this.data = data;
            }

            @Override
            public boolean hasNext() {
                return index <= data.length() - TOKEN_LENGTH;
            }

            @Override
            public String next() {
                return data.substring(index, index+= TOKEN_LENGTH);
            }
        }

        /**
         * Numbers between -1000 and 1000 require 2 bytes to store.
         * 1. "shift" the number (add 1000) so we don't need to serialize the sign (+/-)
         * 2. convert the BASE10 number to BASE128 to fit in a pair of bytes
         * 3. encode the pair of bytes to a string
         */
        private static class Converter {
            private static final int BASE = 128;

            static int convert(String data) {
                final byte[] bytes = data.getBytes(java.nio.charset.StandardCharsets.US_ASCII);
                return (bytes[0] + bytes[1]*BASE) - 1000;
            }

            static String convert(int value) {
                byte[] res = new byte[2];
                res[0] = (byte) ((value+1000) % BASE);
                res[1] = (byte) ((value+1000) / BASE);
                return new String(res, java.nio.charset.StandardCharsets.US_ASCII);
            }
        }
    }
}
