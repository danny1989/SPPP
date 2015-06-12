package www.fitcoders.com.dao.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GenericEntityDaoImpl<T, ID extends Serializable> implements GenericEntityDao<T, Serializable>, Serializable {

    @Autowired
    protected SessionFactory sessionFactory;

    private final Class<T> oClass;

    public GenericEntityDaoImpl() {
        this.oClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    @Override
    public Class<T> getObjectClass() {
        return this.oClass;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public Serializable save(T objeto) throws Exception {
        return getSessionFactory().getCurrentSession().save(objeto);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void saveCollection(List<T> object) throws Exception {
        Session ses = getSessionFactory().getCurrentSession();
        for (T model : object) {
            ses.save(model);
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void saveOrUpdate(T object) throws Exception {
        getSessionFactory().getCurrentSession().saveOrUpdate(object);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(T objeto) throws Exception {
        getSessionFactory().getCurrentSession().delete(objeto);
    }

    @Transactional(readOnly = false)
    @Override
    public int executeQuery(String query, boolean nativeSQL) {
        if (nativeSQL) {
            return getSessionFactory().getCurrentSession().createSQLQuery(query).executeUpdate();
        } else {
            return getSessionFactory().getCurrentSession().createQuery(query).executeUpdate();
        }
    }

    @Transactional(readOnly = false)
    @Override
    public int executeQuery(String query, Map<String, Object> parameters, boolean nativeSQL) {
        Session ses = getSessionFactory().getCurrentSession();
        Query q;
        if (nativeSQL) {
            q = ses.createSQLQuery(query);
        } else {
            q = ses.createQuery(query);
        }
        if (parameters != null) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return q.executeUpdate();
    }

    @Override
    public <T> T getById(Class<T> type, Serializable id) {
        return (T) getSessionFactory().getCurrentSession().get(type, id);
    }

    @Override
    public <T> T getByHQL(String hql, Map<String, Object> parameters, Class<T> type, boolean nativeSQL) {
        Query que;
        if(nativeSQL) {
            que = getSessionFactory().getCurrentSession().createSQLQuery(hql);
        } else {
            que = getSessionFactory().getCurrentSession().createQuery(hql);
        }
        if (parameters != null) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                que.setParameter(entry.getKey(), entry.getValue());
            }
        }
        if (type != null) {
            que.setResultTransformer(Transformers.aliasToBean(type));
        }

        return (T) que.uniqueResult();
    }

    @Override
    public List listHQL(String hql, Map<String, Object> parameters, Class clazz) {
        Query query;
        if (clazz == null) {
            query = getSessionFactory().getCurrentSession().createQuery(hql);
        } else {
            query = getSessionFactory().getCurrentSession().createQuery(hql).setResultTransformer(Transformers.aliasToBean(clazz));
        }
        if (parameters != null) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.list();
    }

    @Override
    public List listSQL(String sql, Map<String, Object> parameters, Class clazz) {
        Query query;
        if (clazz == null) {
            query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
        } else {
            query = getSessionFactory().getCurrentSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(clazz));
        }
        if (parameters != null) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.list();
    }

    @Override
    public List listLazyHQL(String hql, Map<String, Object> parameters, int start, int finish, Class clazz, boolean nativeSQL) {
        Query query;
        if (clazz == null) {
            if (nativeSQL) {
                query = getSessionFactory().getCurrentSession().createSQLQuery(hql);
            } else {
                query = getSessionFactory().getCurrentSession().createQuery(hql);
            }
        } else {
            if(nativeSQL){
                query = getSessionFactory().getCurrentSession().createSQLQuery(hql).setResultTransformer(Transformers.aliasToBean(clazz));
            } else {
                query = getSessionFactory().getCurrentSession().createQuery(hql).setResultTransformer(Transformers.aliasToBean(clazz));
            }
        }
        if (parameters != null) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        query.setFirstResult(start)
                .setMaxResults(finish);
        return query.list();
    }

    @Override
    public List listCriterion(List<Criterion> listCriterion) {
        Criteria criteria = getSessionFactory().getCurrentSession()
                .createCriteria(oClass);
        for (int i = 0; i < listCriterion.size(); i++) {
            criteria.add(listCriterion.get(i));
        }
        return criteria.list();
    }

    @Transactional(readOnly = false)
    @Override
    public void update(T object) throws Exception {
        getSessionFactory().getCurrentSession()
                .update(object);
    }

    @Transactional(readOnly = false)
    @Override
    public void updateCollection(List<T> object) throws Exception {
        Session ses = getSessionFactory().getCurrentSession();
        for (T model : object) {
            ses.update(model);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
