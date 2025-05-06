package com.blogmasterpro;

/**
 * Utility class for analyzing blog content
 */
public class BlogAnalytics {

    /**
     * Counts the number of words in a given text
     * @param text the input text to analyze
     * @return the number of words in the text, or 0 if the text is null or empty
     */
    public static int countWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        // Split by whitespace and count non-empty elements
        return text.trim().split("\\s+").length;
    }

    /**
     * Estimates the reading time based on average reading speed
     * @param text the input text to analyze
     * @return estimated reading time in minutes
     */
    public static int estimateReadingTime(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        int wordCount = countWords(text);
        // Average reading speed: 200-250 words per minute
        return Math.max(1, wordCount / 225);
    }

    /**
     * Counts the occurrences of a specific word in the text
     * @param text the input text to analyze
     * @param word the word to count
     * @return the number of occurrences
     */
    public static int countWordOccurrences(String text, String word) {
        if (text == null || word == null || word.isEmpty()) {
            return 0;
        }
        // Convert to lowercase for case-insensitive matching
        String lowerText = text.toLowerCase();
        String lowerWord = word.toLowerCase();

        int count = 0;
        int index = 0;
        while ((index = lowerText.indexOf(lowerWord, index)) != -1) {
            // Ensure we match whole words only
            boolean startOk = (index == 0) || !Character.isLetterOrDigit(lowerText.charAt(index - 1));
            int endIdx = index + lowerWord.length();
            boolean endOk = (endIdx == lowerText.length()) || !Character.isLetterOrDigit(lowerText.charAt(endIdx));
            if (startOk && endOk) {
                count++;
            }
            index += lowerWord.length();
        }
        return count;
    }

    /**
     * Counts the number of sentences in the text.
     * @param text the input text to analyze
     * @return the number of sentences
     */
    public static int countSentences(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        // Split by sentence-ending punctuation
        String[] sentences = text.trim().split("[.!?]+");
        int count = 0;
        for (String s : sentences) {
            if (!s.trim().isEmpty()) count++;
        }
        return count;
    }

    /**
     * Counts the number of paragraphs in the text.
     * @param text the input text to analyze
     * @return the number of paragraphs
     */
    public static int countParagraphs(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        // Split by double newlines or single newlines
        String[] paragraphs = text.trim().split("(\\r?\\n){2,}");
        int count = 0;
        for (String p : paragraphs) {
            if (!p.trim().isEmpty()) count++;
        }
        return count;
    }

    /**
     * Finds the most common word in the text.
     * @param text the input text to analyze
     * @return the most common word, or null if none found
     */
    public static String mostCommonWord(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        String[] words = text.toLowerCase().split("\\W+");
        java.util.Map<String, Integer> freq = new java.util.HashMap<>();
        for (String w : words) {
            if (w.isEmpty()) continue;
            freq.put(w, freq.getOrDefault(w, 0) + 1);
        }
        String mostCommon = null;
        int max = 0;
        for (java.util.Map.Entry<String, Integer> entry : freq.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mostCommon = entry.getKey();
            }
        }
        return mostCommon;
    }

    /**
     * Calculates the average word length in the text.
     * @param text the input text to analyze
     * @return the average word length, or 0.0 if no words
     */
    public static double averageWordLength(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0.0;
        }
        String[] words = text.trim().split("\\s+");
        int totalLength = 0;
        int wordCount = 0;
        for (String w : words) {
            if (!w.isEmpty()) {
                totalLength += w.length();
                wordCount++;
            }
        }
        return wordCount == 0 ? 0.0 : (double) totalLength / wordCount;
    }
}