package com.github.heaweavy.common.components.webserver1.service.other;

import com.github.heaweavy.common.components.webserver1.service.query.QueryResult;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by Rogers on 15-4-17.
 */
@Service
public class OtherService {

//    private ElasticsearchOperations es;
    private Gson gson = new Gson();
//
//    @Autowired
//    public OtherService(ElasticsearchOperations es) {
//        this.es = es;
//    }

    public <T> QueryResult<T> queryByMsgId(int msgId, int page, int size, Class<T> tClass){
        Preconditions.checkArgument(msgId > 0);
        Preconditions.checkArgument(page == -1 || page >= 1, "page >= 1 required");
        Preconditions.checkArgument(size == -1 || size > 0);
        List<T> resultList = new LinkedList<>();
        Page<T> resultPage = new PageImpl(resultList);
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
