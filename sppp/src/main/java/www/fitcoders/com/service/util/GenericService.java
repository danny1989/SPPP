package www.fitcoders.com.service.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Criterion;

public interface GenericService<T, ID extends java.io.Serializable> {

    

    /**
     * Guarda un nuevo registro en la base de datos.
     * Sus relaciones deben estar completas, puesto que puede ocurrir un error 
     * al subir a la base de datos.
     * 
     * @param object nuevo objeto llena con sus relaciones
     * @return retorna el ID del objeto subido.
     * @throws java.lang.Exception
     */
    public Serializable save(T object) throws Exception;

    public void saveCollection(List<T> list) throws Exception;
    
    /**
     * Realiza un <code>update</code> en la base de datos
     * se debe tener en cuenta que el objeto ya debe existir en la
     * base de datos.
     * 
     * @param hql
     * @param parameters
     * @param start
     * @param finish
     * @param clazz
     * @return 
     * @throws java.lang.Exception
     */
    public List listLazyHQL(String hql, Map<String, Object> parameters, int start, int finish, Class clazz, boolean nativeSQL) throws Exception;

    /**
     * Realiza un <code>insert</code>, <code>update</code>,
     * <code>delete</code>, en la base de datos, segun sea el caso
     * de sus relaciones.
     * 
     * @param object Objeto relacionado.
     * @throws java.lang.Exception
     */
    public void saveOrUpdate(T object) throws Exception;

    /**
     * Borra en cascada todas las realciones del objeto en la base de datos
     * 
     * @param object Tiene que tener lleno sus datos para poder borrar todo en cascada.
     * @throws java.lang.Exception
     */
    public void delete(T object) throws Exception;

    /**
     * Metodo que devuelve un objeto completo ingresando como parametro su Id
     * 
     * @param <T>
     * @param type
     * @param id Su identificador de la clase que esta relacionado con la base de 
     * datos
     * 
     * @return Retorna un Objeto del tipo de la misma clase.
     * @throws java.lang.Exception
     */
    public <T> T getById(Class<T> type, Serializable id) throws Exception;
    public <T> T getByHQL(String hql, Map<String, Object> parameters, Class<T> type, boolean nativeSQL) throws Exception;
 
    /**
     * Realiza Update a los campos que no sean entidades
     * @param query
     * @param nativeSQL
     * @return Numero de filas afectadas
     * @throws Exception 
     */
    public int executeQuery(String query, boolean nativeSQL) throws Exception;
    
    /**
     * Realiza Update a los campos que no sean entidades
     * @param hql
     * @param parameters
     * @param clazz
     * @return Numero de filas afectadas
     * @throws Exception 
     */
    
    public List listHQL(String hql, Map<String, Object> parameters, Class clazz) throws Exception;
    public List listSQL(String sql, Map<String, Object> parameters, Class clazz) throws Exception;
    
    /**
     * Se debe crear una lista de objetos Criterion de Hibernate: <ul>
     * <code>List(Criterion) listCriterion = new ArrayList();</code> Ejemplos:
     * </ul> <ul> <li> To get records having salary more than 2000 </li>
     * <code>listCriterion.add(Restrictions.gt("salary", 2000));</code>
     *
     * </ul> <ul><li>To get records having salary less than 2000</li>
     * <code>listCriterion.add(Restrictions.lt("salary", 2000));</code> </ul>
     * <ul> <li>To get records having fistName starting with zara</li>
     * <code>listCriterion.add(Restrictions.like("firstName", "zara%"));</code>
     * </ul> <ul> <li>Case sensitive form of the above restriction.</li>
     * <code>listCriterion.add(Restrictions.ilike("firstName", "zara%"));</code>
     * </ul> <ul> <li> To get records having salary in between 1000 and
     * 2000</li>
     * <code>listCriterion.add(Restrictions.between("salary", 1000, 2000));</code>
     * </ul> <ul> <li> To check if the given property is null</li>
     * <code>listCriterion.add(Restrictions.isNull("salary"));</code> </ul> <ul>
     * <li> To check if the given property is not null</li>
     * <code>listCriterion.add(Restrictions.isNotNull("salary"));</code> </ul>
     * <ul> <li> To check if the given property is empty</li>
     * <code>listCriterion.add(Restrictions.isEmpty("salary"));</code> </ul>
     * <ul> <li> To check if the given property is not empty</li>
     * <code>listCriterion.add(Restrictions.isNotEmpty("salary"));</code> </ul>
     *
     * @param listCriterion Lista de restricciones para realizar la consulta.
     * @return Retorna una lista con condiciones dadas.
     */
    public List listCriterion( List<Criterion> listCriterion ) throws Exception;
    
    public void update(T object) throws Exception;
    public void updateCollection(List<T> object) throws Exception;
    public int executeQuery(String query, Map<String, Object> parameters, boolean nativeSQL) throws Exception;
}