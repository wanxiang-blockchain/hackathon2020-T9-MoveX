package com.example.vehicle.util;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;

/**
 * Http工具类
 *
 * @author 程高伟
 */

public class HttpUtil {

    public static Logger log= LoggerFactory.getLogger(HttpUtil.class);

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36";

    // 超时设置
    private static final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .setSocketTimeout(10000)
            .build();

    // 编码设置
    private static final ConnectionConfig connectionConfig = ConnectionConfig.custom()
            .setMalformedInputAction(CodingErrorAction.IGNORE)
            .setUnmappableInputAction(CodingErrorAction.IGNORE)
            .setCharset(Consts.UTF_8)
            .build();

    private static HttpClientBuilder getBuilder() {
        List<Header> headers = new ArrayList<>();
        Header header = new BasicHeader("User-Agent", USER_AGENT);
        headers.add(header);
        return HttpClients.custom().setDefaultConnectionConfig(connectionConfig).setDefaultHeaders(headers).setDefaultRequestConfig(requestConfig);
    }


    /**
     * 发送HttpPost请求，参数为json字符串
     *
     * @param url     请求地址
     * @return
     */
    public static String sendGet(String url) throws IOException {
        String result;

        // 设置entity

        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("apiKey","");

        try (CloseableHttpClient httpclient = getBuilder().build(); CloseableHttpResponse httpResponse = httpclient.execute(httpGet);) {
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        }
        return result;
    }


    public static void main(String[] args) throws IOException {

    }

}