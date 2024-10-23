package interview.leetcode.collections;

import java.util.*;

/**
 * 381. Insert Delete GetRandom O(1) - Duplicates allowed
 *
 * https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/
 *
 * RandomizedCollection is a data structure that contains a collection of numbers, possibly duplicates (i.e., a multiset). It should support inserting and removing specific elements and also removing a random element.
 *
 * Implement the RandomizedCollection class:
 *
 * RandomizedCollection() Initializes the empty RandomizedCollection object.
 *  - bool insert(int val) Inserts an item val into the multiset, even if the item is already present. Returns true if the item is not present, false otherwise.
 *  - bool remove(int val) Removes an item val from the multiset if present. Returns true if the item is present, false otherwise. Note that if val has multiple occurrences in the multiset, we only remove one of them.
 *  - int getRandom() Returns a random element from the current multiset of elements. The probability of each element being returned is linearly related to the number of same values the multiset contains. (If the collection contains 1,1,1,2,2 then there is 40% chance to get 2)
 *
 * You must implement the functions of the class such that each function works on average O(1) time complexity.
 *
 * Note: The test cases are generated such that getRandom will only be called if there is at least one item in the RandomizedCollection.
 */
public class RandomizedCollection {
    private final Map<Integer, Set<Integer>> index = new HashMap<>();   // key -> the val we store     value -> the indices in the array where we can find this val
    private final ArrayList<Integer> values = new ArrayList<>();
    private final Random random = new Random();

    public boolean insert(int val) {
        values.add(val);
        final Set<Integer> set = index.computeIfAbsent(val, integer -> new HashSet<>());
        set.add(values.size()-1);
        return set.size() == 1;
    }

    public boolean remove(int val) {
        final Set<Integer> set = index.get(val);
        if (set == null  ||  set.isEmpty()) {
            return false;
        }
        final Iterator<Integer> it = set.iterator();
        final Integer indexOfRemovedValue = it.next();
        it.remove();
        moveLastToHere(indexOfRemovedValue);    //we take the last item in the array and put it in the place of the removed one
        return true;
    }

    private void moveLastToHere(Integer targetIndex) {
        final int moveFrom = values.size() - 1;
        final Integer moveValue = values.remove(moveFrom);
        if (moveFrom == targetIndex) return;    //no move is needed when it is the last one
        values.set(targetIndex, moveValue);
        final Set<Integer> set = this.index.get(moveValue);
        set.remove(moveFrom);
        set.add(targetIndex);
    }

    public int getRandom() {
        return values.get(random.nextInt(values.size()));
    }


    public static void main(String[] args) {
        final RandomizedCollection c = new RandomizedCollection();
        c.insert(1);
        c.insert(2);
        c.insert(1);
        c.remove(2);
        c.remove(2);
        print(c);
    }

    static void print(final RandomizedCollection c) {
        System.out.println(c.values);
        System.out.println(c.index);
    }

}
