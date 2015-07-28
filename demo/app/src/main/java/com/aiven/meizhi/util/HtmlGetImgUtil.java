package com.aiven.meizhi.util;

import android.text.TextUtils;

import com.aiven.meizhi.model.ImgBase;
import com.aiven.seafox.controller.log.Logs;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Aiven
 * Email : aiven163@sina.com
 * Date : 2015/7/22  17:29
 * Desc :
 */
public class HtmlGetImgUtil {


    public static List<ImgBase> htmlGetImgs(String htmlStr) {
        try {
            JSONObject obj = new JSONObject(htmlStr);
            return getImgs(obj.optString("data"));
        } catch (Exception e) {
            Logs.logE(e);
            return null;
        }
    }

    private static List<ImgBase> getImgs(String content) {
        List<ImgBase> list = new ArrayList<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        p_image = Pattern.compile("<img\\s+(?:[^>]*)src\\s*=\\s*([^>]+)/>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        m_image = p_image.matcher(content);
        while (m_image.find()) {
            img = m_image.group();
            Matcher m = Pattern.compile("src=\"([^\"]+)\"").matcher(img);
            while (m.find()) {
                String tempSelected = m.group(1);
                if (!TextUtils.isEmpty(tempSelected)) {
                    ImgBase base = new ImgBase();
                    base.setThumbImgUrl(tempSelected.replace("200,166", "480,400"));
                    base.setNomalImgUrl(tempSelected.replace("200,166", "960,800"));
                    list.add(base);
                }
            }
        }
        return list;
    }
}
