package com.young.image.utils;

import com.alibaba.fastjson.JSON;
import com.young.image.domain.UrlShortener;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;

/**
 * Created by yaobin on 2017/10/18.
 */
public class GoogleShortUtils {

    public static String convert(String longUrl){
        String url = "https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyBYx-Zr-GTZ5ZMq7JhGu_IJ6L8TPcBRI9M";
        Map<String, String> params = new HashedMap();
        params.put("longUrl", longUrl);
        String jsonStr = JSON.toJSONString(params);
        String result = HttpClientUtils.httpPost(url, jsonStr);
        UrlShortener urlShortener = JSON.parseObject(result, UrlShortener.class);

        return urlShortener.getId();
    }
}
