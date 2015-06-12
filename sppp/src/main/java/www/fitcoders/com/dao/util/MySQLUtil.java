/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package www.fitcoders.com.dao.util;

/**
 *
 * @author CJAVAPERU
 */
public class MySQLUtil {
    
    private static JDBCMySQL jdbcMySQL;

    public static JDBCMySQL getJdbcMySQL() {
        if(jdbcMySQL == null) {
            jdbcMySQL = new JDBCMySQL();
        }
        return jdbcMySQL;
    }

    public static void setJdbcMySQL(JDBCMySQL jdbcMySQL) {
        MySQLUtil.jdbcMySQL = jdbcMySQL;
    }
    
    
    
}
