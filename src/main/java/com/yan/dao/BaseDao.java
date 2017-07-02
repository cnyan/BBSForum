package com.yan.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 闫继龙 on 2017/7/1.
 * DAO 的基类
 */
public class BaseDao<T>{

    private Class<T> entityClass;
    private HibernateTemplate hibernateTemplate;

    /**
     * 通过反射，获取子类确定的泛型类
     */
    public  BaseDao(){

        Type genType = getClass().getGenericSuperclass();

        Type[] params = ((ParameterizedType) genType) .getActualTypeArguments();

        entityClass = (Class<T>) params[0];
    }

    /**
     * 根据ID 加载PO实例
     * @param id
     * @return
     */
    public T load(Serializable id){
        return  (T) getHibernateTemplate().load(entityClass, id);
    }

    /**
     * 根据ID，获取PO实例
     * @param id
     * @return
     */
    public List<T> get(Serializable id){
        return (List<T>) getHibernateTemplate().get(entityClass,id);
    }

    /**
     * 获取PO所有对象
     * @return
     */
    public List<T> loadAll(){
        return getHibernateTemplate().loadAll(getEntityClass());
    }

    /**
     * 保存PO
     * @param entity
     */
    public void save(T entity){
         getHibernateTemplate().save(entity);
    }

    /**
     * 删除PO
     * @param entity
     */
    public void remove(T entity){
        getHibernateTemplate().delete(entity);
    }

    /**
     * 更改PO
     * @param entity
     */
    public void update(T entity){
        getHibernateTemplate().update(entity);
    }


    /**
     * 执行HQL 查询
     * @param hql
     * @return
     */
    public List find(String hql){

        return  this.getHibernateTemplate().find(hql);

    }


    /**
     * 执行带参数的HQL 查询
     * @param hql
     * @param params
     * @return
     */
    public List find(String hql,Object... params){
        return  this.getHibernateTemplate().find(hql,params);
    }

    /**
     * 对延迟加载的对象初始化
     * @param entity
     */
    public void initialize(Object entity){
        this.getHibernateTemplate().initialize(entity);
    }


    /**
     * 使用 hql, 分页查询
     * @param hql
     * @param pageNo
     * @param pageSize
     * @param values
     * @return
     */
    public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values) {

        Assert.hasText(hql);
        Assert.isTrue(pageNo >=1 ,"pageNo should start from 1");

        // Count查询
        String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
        List countlist = getHibernateTemplate().find(countQueryString, values);
        long totalCount = (Long) countlist.get(0);

        if (totalCount < 1)
            return new Page();
        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        Query query = createQuery(hql, values);
        List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();

        return new Page(startIndex, totalCount, pageSize, list);
    }


    public Session getSession() {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }


    /**
     * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
     * 留意可以连续设置,如下：
     * <pre>
     * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
     * </pre>
     * 调用方式如下：
     * <pre>
     *        dao.createQuery(hql)
     *        dao.createQuery(hql,arg0);
     *        dao.createQuery(hql,arg0,arg1);
     *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
     * </pre>
     *
     * @param values 可变参数.
     */
    public Query createQuery(String hql, Object... values) {
        Assert.hasText(hql);
        Query query = getSession().createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query;
    }
    /**
     * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
     *
     * @see #pagedQuery(String,int,int,Object[])
     */
    private static String removeSelect(String hql) {
        Assert.hasText(hql);
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
        return hql.substring(beginPos);
    }

    /**
     * 去除hql的orderby 子句，用于pagedQuery.
     *
     * @see #pagedQuery(String,int,int,Object[])
     */

    private static String removeOrders(String hql) {
        Assert.hasText(hql);
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }


    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }
}
