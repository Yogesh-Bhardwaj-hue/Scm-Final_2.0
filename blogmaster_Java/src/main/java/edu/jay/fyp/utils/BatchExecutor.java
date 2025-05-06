public void execute(String batchFilePath) {
    try {
        ProcessBuilder builder;
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            builder = new ProcessBuilder("cmd.exe", "/c", batchFilePath);
        } else {
            // For Unix-based systems
            builder = new ProcessBuilder("/bin/sh", batchFilePath);
        }
        
        builder.redirectErrorStream(true);
        Process process = builder.start();
        
        // Process output
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        
        int exitCode = process.waitFor();
        System.out.println("Batch script executed with exit code: " + exitCode);
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
}