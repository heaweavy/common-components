package com.github.heaweavy.common.components.mysql_test.mapper;

import com.github.heaweavy.common.components.mysql_test.entity.Children;

import java.util.List;
import java.util.Map;

/**
 * Created by Rogers on 15-3-20.
 */

public interface ChildrenMapper {

    public int createChildren(Children children);

    public void updateChildren(Children children);

    public void deleteChildren(int id);

    public Children getChildrenById(int id);

    public Children getChildrenByMobile(String mobile);

    public Children getChildrenByAccount(String account);

    public List<Children> queryChildren(Map<String, Object> param);

    public Integer countChildren(Map<String, Object> param);
}
