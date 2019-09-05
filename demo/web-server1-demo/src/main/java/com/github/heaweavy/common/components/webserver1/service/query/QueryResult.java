package com.github.heaweavy.common.components.webserver1.service.query;

import java.util.List;

/**
 * Created by Rogers on 15-5-28.
 */
public class QueryResult<T> {
    private long totalRecord;
    private int nextPage;
    private List<T> data;

    public QueryResult(long total, int nextPage, List<T> data) {
        this.totalRecord = total;
        this.nextPage = nextPage;
        this.data = data;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

