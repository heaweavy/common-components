package com.github.heaweavy.common.components.webserver1.service.query;

/**
 * Created by Rogers on 15-4-14.
 */
public class QueryException extends Exception {
    public QueryException(){
        super();
    }

    public QueryException(String message){
        super(message);
    }

    public QueryException(Throwable t){
        super(t);
    }

    public QueryException(String message, Throwable cause){
        super(message, cause);
    }
}
