package com.ebaysearch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.util.EntityUtils;
import android.util.Log;

public class getJSON {
    static JSONObject jsonObject = null;
    static String json = "";

    public JSONObject getData(String url){
        try{
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            json = EntityUtils.toString(entity);
        } catch (UnsupportedEncodingException e) {
            Log.e("Encoding Error", e.toString());
        } catch (ClientProtocolException e) {
            Log.e("Protocol Error", e.toString());
        } catch (IOException e) {
            Log.e("I/O Error", e.toString());
        }

        try{
            jsonObject = new JSONObject(json);
        } catch(JSONException e){
            Log.e("JSON Parse Error", e.toString());
        }
        return jsonObject;
    }
}
