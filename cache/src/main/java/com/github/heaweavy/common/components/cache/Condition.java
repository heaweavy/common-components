package com.github.heaweavy.common.components.cache;

import java.util.HashMap;

/**
 * Created by heaweavy on 2016/7/27.
 */
public class Condition extends HashMap{
    private Class entityClass;

    public Class getEntityClass() {
        return entityClass;
    }

    public Condition setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
        return this;
    }

}
