package edu.jay.fyp.featureextractor.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.jay.fyp.featureextractor.video.Videomatch;

/**
 * Database utility for STIP video matching.
 */
public class StipDB {

    /**
     * Finds the parent file path for a given STIP path by checking the videostip table.
     * @param stipPath The STIP path to check.
     * @return The parent file path if found, otherwise null.
     */
    public String getParentFilePath(String stipPath) {
        Videomatch videomatch = new Videomatch();
        String res = null;
        String query = "SELECT * FROM videostip";
        try (
            Connection connection = new DBHelper().getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                String parentStip = rs.getString(3);
                if (videomatch.isPartOf(stipPath, parentStip)) {
                    res = rs.getString(2);
                    break;
                }
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}