/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.dao.hibernate;

import java.io.Serializable;
import org.springframework.stereotype.Repository;
import com.fiis.sppp.dao.util.GenericEntityDaoImpl;

/**
 *
 * @author Danny
 */
@Repository("generalDao")
public class GeneralDaoImpl extends GenericEntityDaoImpl<Object, Serializable> implements GeneralDao{
   
    
}
