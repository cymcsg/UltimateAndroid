package com.marshalchen.ua.common.commonUtils.dbUtils;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.query.LazyList;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

import java.util.List;

/**
 * Userful Dao Utils when using GreenDao
 */
public class GreenDaoUtils {

    private static boolean isLog = true;

    /**
     * Set if the greendao will log the resulting SQL command and the passed values when calling one of the build methods.
     * Like this you can compare if you actually get what you expected. It might also help to copy generated SQL into some SQLite database explorer and execute it with the values.
     * @param isLog
     */
    public static void setIsLog(boolean isLog) {
        GreenDaoUtils.isLog = isLog;
    }

    private static void setIfLog() {
        if (isLog) {
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        } else {
            QueryBuilder.LOG_SQL = false;
            QueryBuilder.LOG_VALUES = false;
        }
    }

    /**
     * Executes the query and returns the result as a list containing all entities loaded into memory.
     * @param dao
     * @param orderProperty
     * @param whereCondition
     * @param whereConditions
     * @return
     */
    public static List getList(AbstractDao dao, Property orderProperty, WhereCondition whereCondition, WhereCondition... whereConditions) {
        setIfLog();
        List indexFavList = dao.queryBuilder()
                .where(whereCondition, whereConditions)
                .orderAsc(orderProperty)
                .list();
        return indexFavList;
    }

    /**
     * @see #getList(de.greenrobot.dao.AbstractDao, de.greenrobot.dao.Property, de.greenrobot.dao.query.WhereCondition, de.greenrobot.dao.query.WhereCondition...)
     * @param dao
     * @param whereCondition
     * @param whereConditions
     * @return
     */
    public static List getList(AbstractDao dao, WhereCondition whereCondition, WhereCondition... whereConditions) {
        setIfLog();
        List indexFavList = dao.queryBuilder()
                .where(whereCondition, whereConditions)
                .list();
        return indexFavList;
    }

    /**
     * @see #getList(de.greenrobot.dao.AbstractDao, de.greenrobot.dao.Property, de.greenrobot.dao.query.WhereCondition, de.greenrobot.dao.query.WhereCondition...)
     * @param dao
     * @param orderProperty
     * @return
     */
    public static List getList(AbstractDao dao, Property... orderProperty) {
        return getList(dao,true,orderProperty);
    }

    /**
     * Executes the query and returns the result as a list containing all entities loaded into memory.
     * @param dao
     * @param isAsc
     * @param orderProperty
     * @return
     */
    public static List getList(AbstractDao dao, boolean isAsc, Property... orderProperty) {
        setIfLog();
        QueryBuilder queryBuilder = dao.queryBuilder();
        if (isAsc) {
            queryBuilder = queryBuilder.orderAsc(orderProperty);
        } else {
            queryBuilder = queryBuilder.orderDesc(orderProperty);
        }
        List indexFavList = queryBuilder.list();
        return indexFavList;
    }

    /**
     * @see #getList(de.greenrobot.dao.AbstractDao, de.greenrobot.dao.Property, de.greenrobot.dao.query.WhereCondition, de.greenrobot.dao.query.WhereCondition...)
     * @param dao
     * @return
     */
    public static List getList(AbstractDao dao) {
        setIfLog();
        List indexFavList = dao.queryBuilder()
                .list();
        return indexFavList;
    }


    /**
     * @see  #getList(de.greenrobot.dao.AbstractDao, de.greenrobot.dao.Property, de.greenrobot.dao.query.WhereCondition, de.greenrobot.dao.query.WhereCondition...)
     * @param dao
     * @param cond
     * @param condmore
     * @return
     */
    public static List queryBuilderList(AbstractDao dao, WhereCondition cond, WhereCondition... condmore) {
        return getQueryBuilder(dao, cond, condmore).list();
    }

    /**
     * Executes the query and returns the result as a list that lazy loads the entities on first access. Entities are cached, so accessing the same entity more than once will not result in loading an entity from the underlying cursor again.Make sure to close it to close the underlying cursor.
     * @param dao
     * @param cond
     * @param condmore
     * @return
     */
    public static LazyList queryBuilderLazyList(AbstractDao dao, WhereCondition cond, WhereCondition... condmore) {
        return getQueryBuilder(dao, cond, condmore).listLazy();
    }

    /**
     * Creates a repeatable Query object based on the given raw SQL where you can pass any WHERE clause and arguments.
     * @param dao
     * @param queryString
     * @param queryValue
     * @return
     */
    public static Query query(AbstractDao dao, String queryString, String queryValue) {
        //queryString=", GROUP G WHERE G.NAME=? AND T.GROUP_ID=G._ID"
        //queryValue="admin"
        Query query = dao.queryRawCreate(
                queryString, queryValue);
        return query;
    }

    /**
     * Creates a repeatable Query object based on the given raw SQL where you can pass any WHERE clause and arguments.
     * @param dao
     * @param queryString
     * @return
     */
    public static Query query(AbstractDao dao, String queryString) {
        //queryString="_ID IN " + "(SELECT USER_ID FROM USER_MESSAGE WHERE READ_FLAG = 0)"

        Query query = dao.queryBuilder().where(
                new WhereCondition.StringCondition(queryString)
        ).build();
        return query;
    }

    /**
     * Insert an entity into the table associated with a concrete DAO.
     * @param dao
     * @param entity
     */
    public static void insert(AbstractDao dao, Object entity) {
        dao.insert(entity);
    }

    /**
     * Insert an entity into the table associated with a concrete DAO.
     * @param dao
     * @param entities
     */
    public static void insert(AbstractDao dao, List entities) {
        dao.insertInTx(entities);
    }

    public static void insertOrReplace(AbstractDao dao, Object entity) {
        dao.insertOrReplace(entity);
    }

    public static void insertOrReplace(AbstractDao dao, List entities) {
        dao.insertOrReplace(entities);
    }

    /**
     * Deletes the given entity from the database. Currently, only single value PK entities are supported.
     * @param dao
     * @param entity
     */
    public static void delete(AbstractDao dao, Object entity) {
        dao.delete(entity);
    }

    /**
     * Deletes the given entities in the database using a transaction.
     * @param dao
     * @param entities
     */
    public static void delete(AbstractDao dao, List entities) {
        dao.deleteInTx(entities);

    }

    public static QueryBuilder getQueryBuilder(AbstractDao dao, WhereCondition cond, WhereCondition... condmore) {
        setIfLog();
        QueryBuilder qb = dao.queryBuilder();
        qb.where(cond, condmore);
        return qb;
    }

    /**
     * Deletes all matching entities without detaching them from the identity scope (aka session/cache). Note that this
     * method may lead to stale entity objects in the session cache. Stale entities may be returned when loaded by their
     * primary key, but not using queries.
     * @param dao
     * @param cond
     * @param condmore
     */
    public static void deleteByCondition(AbstractDao dao, WhereCondition cond, WhereCondition... condmore) {
        setIfLog();
        QueryBuilder qb = dao.queryBuilder();
        qb.where(cond, condmore).buildDelete().executeDeleteWithoutDetachingEntities();

    }

}
