package com.dragon.util;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

public class ApacheHttpClient {

    public static String getMethod(String url, String accept) {
        String result = "";
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);//连接时间
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);//数据传输时间

            HttpGet getRequest = new HttpGet(url);
            if (StringUtils.isNotBlank(accept)) {
                getRequest.addHeader("accept", accept);
            }
            HttpResponse response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            result = IOUtils.toString(response.getEntity().getContent());
            result = result.substring(1, result.length() - 1).replace("\\", "");
            httpClient.getConnectionManager().shutdown();
        } catch (IOException ex) {
            Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, url+"--"+ex.getMessage());
        }
        return result;
    }

    public static String postMethod(String url, Object param, String accept) {
        String result = "";
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url);
            StringEntity input = new StringEntity(JSON.toJSONString(param));
            if (StringUtils.isNotBlank(accept)) {
                postRequest.addHeader("accept", accept);
            }
            input.setContentType("application/json");
            postRequest.setEntity(input);
            HttpResponse response = httpClient.execute(postRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            result = IOUtils.toString(response.getEntity().getContent());
            result = result.substring(1, result.length() - 1).replace("\\", "");
            httpClient.getConnectionManager().shutdown();
        } catch (IOException ex) {
            Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static String postMethod(String url, Map<String, String> map) {
        String result = "";
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url);
            List<NameValuePair> params = new ArrayList<>();
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                params.add(new BasicNameValuePair(key, map.get(key)));
            }
            postRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpResponse response = httpClient.execute(postRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            result = IOUtils.toString(response.getEntity().getContent());
            result = result.substring(1, result.length() - 1).replace("\\", "");
            httpClient.getConnectionManager().shutdown();
        } catch (IOException ex) {
            Logger.getLogger(ApacheHttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
