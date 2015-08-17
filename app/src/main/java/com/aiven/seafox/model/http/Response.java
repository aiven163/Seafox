package com.aiven.seafox.model.http;

import java.io.Serializable;

/**
 * The HTTP request and response network entities
 *
 * @author Aiven
 * @email aiven163@sina.com
 * @date 2015-6-14  PM 11:08:11
 * @desc
 */
public class Response implements Serializable {

    private static final long serialVersionUID = -631047247417282027L;
    private long requestPipIndex = 0;
    private Object carryExtendObj;
    private Object dataObj;
    private PageInfo pageInfo;
    private boolean isCacheData;//是否为缓存数据

    public long getRequestPipIndex() {
        return requestPipIndex;
    }

    public void setRequestPipIndex(long requestPipIndex) {
        this.requestPipIndex = requestPipIndex;
    }

    public Object getCarryExtendObj() {
        return carryExtendObj;
    }

    public void setCarryExtendObj(Object carryExtendObj) {
        this.carryExtendObj = carryExtendObj;
    }

    public Object getDataObj() {
        return dataObj;
    }

    public void setDataObj(Object dataObj) {
        this.dataObj = dataObj;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public boolean isCacheData() {
        return isCacheData;
    }

    public void setIsCacheData(boolean isCacheData) {
        this.isCacheData = isCacheData;
    }
}
