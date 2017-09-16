package com.github.heaweavy.common.components.webserver2.exception;

import com.github.heaweavy.common.components.webserver2.helper.ResponseMessage;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Rogers on 15/10/23.
 */
@Provider
public class NotFoundExpMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception){
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ResponseMessage(404, exception.getMessage()))
                .build();
    }
}
