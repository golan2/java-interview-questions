package interview.leetcode.strings;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/word-pattern/
 *
 * Given a [pattern] and a string [s], find if s follows the same pattern.
 * Here follow means a full match, such that there is a bijection between a letter in pattern and a non-empty word in s.
 * (Each letter in pattern represents a word in the string)
 */
@Slf4j
@SuppressWarnings("squid:S1192")
public class WordPattern {
    public static void main(String[] args) {
        log.info("LowLevelWordsIterator");
        testWordPattern(LowLevelWordsIterator::new);
        log.info("SplitWordsIterator");
        testWordPattern(SplitWordsIterator::new);
    }

    public static void testWordPattern(Function<String, Iterator<String>> wordIteratorProvider) {
        final long a = System.nanoTime();
        if (!wordPattern("abba", wordIteratorProvider.apply("dog cat cat dog"))) {
            log.info("case1 FAILED");
        }
        if (wordPattern("aaaaa", wordIteratorProvider.apply("dog dog dog dog"))) {
            log.info("case2 FAILED");
        }
        if (wordPattern("abba", wordIteratorProvider.apply("dog dog dog dog"))) {
            log.info("case3 FAILED");
        }
        if (wordPattern("abba", wordIteratorProvider.apply("dog cat cat dog dog"))) {
            log.info("case4 FAILED");
        }
        final long b = System.nanoTime();
        log.info("Runtime {}ms", (b-a)/1000000);
        log.debug("");
        log.debug("");
        log.debug("");
    }

    private static boolean wordPattern(String pattern, Iterator<String> words) {
        final BijectionMap<String, String> map = new BijectionMap<>();

        try {
            for (int i = 0; i < pattern.length(); i++) {
                final String letter = String.valueOf(pattern.charAt(i));
                if (!words.hasNext()) {
                    log.debug("i=["+i+"] letter=["+letter+"] pattern is longer than string");
                    log.debug("FALSE");
                    return false;
                }
                final String word = words.next();
                final String mappedWord = map.get(letter);
                if (mappedWord == null) {
                    if (map.containsValue(word)) {
                        final String key = map.getKey(word);
                        log.debug("i=["+i+"] letter=["+letter+"] found word [" + word + "] which is already mapped to letter [" + key + "]");
                        log.debug("FALSE");
                        return false;
                    }
                    map.put(letter, word);
                }
                else {
                    if (!word.equals(mappedWord)) {
                        log.debug("i=["+i+"] letter=["+letter+"] expected mappedWord=["+mappedWord+"] but found word=["+word+"]");
                        log.debug("FALSE");
                        return false;
                    }
                }
            }

            if (words.hasNext()) {
                log.debug("The string is longer than pattern");
                log.debug("FALSE");
                return false;
            }

            log.debug("TRUE");
            return true;
        } finally {
            log.debug("map: {}", map);
        }
    }

    static class LowLevelWordsIterator implements Iterator<String> {
        private final String str;
        private int index;

        public LowLevelWordsIterator(String str) {
            this.str = str;
        }

        @Override
        public boolean hasNext() {
            return index < str.length();
        }

        @Override
        public String next() {
            if (!hasNext()) throw new NoSuchElementException();
            skipSpaces();

            int beginIndex = index;
            while (index < str.length() && str.charAt(index) != ' ') {
                index++;
            }
            int endIndex = index;

            skipSpaces();

            return str.substring(beginIndex, endIndex);
        }

        private void skipSpaces() {
            while (index < str.length() && str.charAt(index) == ' ') {
                index++;
            }
        }
    }

    private static class SplitWordsIterator implements Iterator<String> {
        private final String[] arr;
        private int index;

        private SplitWordsIterator(String str) {
            this.arr= Arrays.stream(str.split(" ")).filter(s -> !s.isEmpty()).collect(Collectors.toList()).toArray(String[]::new);
        }

        @Override
        public boolean hasNext() {
            return index < arr.length;
        }

        @Override
        public String next() {
            if (!hasNext()) throw new NoSuchElementException();
            return arr[index++];
        }
    }

}
