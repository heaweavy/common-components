package com.github.heaweavy.common.components.cache;


import com.github.heaweavy.common.components.cache.data.LeaderTable;

/**
 * Created by heaweavy on 2016/7/27.
 */
public class MemoryCache {
    //静态内部类加载单例
    private static class Singleton{
        private static final MemoryCache INSTANCE =
                MemoryCache.configure == null ? new MemoryCache() : new MemoryCache(MemoryCache.configure);
    }
    public static Configure configure = null;
    //数据表
    private LeaderTable leaderTable;
    //默认数据表最大长度
    private static int maxTableSize = 20;
    //默认数据链最大长度
    private static int maxDataListSize = 100;
    //默认数据缓存时间
    private static int dataCacheTime = 3600;
    //默认数据更新周期
    private static int dataCacheUpdatePeriod = 10;

    private MemoryCache(){
        leaderTable = new LeaderTable();
    }
    private MemoryCache(Configure configure){
        this.leaderTable = new LeaderTable();
        MemoryCache.maxTableSize = configure.getMaxTableSize();
        MemoryCache.maxDataListSize = configure.getMaxDataListSize();
        MemoryCache.dataCacheTime = configure.getDataCacheTime();
        MemoryCache.dataCacheUpdatePeriod = configure.getDataCacheUpdatePeriod();
    }
    public Object query(DatabaseOperator operater, Class entityClass) throws NoSuchMethodException {
        return leaderTable.query(operater,entityClass);
    }
    public int update(DatabaseOperator operater, Class entityClass) throws NoSuchMethodException {
        return leaderTable.update(operater,entityClass);
    }
    public static int getMaxTableSize() {
        return maxTableSize;
    }

    public static void setMaxTableSize(int maxTableSize) {
        MemoryCache.maxTableSize = maxTableSize;
    }

    public static int getMaxDataListSize() {
        return maxDataListSize;
    }

    public static void setMaxDataListSize(int maxDataListSize) {
        MemoryCache.maxDataListSize = maxDataListSize;
    }

    public static MemoryCache instance(){
        return Singleton.INSTANCE;
    }

    public static int getDataCacheTime() {
        return dataCacheTime;
    }

    public static void setDataCacheTime(int dataCacheTime) {
        MemoryCache.dataCacheTime = dataCacheTime;
    }


    public static int getDataCacheUpdatePeriod() {
        return dataCacheUpdatePeriod;
    }

    public static void setDataCacheUpdatePeriod(int dataCacheUpdatePeriod) {
        MemoryCache.dataCacheUpdatePeriod = dataCacheUpdatePeriod;
    }

}
