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
     * Analyzes the reading time based on average reading speed
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
        if (text == null || word == null) {
            return 0;
        }
        
        // Convert to lowercase for case-insensitive matching
        String lowerText = text.toLowerCase();
        String lowerWord = word.toLowerCase();
        
        int count = 0;
        int index = 0;
        
        while ((index = lowerText.indexOf(lowerWord, index)) != -1) {
            count++;
            index += lowerWord.length();
        }
        
        return count;
    }
}