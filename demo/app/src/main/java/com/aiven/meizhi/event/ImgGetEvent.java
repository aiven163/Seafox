package com.aiven.meizhi.event;

import android.graphics.pdf.PdfDocument;

import com.aiven.meizhi.gloable.Constants;
import com.aiven.meizhi.model.ImgBase;
import com.aiven.meizhi.util.HtmlGetImgUtil;
import com.aiven.seafox.controller.event.HttpEvent;
import com.aiven.seafox.controller.log.Logs;
import com.aiven.seafox.model.controller.EventException;
import com.aiven.seafox.model.controller.EventParam;
import com.aiven.seafox.model.http.PageInfo;
import com.aiven.seafox.model.http.Request;
import com.aiven.seafox.model.http.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/7/22  17:31
 * Desc :
 */
public class ImgGetEvent extends HttpEvent {

    private int cid;
    private int pageIndex;

    public ImgGetEvent(int id, String PanelName, int cid, int pageIndex) {
        super(id, PanelName);
        this.cid = cid;
        this.pageIndex = pageIndex;
    }

    /**
     * 设置请求参数
     * @param arrayList
     * @return
     */
    @Override
    public Request paramRequest(ArrayList<EventParam> arrayList) {
        Request request = new Request();
        request.setUrl(Constants.HTTP_BASE + cid + Constants.PAGE_ADRR + pageIndex);
        Logs.logPrint("HTTP", request.getUrl());
        return request;
    }

    /**
     * 数据解析
     * @param s
     * @return
     * @throws EventException
     */
    @Override
    public Response analysisData(String s) throws EventException {
        Logs.logPrint("HTTP", s);
        Response response = new Response();
        List<ImgBase> imgListBase = HtmlGetImgUtil.htmlGetImgs(s);
        response.setDataObj(imgListBase);
        PageInfo page = new PageInfo();
        page.currentPageIndex = pageIndex;
        response.setPageInfo(page);
        return response;
    }

    /**
     * 下一个任务执行参数
     * @param arrayList
     * @return
     */
    @Override
    public ArrayList<EventParam> paramNextEventParam(ArrayList<EventParam> arrayList) {
        return null;
    }

}
