package com.github.heaweavy.common.components.cache;

import java.util.List;

/**
 * Created by heaweavy on 2016/7/28.
 * T 可以使用实体entity也可以使用List<entity> 不可以使用其他类型
 */
public interface DatabaseOperater<T,E> {
    //查询不到数据请返回null,可以减少内部操作，
    //如果缓存中的数据不足，在此函数中自行调用数据库查询补充数据
    public T queryCache(final List<E> dataList);
    //查询不到数据请返回null,可以减少内部操作
    public T queryDatabase();
    public int updateCache(T t);
    public int updateDatabase(final E entity);
}
