package com.pigx.engine.core.definition.domain.view.datatables;

import com.google.common.base.MoreObjects;
import java.io.Serializable;

/* loaded from: core-definition-3.5.7.0.jar:cn/herodotus/engine/core/definition/domain/view/datatables/DataTableResult.class */
public class DataTableResult implements Serializable {
    private int pageNumber;
    private int pageSize;
    private String sEcho;
    private int iDisplayStart;
    private int iDisplayLength;
    private String jsonString;
    private long total;

    public DataTableResult(String sEcho, int iDisplayStart, int iDisplayLength, String jsonString) {
        this.sEcho = sEcho;
        this.iDisplayStart = iDisplayStart;
        this.iDisplayLength = iDisplayLength;
        this.pageNumber = this.iDisplayStart / this.iDisplayLength;
        this.pageSize = this.iDisplayLength;
        this.jsonString = jsonString;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getsEcho() {
        return this.sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getiDisplayStart() {
        return this.iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getiDisplayLength() {
        return this.iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public String getJsonString() {
        return this.jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("pageNumber", this.pageNumber).add("pageSize", this.pageSize).add(DataTableUtils.ECHO, this.sEcho).add(DataTableUtils.DISPLAY_START, this.iDisplayStart).add(DataTableUtils.DISPLAY_LENGTH, this.iDisplayLength).add("jsonString", this.jsonString).add("total", this.total).toString();
    }
}
