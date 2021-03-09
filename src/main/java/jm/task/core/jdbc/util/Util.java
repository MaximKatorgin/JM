package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static Connection dbConnectionInstance = null;
    private static SessionFactory sessionFactoryInstance = null;
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

    public static Session getSession() {
        if (sessionFactoryInstance == null) {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/jm?serverTimezone=UTC");
            prop.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
            prop.setProperty("hibernate.connection.username", "root");
            prop.setProperty("hibernate.connection.password", "root");
            prop.setProperty("hibernate.show_sql", "true");
            prop.setProperty("hibernate.hbm2ddl.auto","create");
            prop.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            sessionFactoryInstance = new Configuration().addProperties(prop).addAnnotatedClass(User.class)
                    .buildSessionFactory();
            return sessionFactoryInstance.openSession();
        } else {
            return sessionFactoryInstance.openSession();
        }
    }



}
