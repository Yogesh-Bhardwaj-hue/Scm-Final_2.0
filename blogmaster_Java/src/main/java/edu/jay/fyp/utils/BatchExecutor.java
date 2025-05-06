package edu.jay.fyp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Utility class to execute batch or shell scripts cross-platform.
 */
public class BatchExecutor {

    /**
     * Executes a batch (.bat) or shell (.sh) script and prints its output.
     * @param batchFilePath The path to the batch or shell script.
     * @return The exit code of the process.
     */
    public int execute(String batchFilePath) {
        Objects.requireNonNull(batchFilePath, "Batch file path cannot be null");
        ProcessBuilder builder;
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            builder = new ProcessBuilder("cmd.exe", "/c", batchFilePath);
        } else {
            builder = new ProcessBuilder("/bin/sh", batchFilePath);
        }

        builder.redirectErrorStream(true);
        int exitCode = -1;
        try {
            Process process = builder.start();

            // Process output
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            exitCode = process.waitFor();
            System.out.println("Batch script executed with exit code: " + exitCode);
        } catch (IOException e) {
            System.err.println("IO error while executing script: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Script execution interrupted.");
        }
        return exitCode;
    }
}
