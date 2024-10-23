package interview.leetcode.linkedlist;

import static interview.leetcode.linkedlist.ListNode.buildList;
import static interview.leetcode.linkedlist.ListNode.printList;

/**
 * https://leetcode.com/problems/swapping-nodes-in-a-linked-list/
 * You are given the head of a linked list, and an integer k.
 * Return the head of the linked list after swapping the values of the kth node from the beginning and the kth node from the end (the list is 1-indexed).
 */
public class SwappingNodesInLinkedListComplicated {

    public static void main(String[] args) {
        final ListNode head = buildList(new int[]{1,2});
        printList(head);
        final SwappingNodesInLinkedListComplicated s = new SwappingNodesInLinkedListComplicated();
        final ListNode newList = s.swapNodes(head, 1);
        printList(newList);
    }

    public ListNode swapNodes(ListNode head, int k) {
        if (k == 1) {
            if (head.next == null) {
                return head;
            }
            ListNode bl = head;
            while (bl.next.next != null) {
                bl = bl.next;
            }
            if (bl == head) {
                head = head.next;
                head.next = bl;
                bl.next = null;
                return head;
            }
            else {
                bl.next.next = head.next;
                ListNode newHead = bl.next;
                bl.next = head;
                head.next = null;
                return newHead;
            }
        }


        ListNode first = head;
        for (int i=0 ; i < k-2 ; i++) {
            first = first.next;
        }

        if (first.next.next == null) {   //k==n
            ListNode temp = head;
            first.next.next = head.next;
            head.next = null;
            head = first.next;
            first.next = temp;
        }
        else {
            ListNode second = head;
            ListNode scout = first.next;
            while (scout.next.next != null) {
                scout = scout.next;
                second = second.next;
            }
            swap(first, second);
        }
        return head;
    }

    private void swap(ListNode first, ListNode second) {
        ListNode temp = first.next;
        first.next = second.next;
        ListNode temp2 = second.next.next;
        second.next.next = temp.next;
        second.next = temp;
        temp.next = temp2;
    }

}
