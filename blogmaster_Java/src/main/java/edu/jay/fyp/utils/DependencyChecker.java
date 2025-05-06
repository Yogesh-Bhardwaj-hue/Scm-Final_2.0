package edu.jay.fyp.utils;

import java.io.File;
import java.io.IOException;

public class DependencyChecker {
    
    public static boolean checkExternalTools() {
        boolean allToolsAvailable = true;
        
        // Check Java dependencies
        allToolsAvailable &= checkMavenDependencies();
        
        // Check external executables
        allToolsAvailable &= checkExecutable("ffmpeg");
        allToolsAvailable &= checkExecutable("python");
        
        return allToolsAvailable;
    }
    
    private static boolean checkMavenDependencies() {
        try {
            ProcessBuilder builder = new ProcessBuilder("mvn", "dependency:resolve");
            builder.directory(new File("./blogmaster_Java"));
            Process process = builder.start();
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            System.err.println("Error checking Maven dependencies: " + e.getMessage());
            return false;
        }
    }
    
    private static boolean checkExecutable(String name) {
        try {
            ProcessBuilder builder;
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                builder = new ProcessBuilder("where", name);
            } else {
                builder = new ProcessBuilder("which", name);
            }
            
            Process process = builder.start();
            boolean exists = process.waitFor() == 0;
            
            if (!exists) {
                System.err.println("Required executable not found: " + name);
            }
            
            return exists;
        } catch (IOException | InterruptedException e) {
            System.err.println("Error checking for executable " + name + ": " + e.getMessage());
            return false;
        }
    }
    
    public static void main(String[] args) {
        boolean ready = checkExternalTools();
        System.out.println("All dependencies available: " + ready);
    }
}