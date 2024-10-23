package interview.leetcode.linkedlist;

@SuppressWarnings("unused")
public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    static ListNode buildList(int[] arr) {
        ListNode root = new ListNode(arr[arr.length-1]);
        for (int i = arr.length-2; i >=0 ; i--) {
            root = new ListNode(arr[i], root);
        }
        return root;
    }

    static void printList(ListNode root) {
        while (root != null) {
            System.out.print(root + ", ");
            root = root.next;
        }
        System.out.println();
    }

    public String toString() {
        return "" + val;
    }
}
