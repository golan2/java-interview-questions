package interview.recursion.permutations;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given an array of coins and a sum.
 * List all combinations of coins to get the given sum
 * Option 1:
 * Unlimited coins of each type.
 * So they array is just the "types" of coins we have in the game.
 * Actually the array can be a Set
 * If the Set has a single value "1" then "1,1,1,1,1" is a valid answer for "sum=5"
 * Option 2:
 * The array of coins represents actual coins.
 * Once you used a coin for the sum in a certain combination it is "removed" from the array.
 * The array can represent the content of a wallet or deposit box.
 * For this array {1,2,5} the answer "1,1,1,1,1" is NOT valid for "sum=5"
 */
public class SumCoins {

    public static void main(String[] args) {
        final Set<Integer> coins = new HashSet<>(Arrays.asList(2,5,10));
        final int sum = 7;

        Set<Permutation> results = comb1(coins, sum);

        System.out.println(results.size());

    }

    private static Set<Permutation> comb1(Set<Integer> coins, int sum) {
        final Set<Permutation> results = new HashSet<>();

        for (Integer coin : coins) {
            if (coin < sum) {
                final Set<Permutation> combinations = comb1(coins, sum - coin);
                for (Permutation perm : combinations) {
                    perm.add(coin);
                }
                results.addAll(combinations);
            }
            else if (coin==sum) {
                results.add(Permutation.createSingleCoinPermutation(coin));
            }
        }

        System.out.println("sum=["+sum+"] results=["+results.stream().map(Object::toString).collect(Collectors.joining(" ; "))+"] ");
        return results;
    }

    private static class Permutation {
        final Map<Integer, Integer> coins = new HashMap<>();    //key=coin ; value=count

        static Permutation createSingleCoinPermutation(Integer coin) {
            final Permutation result = new Permutation();
            result.add(coin);
            return result;
        }

        public void add(Integer coin) {
            this.coins.merge(coin, 1, (a, b) -> a + b);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Permutation that = (Permutation) o;
            return Objects.equals(coins, that.coins);
        }

        @Override
        public int hashCode() {
            return Objects.hash(coins);
        }

        @Override
        public String toString() {
            final StringBuilder buf = new StringBuilder();
            for (Integer coin : this.coins.keySet().stream().sorted().collect(Collectors.toList())) {
                final Integer count = this.coins.get(coin);
                for (int i=0 ; i<count ; i++) {
                    buf.append(coin).append(",");
                }
            }
            return "{" + buf.toString() + '}';
        }
    }
}
