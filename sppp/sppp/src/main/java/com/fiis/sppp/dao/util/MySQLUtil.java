/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.dao.util;

/**
 *
 * @author Danny
 */
public class MySQLUtil {
    
    private static JDBCMySQL jdbcMySQL;
    private static JDBCPostGresSQL jdbcPostGresSQL;
    
    
    public static JDBCMySQL getJdbcMySQL() {
        if(jdbcMySQL == null) {
            jdbcMySQL = new JDBCMySQL();
        }
        return jdbcMySQL;
    }

    public static void setJdbcMySQL(JDBCMySQL jdbcMySQL) {
        MySQLUtil.jdbcMySQL = jdbcMySQL;
    }

    public static JDBCPostGresSQL getJdbcPostGresSQL() {
        if(jdbcPostGresSQL == null ) {
            jdbcPostGresSQL = new JDBCPostGresSQL();
        }
        return jdbcPostGresSQL;
    }

    public static void setJdbcPostGresSQL(JDBCPostGresSQL jdbcPostGresSQL) {
        MySQLUtil.jdbcPostGresSQL = jdbcPostGresSQL;
    }
    
    
    
}
