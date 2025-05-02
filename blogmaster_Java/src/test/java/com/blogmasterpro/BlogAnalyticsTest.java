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