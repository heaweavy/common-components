package com.github.heaweavy.common.components.webserver1.rest;

import com.github.heaweavy.common.components.webserver1.schema.Notice;
import com.github.heaweavy.common.components.webserver1.service.query.QueryService;
import com.github.heaweavy.common.components.webserver1.service.query.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Rogers on 15-8-19.
 */
@RestController
@RequestMapping("/query")
public class QueryResource {
    @Autowired
    private QueryService guardianService;

    @RequestMapping(value = "/guardian/{guardianId}/student/{studentId}/notice",
            method = RequestMethod.GET,
            produces= {"application/json"})
    public ResponseEntity<QueryResult> queryNoticeByGuardian(@PathVariable(value = "guardianId") Integer guardianId,
                                                             @PathVariable(value = "studentId") Integer studentId,
                                                             @RequestParam(value = "p", required = false, defaultValue = "1") Integer page,
                                                             @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) throws Exception{
        Page<Notice> notices = guardianService.findNoticeByGuardianAndStudent(guardianId, studentId, page, size);
        QueryResult<Notice> result = new QueryResult<>(notices.getTotalElements(), notices.getTotalPages() - page > 0 ? page+1 : page, notices.getContent());
        return new ResponseEntity<QueryResult>(result, HttpStatus.OK);
    }

}
