package com.github.heaweavy.common.components.webserver1.service.query;

import com.github.heaweavy.common.components.webserver1.schema.Notice;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rogers on 15-4-16.
 */
@Service
public class QueryService {

/*    @Autowired
    public QueryService(ElasticsearchTemplate es) {
        this.es = es;
    }*/

    /**
     * 分页搜索
     * @param guardianId
     * @param page
     * @param size
     * @return
     */
    public Page<Notice> findNoticeByGuardianAndStudent(int guardianId, int studentId, int page, int size) throws QueryException{
        List<Notice> notices = new LinkedList<>();
        Page<Notice> result = new PageImpl(notices);
        return result;
    }

}
