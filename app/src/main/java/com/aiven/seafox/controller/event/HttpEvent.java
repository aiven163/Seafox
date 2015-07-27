package com.aiven.seafox.controller.event;

import com.aiven.seafox.controller.event.base.EventBase;
import com.aiven.seafox.controller.http.QueueManager;
import com.aiven.seafox.controller.http.volley.AuthFailureError;
import com.aiven.seafox.controller.http.volley.VolleyError;
import com.aiven.seafox.controller.http.volley.toolbox.StringRequest;
import com.aiven.seafox.controller.log.Logs;
import com.aiven.seafox.model.controller.EventException;
import com.aiven.seafox.model.controller.EventParam;
import com.aiven.seafox.model.http.Request;
import com.aiven.seafox.model.http.Response;

import java.util.ArrayList;
import java.util.Map;


/**
 * Http 网络任务
 *
 * @项目名称 你我金融 APP客户端(Android)
 * @类名称 HttpEvent
 * @创建人 Aiven
 * @Email aiven163@sina.com
 * @创建时间 2015-6-24 下午4:13:29
 * @类描述 TODO
 */
public abstract class HttpEvent extends EventBase implements com.aiven.seafox.controller.http.volley.Response.Listener<String>,
        com.aiven.seafox.controller.http.volley.Response.ErrorListener {

    private static final long serialVersionUID = -20693265516115800L;
    private Request mRequest;

    public HttpEvent(int id, String PanelName) {
        super(id, PanelName);
    }

    @Override
    final public void syncEventExcute(ArrayList<EventParam> paramList) {
        Request request = paramRequest(paramList);
        sendHttpRequest(request);
    }

    private void sendHttpRequest(Request request) {
        if (request == null)
            return;
        this.mRequest = request;
        StringRequest httpRequest = new StringRequest(mRequest.getUrl(), this, this) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (mRequest.getHeaders() != null)
                    return mRequest.getHeaders();
                return super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (mRequest.getParams() != null)
                    return mRequest.getParams();
                return super.getParams();
            }
        };
        httpRequest.setTag(mRequest.getRequestTag());
        QueueManager.getInstance().getHttpQueue().add(httpRequest);
    }

    @Override
    final public void onErrorResponse(VolleyError error) {
        String errorInfo = "";
        int errorCode = 0;
        if (error != null) {
            if (error.networkResponse != null && error.networkResponse.data != null) {
                errorCode = error.networkResponse.statusCode;
                try {
                    errorInfo = new String(error.networkResponse.data, "UTF-8");
                } catch (Exception e) {
                    Logs.logE(e);
                    errorInfo = error.getMessage();
                }
            } else {
                errorInfo = error.getMessage();
            }
            errorToUI(eventId, errorCode, errorInfo);
        } else {
            errorToUI(eventId, errorCode, errorInfo);
        }
    }

    @Override
    final public void onResponse(String response) {
        try {
            Response object = analysisData(response);
            if (mRequest != null) {
                object.setCarryExtendObj(mRequest.getCarryExtendObj());
                object.setRequestPipIndex(mRequest.getRequestPipIndex());
            }
            mRequest = null;
            eventOver(object);
        } catch (EventException e) {
            Logs.logE(e);
            errorToUI(eventId, -10000000, e.getMessage());
        }
    }

    public void cancel() {
        if (mRequest != null) {
            QueueManager.getInstance().getHttpQueue().cancelAll(mRequest.getRequestTag());
        }
    }

    abstract public Request paramRequest(ArrayList<EventParam> paramList);

    abstract public Response analysisData(String data) throws EventException;

}