package com.ebaysearch;

/**
 * Created by Divydeep Agarwal on 4/15/2015.
 */
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GetImages {
    List<Bitmap> images = new ArrayList<Bitmap>();

    public List<Bitmap> GetImage(String... urls){
        for(int i=0; i<urls.length; i++){
                Bitmap bmp = null;
                try {
                    URL url = new URL(urls[i]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    InputStream is = con.getInputStream();
                    bmp = BitmapFactory.decodeStream(is);
                    if(null != bmp){
                        images.add(bmp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return images;
    }
}

