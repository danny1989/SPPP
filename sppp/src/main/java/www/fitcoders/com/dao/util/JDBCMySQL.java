/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package www.fitcoders.com.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author max
 */
public class JDBCMySQL {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/bd_erp_cjava";
    public static final String USER = "root";
    public static final String PASSWORD = "CJAVAPERU";
    
    private Connection connection = null;
    
    public JDBCMySQL() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCMySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() throws SQLException {
        if(connection.isClosed()) {
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
                Logger.getLogger(JDBCMySQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
