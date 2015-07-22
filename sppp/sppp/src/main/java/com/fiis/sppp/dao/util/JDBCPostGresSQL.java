/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danny
 */
public class JDBCPostGresSQL {
    public static final String DRIVER = "org.postgresql.Driver";
    public static final String URL = "jdbc:postgresql://localhost:5432/db_sppp";
    public static final String USER = "postgres";
    public static final String PASSWORD = "admin";
    
    private Connection connection = null;
    public JDBCPostGresSQL() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCPostGresSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCPostGresSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() throws SQLException {
        if(connection == null) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } else if(connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public void closeConnection() {
        if( connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(JDBCPostGresSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
