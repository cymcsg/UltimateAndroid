package com.marshalchen.common.commonUtils.dbUtils;

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

    static boolean isLog = true;

    private static void isLog() {
        if (isLog) {
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        } else {
            QueryBuilder.LOG_SQL = false;
            QueryBuilder.LOG_VALUES = false;
        }
    }

    public static List getList(AbstractDao dao, Property orderProperty, WhereCondition cond, WhereCondition... condMore) {
        isLog();
        List indexFavList = dao.queryBuilder()
                .where(cond, condMore)
                .orderAsc(orderProperty)
                .list();
        return indexFavList;
    }

    public static List getList(AbstractDao dao, WhereCondition cond, WhereCondition... condMore) {
        isLog();
        List indexFavList = dao.queryBuilder()
                .where(cond, condMore)
                .list();
        return indexFavList;
    }

    public static List getList(AbstractDao dao, Property... orderProperty) {
        isLog();
        List indexFavList = dao.queryBuilder()
                .orderAsc(orderProperty)
                .list();
        return indexFavList;
    }

    public static List getList(AbstractDao dao) {
        isLog();
        List indexFavList = dao.queryBuilder()
                .list();
        return indexFavList;
    }

    public static List queryBuilderList(AbstractDao dao, WhereCondition cond, WhereCondition... condmore) {
        return getQueryBuilder(dao, cond, condmore).list();
    }

    public static LazyList queryBuilderLazyList(AbstractDao dao, WhereCondition cond, WhereCondition... condmore) {
        return getQueryBuilder(dao, cond, condmore).listLazy();
    }

    public static Query query(AbstractDao dao, String queryString, String queryValue) {
        //queryString=", GROUP G WHERE G.NAME=? AND T.GROUP_ID=G._ID"
        //queryValue="admin"
        Query query = dao.queryRawCreate(
                queryString, queryValue);
        return query;
    }

    public static Query query(AbstractDao dao, String queryString) {
        //queryString="_ID IN " + "(SELECT USER_ID FROM USER_MESSAGE WHERE READ_FLAG = 0)"

        Query query = dao.queryBuilder().where(
                new WhereCondition.StringCondition(queryString)
        ).build();
        return query;
    }

    public static void insert(AbstractDao dao, Object entity) {
        dao.insert(entity);
    }

    public static void insert(AbstractDao dao, List entities) {
        dao.insertInTx(entities);
    }

    public static void delete(AbstractDao dao, Object entity) {
        dao.delete(entity);
    }

    public static void delete(AbstractDao dao, List entities) {
        dao.deleteInTx(entities);

    }

    public static QueryBuilder getQueryBuilder(AbstractDao dao, WhereCondition cond, WhereCondition... condmore) {
        isLog();
        QueryBuilder qb = dao.queryBuilder();
        qb.where(cond, condmore);
        return qb;
    }

    public static void deleteByCondition(AbstractDao dao, WhereCondition cond, WhereCondition... condmore) {
        isLog();
        QueryBuilder qb = dao.queryBuilder();
        qb.where(cond, condmore).buildDelete().executeDeleteWithoutDetachingEntities();

    }

}
