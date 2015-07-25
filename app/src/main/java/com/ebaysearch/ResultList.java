package com.ebaysearch;

import android.graphics.Bitmap;

/**
 * Created by Divydeep Agarwal on 4/15/2015.
 */
public class ResultList {
    private String title;
    private String description;
    private String itemUrl;
    private Bitmap image;

    public ResultList(String rtitle)
    {
        title = rtitle;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String rdescription){
        description = rdescription;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String ritemUrl) {
        itemUrl = ritemUrl;
    }

    public Bitmap getImage(){
        return image;
    }

    public void setImage(Bitmap rimage) {
        image = rimage;
    }
}
