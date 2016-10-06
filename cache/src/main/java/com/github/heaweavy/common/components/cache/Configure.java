package com.github.heaweavy.common.components.cache;

/**
 * Created by heaweavy on 2016/8/15.
 */
public class Configure {
    private int maxTableSize = 20;
    private int maxDataListSize = 100;
    private int dataCacheTime = 3600;
    private int dataCacheUpdatePeriod = 10;
    public Configure(int maxTableSize ,int maxDataListSize,int dataCacheTime,int dataCacheUpdatePeriod){
        this.maxTableSize = maxTableSize;
        this.maxDataListSize = maxDataListSize;
        this.dataCacheTime = dataCacheTime;
        this.dataCacheUpdatePeriod = dataCacheUpdatePeriod;
    }
    public int getMaxTableSize() {
        return maxTableSize;
    }

    public void setMaxTableSize(int maxTableSize) {
        this.maxTableSize = maxTableSize;
    }

    public int getMaxDataListSize() {
        return maxDataListSize;
    }

    public void setMaxDataListSize(int maxDataListSize) {
        this.maxDataListSize = maxDataListSize;
    }

    public int getDataCacheTime() {
        return dataCacheTime;
    }

    public void setDataCacheTime(int dataCacheTime) {
        this.dataCacheTime = dataCacheTime;
    }

    public int getDataCacheUpdatePeriod() {
        return dataCacheUpdatePeriod;
    }

    public void setDataCacheUpdatePeriod(int dataCacheUpdatePeriod) {
        this.dataCacheUpdatePeriod = dataCacheUpdatePeriod;
    }

}
