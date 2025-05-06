package com.blogmasterpro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.blogmasterpro.util.FileExtractor;

public class BlogAnalyticsRunner {
    private static final String[] TOPICS = {"JavaScript", "HTML", "CSS", "React", "Java", "Django", "Python"};
    private static final List<String> SKIP_DIRS = Arrays.asList(".git", "node_modules");

    public static void main(String[] args) {
        String rootDir = args.length > 0 ? args[0] : ".";
        int totalWords = 0;
        int fileCount = 0;
        Map<String, Integer> topicCount = new HashMap<>();

        try {
            FileExtractor mdExtractor = new FileExtractor(rootDir, ".md");
            FileExtractor htmlExtractor = new FileExtractor(rootDir, ".html");

            List<String> mdFiles = mdExtractor.getFiles();
            List<String> htmlFiles = htmlExtractor.getFiles();

            // Process markdown files
            for (String file : mdFiles) {
                if (shouldSkip(file)) continue;
                String content = readFileContent(file);
                if (content == null) continue;
                totalWords += BlogAnalytics.countWords(content);
                fileCount++;
                countTopics(content, topicCount);
            }

            // Process HTML files
            for (String file : htmlFiles) {
                if (shouldSkip(file)) continue;
                if (file.contains("blog") || file.contains("post")) {
                    String content = readFileContent(file);
                    if (content == null) continue;
                    String textContent = content.replaceAll("<[^>]*>", " ");
                    totalWords += BlogAnalytics.countWords(textContent);
                    fileCount++;
                    countTopics(textContent, topicCount);
                }
            }

            int avgWords = fileCount > 0 ? totalWords / fileCount : 0;

            System.out.println("Total Words: " + totalWords);
            System.out.println("Average Words per Post: " + avgWords);
            System.out.println("Total Posts: " + fileCount);

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

    private static boolean shouldSkip(String filePath) {
        for (String skip : SKIP_DIRS) {
            if (filePath.contains(skip)) return true;
        }
        return false;
    }

    private static String readFileContent(String file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            System.err.println("Failed to read file: " + file + " (" + e.getMessage() + ")");
            return null;
        }
    }

    private static void countTopics(String content, Map<String, Integer> topicCount) {
        for (String topic : TOPICS) {
            int occurrences = BlogAnalytics.countWordOccurrences(content, topic);
            if (occurrences > 0) {
                topicCount.put(topic, topicCount.getOrDefault(topic, 0) + occurrences);
            }
        }
    }
}
