package com.aiven.seafox.model.http;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/7/23  16:34
 * Desc : 翻页信息
 */
public class PageInfo {
    /**
     * 总页数
     */
    public int totallPages;
    /**
     * 没页条数
     */
    public int pageItemSize;
    /**
     * 当前请求索引
     */
    public int currentPageIndex;
}
