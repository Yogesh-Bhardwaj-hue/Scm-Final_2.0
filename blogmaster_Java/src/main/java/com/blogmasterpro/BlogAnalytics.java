package com.blogmasterpro;

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
    
    // Add more analytics methods here as needed
}