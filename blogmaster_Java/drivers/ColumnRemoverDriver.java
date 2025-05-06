package edu.jay.fyp.featureextractor.drivers;

import java.io.IOException;
import edu.jay.fyp.featureextractor.ColumnNamer;
import edu.jay.fyp.featureextractor.Converters;

/**
 * Driver class to convert CSV features to Excel, name columns, convert back to CSV, and then to ARFF.
 */
public class ColumnRemoverDriver {

    // Use configurable paths or pass as arguments for flexibility
    private static final String DIR = "D:\\fyp\\hollywood\\csv features";
    private static final String EXCEL_PATH = "D:\\fyp\\hollywood\\excel features";
    private static final String FINAL_CSV = "D:\\fyp\\hollywood\\final CSV";

    public static void main(String[] args) {
        Converters converters = new Converters();
        try {
            // Step 1: Convert CSV features to Excel format
            converters.toExcel(DIR, EXCEL_PATH);

            // Step 2: Name columns in all Excel files
            ColumnNamer columnNamer = new ColumnNamer();
            columnNamer.nameColumns(EXCEL_PATH, ".xls");

            // Step 3: Convert Excel files back to CSV
            converters.toCSV(EXCEL_PATH, FINAL_CSV);

            // Step 4: Convert final CSV to ARFF format for ML tools
            converters.CSV2Arff(FINAL_CSV);

            System.out.println("All conversions completed successfully.");
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}