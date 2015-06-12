/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package www.fitcoders.com.util;

import java.util.List;

/**
 *
 * @author Max
 */
public class SeparatorUtil {

    /**
     * Encia un String de forma:  'param1', 'param2', 'param3'
     * @param list
     * @return 
     */
    public static String separatorList(List<String> list) {
        String concat = "";
        if (list.isEmpty()) {
            concat = "''";
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    concat = concat + "'" + list.get(i) + "'";
                } else {
                    concat = concat + "'" + list.get(i) + "',";
                }

            }
        }
        return concat;
    }

    /**
     * Envia un String de la forma:  1,2,3,4
     * @param list
     * @return 
     */
    public static String separatorListInteger(List<Integer> list) {
        String concat = "";
        if (list.isEmpty()) {
            concat = "";
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    concat = concat + "" + list.get(i) + "";
                } else {
                    concat = concat + "" + list.get(i) + ",";
                }

            }
        }
        return concat;
    }
}
