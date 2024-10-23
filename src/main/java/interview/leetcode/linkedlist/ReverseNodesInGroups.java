package interview.leetcode.linkedlist;


import static interview.leetcode.linkedlist.ListNode.printList;

/**
 * https://leetcode.com/problems/reverse-nodes-in-k-group
 *
 * Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list.
 *
 * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.
 *
 * You may not alter the values in the list's nodes, only nodes themselves may be changed.
 */
@SuppressWarnings("JavadocLinkAsPlainText")
public class ReverseNodesInGroups {


    public static void main(String[] args) {
        new ReverseNodesInGroups().go();
    }

    private void go() {
        final ListNode root = new ListNode(1);
        ListNode a = root;
        for (int i = 2; i < 25; i++) {
            a.next = new ListNode(i);
            a = a.next;
        }

        printList(root);
        ListNode it  = new Solution().reverseKGroup(root, 4);
        printList(it);
    }

    private void a() {
        ListNode segBegin = new ListNode(1);
        ListNode segEnd = segBegin;
        for (int i = 2; i < 11; i++) {
            segEnd.next = new ListNode(i);
            segEnd = segEnd.next;
        }
        System.out.println("[======");
        printList(segBegin);
        System.out.println(segEnd);
        System.out.println("======]");

        ListNode reversed = null;
        segEnd = segBegin;
        while (segBegin != null) {
            ListNode t = segBegin.next;
            segBegin.next = reversed;
            reversed = segBegin;
            segBegin = t;
        }
        segBegin = reversed;

        System.out.println("[======");
        printList(segBegin);
        System.out.println(segEnd);
        System.out.println("======]");


    }


    static class Solution {
        public ListNode reverseKGroup(ListNode head, int k) {
            ListNode resultBegin = null;
            ListNode resultEnd = null;

            ListNode current = head;

            while (current != null) {
                int segLength = 0;
                ListNode segBegin = null;
                ListNode segEnd = null;

                //build segment
                while (current != null && segLength < k) {
                    ListNode tmp = current;
                    current = current.next;
                    //add to the beginning of segment
                    tmp.next = segBegin;
                    segBegin = tmp;
                    if (segEnd == null) {
                        segEnd = segBegin;
                    }
                    segLength++;
                }

                if (segLength < k) {  //this is the last segment and it is reversed so we restore the original order
                    ListNode reversed = null;
                    segEnd = segBegin;
                    while (segBegin != null) {
                        ListNode t = segBegin.next;
                        segBegin.next = reversed;
                        reversed = segBegin;
                        segBegin = t;
                    }
                    segBegin = reversed;
                }

                //add the segment to the end of the result
                if (resultEnd == null) {
                    //empty result so the segment is the result
                    resultBegin = segBegin;
                    resultEnd = segEnd;
                }
                else {
                    resultEnd.next = segBegin;
                    resultEnd = segEnd;
                }
            }

            return resultBegin;
        }

    }


}
