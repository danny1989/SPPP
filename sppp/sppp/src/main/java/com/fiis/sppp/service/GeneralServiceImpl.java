/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fiis.sppp.dao.hibernate.GeneralDao;

/**
 *
 * @author Danny
 */
@Service("generalService")
public class GeneralServiceImpl implements GeneralService, Serializable {

    @Autowired
    GeneralDao generalDao;

    @Override
    public Serializable save(Object object) throws Exception {
        return generalDao.save(object);
    }

    @Override
    public void saveCollection(List<Object> list) throws Exception {
        generalDao.saveCollection(list);
    }

    @Override
    public List listLazyHQL(String hql, Map<String, Object> parameters, int start, int finish, Class clazz, boolean nativeSQL) throws Exception {
        return generalDao.listLazyHQL(hql, parameters, start, finish, clazz, nativeSQL);
    }

    @Override
    public void saveOrUpdate(Object object) throws Exception {
        generalDao.saveOrUpdate(object);
    }

    @Override
    public void delete(Object object) throws Exception {
        generalDao.delete(object);
    }

    @Override
    public <T> T getById(Class<T> type, Serializable id) throws Exception {
        return generalDao.getById(type, id);
    }

    @Override
    public <T> T getByHQL(String hql, Map<String, Object> parameters, Class<T> type, boolean nativeSQL) throws Exception {
        return generalDao.getByHQL(hql, parameters, type, nativeSQL);
    }

    @Override
    public int executeQuery(String query, boolean nativeSQL) throws Exception {
        return generalDao.executeQuery(query, nativeSQL);
    }

    @Override
    public List listHQL(String hql, Map<String, Object> parameters, Class clazz) throws Exception {
        return generalDao.listHQL(hql, parameters, clazz);
    }

    @Override
    public List listSQL(String sql, Map<String, Object> parameters, Class clazz) throws Exception {
        return generalDao.listSQL(sql, parameters, clazz);
    }

    @Override
    public List listCriterion(List<Criterion> listCriterion) throws Exception {
        return generalDao.listCriterion(listCriterion);
    }

    @Override
    public void update(Object object) throws Exception {
        generalDao.update(object);
    }

    @Override
    public void updateCollection(List<Object> object) throws Exception {
        generalDao.updateCollection(object);
    }

    @Override
    public int executeQuery(String query, Map<String, Object> parameters, boolean nativeSQL) throws Exception {
        return generalDao.executeQuery(query, parameters, nativeSQL);
    }

}
