package interview.leetcode.collections;

import org.junit.jupiter.api.Test;

public class MedianFinder100Test {


    @Test
    public void a() {
        final MedianFinder100.BucketArray arr = new MedianFinder100.BucketArray();
        arr.add(1);
        arr.add(1);
        arr.add(8);
        arr.add(3);
        arr.add(5);
        arr.add(5);

        final MedianFinder100.BucketArrayIterator it = arr.iterator();
        while (it.hasNext()) {
            System.out.println( it.next() );

        }

        System.out.println("---");

        while (it.hasPrevious()) {
            System.out.println( it.previous() );

        }

    }

}