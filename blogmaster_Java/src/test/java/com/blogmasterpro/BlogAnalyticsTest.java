package com.blogmasterpro;

import org.junit.Test;
import static org.junit.Assert.*;

public class BlogAnalyticsTest {

    @Test
    public void testCountWords() {
        assertEquals(5, BlogAnalytics.countWords("This is a test blog."));
        assertEquals(0, BlogAnalytics.countWords(""));
        assertEquals(0, BlogAnalytics.countWords(null));
    }
}

import java.util.*;
import java.util.regex.Pattern;

/**
 * Utility class for blog analytics.
 */
public class BlogAnalytics {

    /**
     * Counts the number of words in a blog post.
     * @param content The blog content.
     * @return The word count.
     */
    public static int countWords(String content) {
        if (content == null || content.trim().isEmpty()) return 0;
        return content.trim().split("\\s+").length;
    }

    /**
     * Counts the number of sentences in a blog post.
     * @param content The blog content.
     * @return The sentence count.
     */
    public static int countSentences(String content) {
        if (content == null || content.trim().isEmpty()) return 0;
        // Simple sentence split by ., !, or ?
        String[] sentences = content.trim().split("[.!?]+\\s*");
        return (int) Arrays.stream(sentences).filter(s -> !s.trim().isEmpty()).count();
    }

    /**
     * Counts the number of paragraphs in a blog post.
     * @param content The blog content.
     * @return The paragraph count.
     */
    public static int countParagraphs(String content) {
        if (content == null || content.trim().isEmpty()) return 0;
        String[] paragraphs = content.trim().split("(\\r?\\n){2,}");
        return (int) Arrays.stream(paragraphs).filter(p -> !p.trim().isEmpty()).count();
    }

    /**
     * Finds the most common word in the blog post (ignoring case and punctuation).
     * @param content The blog content.
     * @return The most common word, or null if none.
     */
    public static String mostCommonWord(String content) {
        if (content == null || content.trim().isEmpty()) return null;
        Map<String, Integer> freq = new HashMap<>();
        String[] words = content.toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+");
        for (String word : words) {
            if (word.isEmpty()) continue;
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }
        return freq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Calculates the average word length in the blog post.
     * @param content The blog content.
     * @return The average word length, or 0 if no words.
     */
    public static double averageWordLength(String content) {
        if (content == null || content.trim().isEmpty()) return 0.0;
        String[] words = content.trim().split("\\s+");
        int totalLength = 0, count = 0;
        for (String word : words) {
            String clean = word.replaceAll("[^a-zA-Z0-9]", "");
            if (!clean.isEmpty()) {
                totalLength += clean.length();
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) totalLength / count;
    }

    public static void main(String[] args) {
        String sample = "Welcome to BlogMaster Pro! This is a test blog post. " +
                "It has multiple sentences. And even paragraphs.\n\n" +
                "Here's a new paragraph. Enjoy blogging!";

        System.out.println("Word count: " + countWords(sample));
        System.out.println("Sentence count: " + countSentences(sample));
        System.out.println("Paragraph count: " + countParagraphs(sample));
        System.out.println("Most common word: " + mostCommonWord(sample));
        System.out.printf("Average word length: %.2f\n", averageWordLength(sample));
    }
}