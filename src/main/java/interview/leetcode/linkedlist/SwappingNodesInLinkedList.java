package interview.leetcode.linkedlist;

import static interview.leetcode.linkedlist.ListNode.buildList;
import static interview.leetcode.linkedlist.ListNode.printList;

/**
 * https://leetcode.com/problems/swapping-nodes-in-a-linked-list/
 * You are given the head of a linked list, and an integer k.
 * Return the head of the linked list after swapping the values of the kth node from the beginning and the kth node from the end (the list is 1-indexed).
 */
public class SwappingNodesInLinkedList {

    public static void main(String[] args) {
        final ListNode head = buildList(new int[]{1,2,3,4});
        printList(head);
        final SwappingNodesInLinkedList s = new SwappingNodesInLinkedList();
        final ListNode newList = s.swapNodes(head, 3);
        printList(newList);
    }

    public ListNode swapNodes(ListNode head, int k) {
        if (k == 1 && head.next == null) {
            return head;
        }

        ListNode first = head;
        for (int i = 1; i < k; i++) {
            first = first.next;
        }

        ListNode scout = first;
        ListNode second = head;
        while (scout.next != null) {
            scout = scout.next;
            second = second.next;
        }

        int temp = first.val;
        first.val = second.val;
        second.val = temp;

        return head;
    }
}
