package com.blogmasterpro.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FileExtractor {
    private String dirPath;
    private String extension;
    
    public FileExtractor(String dirPath, String extension) {
        this.dirPath = dirPath;
        this.extension = extension;
    }
    
    public List<String> getFiles() {
        List<String> filePaths = new ArrayList<>();
        File dir = new File(dirPath);
        
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(extension.toLowerCase());
                }
            });
            
            if (files != null) {
                for (File file : files) {
                    filePaths.add(file.getAbsolutePath());
                }
            }
        }
        
        return filePaths;
    }
}