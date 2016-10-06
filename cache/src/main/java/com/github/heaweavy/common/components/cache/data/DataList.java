package com.github.heaweavy.common.components.cache.data;




import com.github.heaweavy.common.components.cache.DatabaseOperator;
import com.github.heaweavy.common.components.cache.MemoryCache;

import java.util.*;

/**
 * Created by heaweavy on 2016/7/27.
 */
public class DataList {
    private LeaderTable leaderTable;
    private Class entityClass;
    private List<Object> entityList ;
    private long lastUsedTime;
    private Timer timer;
    private DatabaseOperator operator;
    public DataList(DatabaseOperator operator, LeaderTable leaderTable){
        this.leaderTable = leaderTable;
        this.operator = operator;
        entityList = new LinkedList<>();
        timer = new Timer();
        this.lastUsedTime = System.currentTimeMillis();
        startSaveTask();
    }
    public void startSaveTask(){
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(DataList.this.operator != null){
                    //已到缓存更新时间，更新到数据库中并销毁这部分内存
                    if((System.currentTimeMillis() - DataList.this.lastUsedTime ) >= (MemoryCache.getDataCacheTime()*1000)) {
                        //数据在一个更新周期中没有被再次使用，从缓存中移除这个数据表
                        synchronized (DataList.this) {
                            Iterator it = DataList.this.entityList.iterator();
                            while(it.hasNext()){
                                DataList.this.operator.updateDatabase(it.next());
                            }
                            DataList.this.entityList.clear();
                            if(DataList.this.leaderTable != null){
                                leaderTable.removeOneDataList(DataList.this);
                            }
                            DataList.this.timer.cancel();
                            DataList.this.timer = null;
                        }
                        return ;
                    }
                    Iterator it = DataList.this.entityList.iterator();
                    while(it.hasNext()){
                        DataList.this.operator.updateDatabase(it.next());
                    }
                }
            }
        }, MemoryCache.getDataCacheUpdatePeriod()*1000,MemoryCache.getDataCacheUpdatePeriod()*1000);
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public List<Object> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<Object> entityList) {
        this.entityList = entityList;
    }

    public void renewUsedTime() {
        lastUsedTime = System.currentTimeMillis();
    }
    public long getLastUsedTime() {
        long lt = this.lastUsedTime;
        this.lastUsedTime = System.currentTimeMillis();
        return lt;
    }
    public void updateAll(){
        synchronized (this){
            if(this.getEntityList() == null || this.getEntityList().size() == 0){
                return ;
            }
            Iterator it = this.entityList.iterator();
            while (it.hasNext()){
                this.operator.updateDatabase(it.next());
            }
            this.entityList.clear();
        }
    }

    protected void stopTimerTask() {
        this.timer.cancel();
        this.timer = null;
    }
}
