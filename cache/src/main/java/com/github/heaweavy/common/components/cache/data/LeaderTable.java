package com.github.heaweavy.common.components.cache.data;


import com.github.heaweavy.common.components.cache.DatabaseOperator;
import com.github.heaweavy.common.components.cache.MemoryCache;
import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by heaweavy on 2016/7/27.
 * TODO 需要强化缓存保护，需要增加短时性已变数据更新到数据中的定时任务
 * TODO 当前只做了1小时一次的数据检查，并更新到数据库中，如果数据未被使用则更新并从缓存中移除
 */
public class LeaderTable {
    //数据链
    private List<DataList> dataList;
    public LeaderTable(){
        dataList = new LinkedList<>();
    }
    private Gson gson = new Gson();
    //TODO 需要升级淘汰算法
    //查询操作交由使用者自定义
    public Object query(DatabaseOperator operater, Class entityClass) throws NoSuchMethodException {
        DataList dataListTemp = null;
        //判断缓存中是否有这张表

//        showDebug(entityClass , "判断缓存中是否有这张表");
        for(DataList datals : this.dataList){
            if(entityClass == datals.getEntityClass()){
                dataListTemp = datals;
                break;
            }
        }
        if(dataListTemp == null){
            //缓存中不存在这类数据；需要在缓存中建表
//            showDebug(entityClass ,"缓存中不存在这类数据；需要在缓存中建表");
            synchronized (this.dataList) {
//                showDebug(entityClass ,"锁表开始在缓存中建表");
                Method methods[] = operater.getClass().getMethods();
                Method methods2[] = new Method[2];
                Method queryCacheMd = getMethod(operater,"queryCache");
                Class returnType = queryCacheMd.getReturnType();
                //判断是否有其他线程已经插入此表到缓存中
//                showDebug(entityClass ,"判断是否有其他线程已经插入此表到缓存中");
                for (DataList datals : this.dataList) {
                    if (entityClass == datals.getEntityClass()) {
//                        showDebug(entityClass ,"已经有其他线程已经插入此表到缓存中");
                        return unrelevance(query( operater ,entityClass),returnType,entityClass);
                    }
                }
                DataList dataListNew = new DataList(operater,this);
                dataListNew.setEntityClass(entityClass);
                Object obj = operater.queryDatabase();
                if(obj == null){
                    return null;
                }
                if (returnType.getSimpleName().equals("List")) {
                    List objects = (List<Object>) obj;
                    Iterator it = objects.iterator();
                    while (it.hasNext()){
                        dataListNew.getEntityList().add(0,it.next());
                    }
                }else{
                    dataListNew.getEntityList().add(0,obj);
                }
                this.dataList.add(dataListNew);
                return unrelevance(obj,returnType,entityClass);
            }
        }
        else{
//            showDebug(entityClass ,"缓存中存在这类数据；直接查缓存");
            dataListTemp.renewUsedTime();
            Object obj = operater.queryCache(dataListTemp.getEntityList());
            Method queryCacheMd = getMethod(operater,"queryCache");
            Class returnType = queryCacheMd.getReturnType();
            //缓存中有数据，将数据顶置
//            showDebug(entityClass ,"缓存中有数据，将数据顶置");
            if(obj != null) {
                synchronized (dataListTemp) {
                    promote(returnType,obj,dataListTemp.getEntityList());
                    return unrelevance(obj,returnType,entityClass);
                }
            }
            //缓存中没有此数据，查数据库
//            showDebug(entityClass ,"缓存中没有此数据，查数据库");
            synchronized (dataListTemp){
                //重新查询缓存防止其它线程已经将这些数据插入到缓存表中
//                showDebug(entityClass ,"重新查询缓存防止其它线程已经将这些数据插入到缓存表中");
                obj = operater.queryCache(dataListTemp.getEntityList());
                if(obj != null){
                    //有数据表明有其他线程调用数据库查询并将结果插入缓存中，
                    //插入新数据
//                    showDebug(entityClass ,"有数据表明有其他线程调用数据库查询并将结果插入缓存中");
                    promote(returnType,obj,dataListTemp.getEntityList());
                    return unrelevance(obj,returnType,entityClass);
                }
                //查询数据库
//                showDebug(entityClass ,"缓存中没有此数据，查数据库");
                obj = operater.queryDatabase();
                if(obj == null) {
                    return null;
                }
                synchronized (dataListTemp) {
                    if (returnType.getSimpleName().equals("List")) {
                        List<Object> objects = (List<Object>) obj;
                        Iterator it = objects.iterator();
                        while (it.hasNext()) {
                            dataListTemp.getEntityList().add(0, it.next());
                        }
                        while (dataListTemp.getEntityList().size() > MemoryCache.getMaxDataListSize()){
                            operater.updateDatabase(dataListTemp.getEntityList().get(dataListTemp.getEntityList().size() - 1));
                            dataListTemp.getEntityList().remove(dataListTemp.getEntityList().size() - 1);
                        }
                    }else{
                        dataListTemp.getEntityList().add(0, obj);
                        if(dataListTemp.getEntityList().size() >= MemoryCache.getMaxDataListSize()){
                            operater.updateDatabase(dataListTemp.getEntityList().get(dataListTemp.getEntityList().size() - 1));
                            dataListTemp.getEntityList().remove(dataListTemp.getEntityList().size() - 1 );
                        }
                    }
                    return unrelevance(obj,returnType,entityClass);
                }
            }
        }
    }
    private Object queryWithoutUnrelevance(DatabaseOperator operater, Class entityClass) throws NoSuchMethodException {
        DataList dataListTemp = null;
        //判断缓存中是否有这张表

//        showDebug(entityClass , "判断缓存中是否有这张表");
        for(DataList datals : this.dataList){
            if(entityClass == datals.getEntityClass()){
                dataListTemp = datals;
                break;
            }
        }
        if(dataListTemp == null){
            //缓存中不存在这类数据；需要在缓存中建表
//            showDebug(entityClass ,"缓存中不存在这类数据；需要在缓存中建表");
            synchronized (this.dataList) {
//                showDebug(entityClass ,"锁表开始在缓存中建表");
                Method methods[] = operater.getClass().getMethods();
                Method methods2[] = new Method[2];
                Method queryCacheMd = getMethod(operater,"queryCache");
                Class returnType = queryCacheMd.getReturnType();
                //判断是否有其他线程已经插入此表到缓存中
//                showDebug(entityClass ,"判断是否有其他线程已经插入此表到缓存中");
                for (DataList datals : this.dataList) {
                    if (entityClass == datals.getEntityClass()) {
//                        showDebug(entityClass ,"已经有其他线程已经插入此表到缓存中");
                        return query( operater ,entityClass);
                    }
                }
                DataList dataListNew = new DataList(operater,this);
                dataListNew.setEntityClass(entityClass);
                Object obj = operater.queryDatabase();
                if(obj == null){
                    return null;
                }
                if (returnType.getSimpleName().equals("List")) {
                    List objects = (List<Object>) obj;
                    Iterator it = objects.iterator();
                    while (it.hasNext()){
                        dataListNew.getEntityList().add(0,it.next());
                    }
                }else{
                    dataListNew.getEntityList().add(0,obj);
                }
                this.dataList.add(dataListNew);
                return obj;
            }
        }
        else{
//            showDebug(entityClass ,"缓存中存在这类数据；直接查缓存");
            dataListTemp.renewUsedTime();
            Object obj = operater.queryCache(dataListTemp.getEntityList());
            Method queryCacheMd = getMethod(operater,"queryCache");
            Class returnType = queryCacheMd.getReturnType();
            //缓存中有数据，将数据顶置
//            showDebug(entityClass ,"缓存中有数据，将数据顶置");
            if(obj != null) {
                synchronized (dataListTemp) {
                    promote(returnType,obj,dataListTemp.getEntityList());
                    return obj;
                }
            }
            //缓存中没有此数据，查数据库
//            showDebug(entityClass ,"缓存中没有此数据，查数据库");
            synchronized (dataListTemp){
                //重新查询缓存防止其它线程已经将这些数据插入到缓存表中
//                showDebug(entityClass ,"重新查询缓存防止其它线程已经将这些数据插入到缓存表中");
                obj = operater.queryCache(dataListTemp.getEntityList());
                if(obj != null){
                    //有数据表明有其他线程调用数据库查询并将结果插入缓存中，
                    //插入新数据
//                    showDebug(entityClass ,"有数据表明有其他线程调用数据库查询并将结果插入缓存中");
                    promote(returnType,obj,dataListTemp.getEntityList());
                    return obj;
                }
                //查询数据库
//                showDebug(entityClass ,"缓存中没有此数据，查数据库");
                obj = operater.queryDatabase();
                if(obj == null) {
                    return null;
                }
                synchronized (dataListTemp) {
                    if (returnType.getSimpleName().equals("List")) {
                        List<Object> objects = (List<Object>) obj;
                        Iterator it = objects.iterator();
                        while (it.hasNext()) {
                            dataListTemp.getEntityList().add(0, it.next());
                        }
                        while (dataListTemp.getEntityList().size() > MemoryCache.getMaxDataListSize()){
                            operater.updateDatabase(dataListTemp.getEntityList().get(dataListTemp.getEntityList().size() - 1));
                            dataListTemp.getEntityList().remove(dataListTemp.getEntityList().size() - 1);
                        }
                    }else{
                        dataListTemp.getEntityList().add(0, obj);
                        if(dataListTemp.getEntityList().size() >= MemoryCache.getMaxDataListSize()){
                            operater.updateDatabase(dataListTemp.getEntityList().get(dataListTemp.getEntityList().size() - 1));
                            dataListTemp.getEntityList().remove(dataListTemp.getEntityList().size() - 1 );
                        }
                    }
                    return obj;
                }
            }
        }
    }

    /**
     * 更新数据
     * @param operater
     * @param entityClass
     * @return
     * @throws NoSuchMethodException
     */
    public int update(DatabaseOperator operater, Class entityClass) throws NoSuchMethodException {
//        System.out.println("更新数据");
        synchronized (this.dataList) {
            Object obj = this.queryWithoutUnrelevance(operater, entityClass);
            if(obj != null) {
                synchronized (obj) {
                    return operater.updateCache(obj);
                }
            }
            return -1;
        }
    }
    public void promote(Class returnType,Object valueObject,List list ){
        if (returnType.getSimpleName().equals("List")) {
            List<Object> objects = (List<Object>) valueObject;
            Iterator it = objects.iterator();
            while (it.hasNext()) {
                Object ob = it.next();
                //从数据库中查出的新数据不会remove现有数据，继而保存到缓存中
                list.remove(ob);
                list.add(0, ob);
            }
        }
        else{
            list.remove(valueObject);
            list.add(0, valueObject);
        }
    }
    private Method getMethod(DatabaseOperator operater, String methodName){
        Method methods[] = operater.getClass().getMethods();
        Method methods2[] = new Method[2];
        Method queryCacheMd = null;
        int len = 0;
        for(int i = 0; i < methods.length; ++i){
            if(methods[i].getName().equals(methodName)){
                System.out.println("Class => " + methods[i].getClass());
                methods2[len++] = methods[i];
            }
        }
        queryCacheMd = methods2[0];
        for(int i = 0; i < len; ++i){
            if(!methods2[i].getReturnType().getSimpleName().equals("Object")){
                queryCacheMd = methods2[i];
            }
        }
        return queryCacheMd;
    }
    public void removeOneDataList(DataList dataList){
        if(dataList == null){
            return ;
        }
        synchronized (this){
            if(this.dataList.contains(dataList)){
                this.dataList.remove(dataList);
            }
        }
    }

    /**
     * 去关联，将缓存数据与返回给用户操作的数据分开，保证缓存与逻辑操作隔离
     * @param object
     * @param entityClass
     * @return
     */
    public Object unrelevance(Object object,Class entityClass){
        Gson gson = new Gson();
        String jstr = gson.toJson(object);
        return gson.fromJson(jstr,entityClass);
    }

    /**
     * 去关联，将缓存数据与返回给用户操作的数据分开，保证缓存与逻辑操作隔离
     * @param object
     * @param returnType
     * @param entityClass
     * @return
     */
    public Object unrelevance(Object object,Class returnType,Class entityClass){
        if(object == null)
            return null;
        if(returnType.getSimpleName().equals("List")){
            Gson gson = new Gson();
            List<Object> objs = (List<Object>) object;
            List<Object> returnObj = new LinkedList<>();
            Iterator it = objs.iterator();
            while (it.hasNext()){
                Object obj = it.next();
                String jstr = gson.toJson(obj);
                returnObj.add(gson.fromJson(jstr,entityClass));
            }
            return returnObj;
        }
        return unrelevance(object,entityClass);
    }


    private void showDebug(Class entityClass,String message){
        System.out.println(entityClass.getSimpleName() + " ##########=====>  " + message);
    }
}
