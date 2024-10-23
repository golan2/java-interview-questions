package interview.leetcode.linkedlist;

import java.util.Arrays;

import static interview.leetcode.linkedlist.ListNode.buildList;

/**
 * https://leetcode.com/problems/merge-k-sorted-lists/
 *
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 * Merge all the linked-lists into one sorted linked-list and return it.
 */
@SuppressWarnings("JavadocLinkAsPlainText")
public class MergeSortedLists {

    public ListNode mergeKLists(ListNode[] lists) {
        return lists[recursiveMerge(lists, 0, lists.length - 1)];
    }


    private int recursiveMerge(ListNode[] lists, int left, int right) {
        if (right == left) {
            return left;
        }
        else if (right-left == 1) {
            lists[left] = mergeTwoLists(lists[left], lists[right]);
            lists[right] = null;
            return left;
        }
        else {
            final int mid = left + (right - left) / 2;
            final int a = recursiveMerge(lists, left, mid);
            final int b = recursiveMerge(lists, mid + 1, right);
            if (a == b) {
                return a;
            }
            else {
                lists[a] = mergeTwoLists(lists[a], lists[b]);
                lists[b] = null;
                return a;
            }
        }
    }

    private static ListNode mergeTwoLists(ListNode a, ListNode b) {
        L result = new L();
        while (a != null  ||  b != null) {
            if (b == null) {
                a = result.addAndNext(a);
            }
            else if (a == null) {
                b = result.addAndNext(b);
            }
            else if (a.val < b.val) {
                a = result.addAndNext(a);
            }
            else {
                b = result.addAndNext(b);
            }
        }
        return result.head;
    }


    private static class L {
        ListNode head = null;
        ListNode tail = null;

        ListNode addAndNext(ListNode a) {
            if (head == null) {
                head = a;
            }
            else {
                tail.next = a;
            }
            tail = a;
            ListNode res = tail.next;
            tail.next = null;
            return res;
        }
    }


    public static void main(String[] args) {
        ListNode[] lists = new ListNode[]{
                buildList(new int[]{10,14,15}),
                buildList(new int[]{10,14,15}),
                buildList(new int[]{1,3,5,7,9}),
                buildList(new int[]{1,3,5,7,9}),
                buildList(new int[]{2,4,6,8}),
                buildList(new int[]{2,4,6,8})
        };


        print( new MergeSortedLists().mergeKLists(lists) );


        System.out.println(Arrays.toString(lists));
    }

    private static void print(ListNode root) {
        while (root != null) {
            System.out.printf("%d, ", root.val);
            root = root.next;
        }
        System.out.println("||");
    }


}
