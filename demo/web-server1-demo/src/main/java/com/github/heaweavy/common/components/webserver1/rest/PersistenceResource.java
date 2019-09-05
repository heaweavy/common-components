package com.github.heaweavy.common.components.webserver1.rest;

import com.github.heaweavy.common.components.webserver1.schema.Notice;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Rogers on 15-8-19.
 */
@RestController
@RequestMapping("/persist")
public class PersistenceResource {
    private static final Logger logger = LoggerFactory.getLogger(PersistenceResource.class);

    @Autowired
    private Gson gson = new Gson();

    @RequestMapping(value = "/notice", method = RequestMethod.POST,
            consumes = {"application/json"},
            produces= {"application/json"})
    public ResponseEntity<String> saveNotice(@RequestBody @Valid Notice notice) throws Exception{
        String documentId = "demo_result_id";
        return new ResponseEntity<String>(documentId, HttpStatus.OK);
    }

}
