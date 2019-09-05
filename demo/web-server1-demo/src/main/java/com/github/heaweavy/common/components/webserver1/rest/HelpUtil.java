package com.github.heaweavy.common.components.webserver1.rest;

import com.github.heaweavy.common.components.webserver1.service.query.QueryResult;
import org.springframework.data.domain.Page;

/**
 * Created by heaweavy on 2016/9/8.
 */
public class HelpUtil {
    public static  <T> QueryResult<T> getQueryResult(Page<T> resultPage, Integer page, Integer size){
        QueryResult<T> result = null;
        if(page == -1 && size == -1){
            result = new QueryResult<>(resultPage.getTotalElements(),1,resultPage.getContent());
        }
        else {
            result = new QueryResult<>(resultPage.getTotalElements(), resultPage.getTotalPages() - page > 0 ? page + 1 : page, resultPage.getContent());
        }
        return result;
    }
}
