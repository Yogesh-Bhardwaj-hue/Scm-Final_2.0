package com.blogmasterpro;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.blogmasterpro.util.FileExtractor; // Fixed import

public class BlogAnalyticsRunner {
    public static void main(String[] args) {
        try {
            String rootDir = args.length > 0 ? args[0] : ".";
            
            // Find all blog files
            FileExtractor mdExtractor = new FileExtractor(rootDir, ".md");
            FileExtractor htmlExtractor = new FileExtractor(rootDir, ".html");
            
            List<String> mdFiles = mdExtractor.getFiles();
            List<String> htmlFiles = htmlExtractor.getFiles();
            
            // Calculate statistics
            int totalWords = 0;
            int fileCount = 0;
            Map<String, Integer> topicCount = new HashMap<>();
            
            // Common topics to check for
            String[] topics = {"JavaScript", "HTML", "CSS", "React", "Java", "Django", "Python"};
            
            // Process markdown files
            for (String file : mdFiles) {
                if (file.contains(".git") || file.contains("node_modules")) continue;
                String content = new String(Files.readAllBytes(Paths.get(file)));
                int words = BlogAnalytics.countWords(content);
                totalWords += words;
                fileCount++;
                
                // Count topic occurrences
                for (String topic : topics) {
                    int occurrences = BlogAnalytics.countWordOccurrences(content, topic);
                    if (occurrences > 0) {
                        topicCount.put(topic, topicCount.getOrDefault(topic, 0) + occurrences);
                    }
                }
            }
            
            // Process HTML files
            for (String file : htmlFiles) {
                if (file.contains(".git") || file.contains("node_modules")) continue;
                
                // Only process blog content HTML files
                if (file.contains("blog") || file.contains("post")) {
                    String content = new String(Files.readAllBytes(Paths.get(file)));
                    // Extract text content from HTML
                    String textContent = content.replaceAll("<[^>]*>", " ");
                    int words = BlogAnalytics.countWords(textContent);
                    totalWords += words;
                    fileCount++;
                    
                    // Count topic occurrences
                    for (String topic : topics) {
                        int occurrences = BlogAnalytics.countWordOccurrences(textContent, topic);
                        if (occurrences > 0) {
                            topicCount.put(topic, topicCount.getOrDefault(topic, 0) + occurrences);
                        }
                    }
                }
            }
            
            int avgWords = fileCount > 0 ? totalWords / fileCount : 0;
            
            // Print results for the GitHub Action to capture
            System.out.println("Total Words: " + totalWords);
            System.out.println("Average Words per Post: " + avgWords);
            System.out.println("Total Posts: " + fileCount);
            
            // Print topic statistics
            for (Map.Entry<String, Integer> entry : topicCount.entrySet()) {
                if (entry.getValue() > 0) {
                    System.out.println("Topic: " + entry.getKey() + " Count: " + entry.getValue());
                }
            }
            
        } catch (Exception e) {
            System.err.println("Error analyzing blogs: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
