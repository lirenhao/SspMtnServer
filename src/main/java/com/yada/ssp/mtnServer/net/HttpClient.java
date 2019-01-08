package com.yada.ssp.mtnServer.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpClient {

    private HttpClientBuilder clientBuilder;

    public HttpClient() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(256);
        cm.setDefaultMaxPerRoute(256);

        ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {

            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                long keepAliveDuration = super.getKeepAliveDuration(response, context);
                if (keepAliveDuration == -1) {
                    keepAliveDuration = 10 * 1000; // 45 seconds
                }
                return keepAliveDuration;
            }
        };

        clientBuilder = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(keepAliveStrategy);
    }

    public String postJson(String url, String data) throws IOException {
        CloseableHttpClient client = clientBuilder.build();

        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data);
        postEntity.setContentType("application/json");
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = client.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity);
    }
}
