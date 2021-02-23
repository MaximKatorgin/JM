package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection dbConnectionInstance = null;
    private static final String hostName = "localhost";
    private static final String dbName = "jm";
    private static final String userName = "root";
    private static final String password = "root";

    public static Connection getMYSQLConnection() {
        if (dbConnectionInstance == null) {
            try {
                String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName + "?serverTimezone=UTC";
                dbConnectionInstance = DriverManager.getConnection(connectionURL, userName, password);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return dbConnectionInstance;
    }
}
