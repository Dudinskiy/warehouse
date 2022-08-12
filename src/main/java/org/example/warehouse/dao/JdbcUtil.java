package org.example.warehouse.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcUtil {

    public static void free(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
