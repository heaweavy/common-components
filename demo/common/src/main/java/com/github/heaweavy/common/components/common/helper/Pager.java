package com.github.heaweavy.common.components.common.helper;

import com.google.common.base.Strings;

/**
 * Created by Rogers on 15-4-21.
 */
public class Pager {
    private static int DEFAULT_PAGE_SIZE = 30;
    public final static int DEFAULT_ID = -1;
    // current page
    private Integer current;
    // page size
    private Integer size;
    // query string
    private String query;
    // page url
    private String url;
    // total items
    private long total;
    // total pages
//    private int totalPages;
    private Integer id;
    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        if(current == null || current <= 0){
            this.current = 1;
            return;
        }
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        if(size == null || size <= 0){
            this.size = DEFAULT_PAGE_SIZE;
            return;
        }
        this.size = size;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalPages() {
        return this.getTotal() % this.getSize() > 0 ? (this.getTotal() / this.getSize() + 1) : (this.getTotal() / this.getSize());
    }

    public long getPrev(){
        return (getCurrent() - 1) > 0 ? (getCurrent()-1) : 1;
    }

    public boolean hasPrev(){
        return (getCurrent() - 1) > 0;
    }

    public long getNext(){
        if(getTotalPages() == 0){
            return 1L;
        }
        return getCurrent() + 1 > getTotalPages() ? getTotalPages() : getCurrent() + 1;
    }

    public boolean hasNext(){
        return getCurrent() < getTotalPages();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrevUrl(){
        if( !Strings.isNullOrEmpty(getQuery())){
            return this.getUrl() +  String.format("?q=%s&p=%d&size=%d", getQuery(), getPrev(), getSize());
        }

        return this.getUrl() +  String.format("?p=%d&size=%d", getPrev(), getSize());
    }

    public String getNextUrl(){
        if( !Strings.isNullOrEmpty(getQuery())){
            return this.getUrl() +  String.format("?q=%s&p=%d&size=%d", getQuery(), getNext(), getSize());
        }
        return this.getUrl() +  String.format("?p=%d&size=%d", getNext(), getSize());
    }
}
