package com.ebaysearch;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultActivity extends ActionBarActivity {

    ListView lv;
    Context context;
    JSONObject jsonData, item0m, item1m, item2m, item3m, item4m;
    JSONObject item0, item1, item2, item3, item4;
    String searchString;
    String[] imageURLS;
    TextView heading;
    private List<ResultList> rList;
    public Bitmap img0, img1, img2, img3, img4;
    String title0, title1, title2, title3, title4;
    String desc0, desc1, desc2, desc3, desc4;
    String itemUrl0, itemUrl1, itemUrl2, itemUrl3, itemUrl4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        heading = (TextView)findViewById(R.id.heading);
        context = this;

        try {
            jsonData = new JSONObject(getIntent().getStringExtra("json"));
            searchString = getIntent().getStringExtra("searchString").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String headingText = "Results for '" + searchString.toString() + "'";
        heading.setText(headingText);

        initResultList();

    }

    private void initResultList() {
        rList = new ArrayList<ResultList>();

        try {
            item0m = jsonData.getJSONObject("item0");
            item1m = jsonData.getJSONObject("item1");
            item2m = jsonData.getJSONObject("item2");
            item3m = jsonData.getJSONObject("item3");
            item4m = jsonData.getJSONObject("item4");

            item0 = item0m.getJSONObject("basicInfo");
            item1 = item1m.getJSONObject("basicInfo");
            item2 = item2m.getJSONObject("basicInfo");
            item3 = item3m.getJSONObject("basicInfo");
            item4 = item4m.getJSONObject("basicInfo");

            title0 = item0.getString("title");
            title1 = item1.getString("title");
            title2 = item2.getString("title");
            title3 = item3.getString("title");
            title4 = item4.getString("title");

            Double price0 = item0.getDouble("convertedCurrentPrice");
            Double price1 = item1.getDouble("convertedCurrentPrice");
            Double price2 = item2.getDouble("convertedCurrentPrice");
            Double price3 = item3.getDouble("convertedCurrentPrice");
            Double price4 = item4.getDouble("convertedCurrentPrice");

            Double shipcost0, shipcost1, shipcost2, shipcost3, shipcost4;

            if(item0.getString("shippingServiceCost").isEmpty()){
                shipcost0 = 0.0;
            }
            else {
                shipcost0 = item0.getDouble("shippingServiceCost");
            }

            if(item1.getString("shippingServiceCost").isEmpty()){
                shipcost1 = 0.0;
            }
            else {
                shipcost1 = item1.getDouble("shippingServiceCost");
            }

            if(item2.getString("shippingServiceCost").isEmpty()){
                shipcost2 = 0.0;
            }
            else {
                shipcost2 = item2.getDouble("shippingServiceCost");
            }
            if(item3.getString("shippingServiceCost").isEmpty()){
                shipcost3 = 0.0;
            }
            else {
                shipcost3 = item3.getDouble("shippingServiceCost");
            }
            if(item4.getString("shippingServiceCost").isEmpty()){
                shipcost4 = 0.0;
            }
            else {
                shipcost4 = item4.getDouble("shippingServiceCost");
            }

            if (shipcost0 > 0) {
                desc0 = "Price: $" + price0 + " (+ $" + shipcost0 + " Shipping)";
            } else {
                desc0 = "Price: $" + price0 + " (FREE Shipping)";
            }
            if (shipcost1 > 0) {
                desc1 = "Price: $" + price1 + " (+ $" + shipcost1 + " Shipping)";
            } else {
                desc1 = "Price: $" + price1 + " (FREE Shipping)";
            }
            if (shipcost2 > 0) {
                desc2 = "Price: $" + price2 + " (+ $" + shipcost2 + " Shipping)";
            } else {
                desc2 = "Price: $" + price2 + " (FREE Shipping)";
            }
            if (shipcost3 > 0) {
                desc3 = "Price: $" + price3 + " (+ $" + shipcost3 + " Shipping)";
            } else {
                desc3 = "Price: $" + price3 + " (FREE Shipping)";
            }
            if (shipcost4 > 0) {
                desc4 = "Price: $" + price4 + " (+ $" + shipcost4 + " Shipping)";
            } else {
                desc4 = "Price: $" + price4 + " (FREE Shipping)";
            }
            itemUrl0 = item0.getString("viewItemURL");
            itemUrl1 = item1.getString("viewItemURL");
            itemUrl2 = item2.getString("viewItemURL");
            itemUrl3 = item3.getString("viewItemURL");
            itemUrl4 = item4.getString("viewItemURL");

            imageURLS = new String[5];
            imageURLS[0] = item0.getString("galleryURL");
            imageURLS[1] = item1.getString("galleryURL");
            imageURLS[2] = item2.getString("galleryURL");
            imageURLS[3] = item3.getString("galleryURL");
            imageURLS[4] = item4.getString("galleryURL");

            new GetImage().execute(imageURLS);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addResult(String title, String desc, Bitmap img, String itemUrl) {
        ResultList rl = new ResultList(title);
        rl.setDescription(desc);
        rl.setImage(img);
        rl.setItemUrl(itemUrl);
        rList.add(rl);
    }

    private class GetImage extends AsyncTask<String, Void, List<Bitmap>> {

        @Override
        public List<Bitmap> doInBackground(String... urls) {
            GetImages obj = new GetImages();
            List<Bitmap> imgObject = obj.GetImage(urls);
            return imgObject;
        }
        @Override
        public void onPostExecute(List<Bitmap> result) {
            img0 = result.get(0);
            img1 = result.get(1);
            img2 = result.get(2);
            img3 = result.get(3);
            img4 = result.get(4);

            callResult();
        }
    }

    private void callResult() {
        addResult(title0, desc0, img0, itemUrl0);
        addResult(title1, desc1, img1, itemUrl1);
        addResult(title2, desc2, img2, itemUrl2);
        addResult(title3, desc3, img3, itemUrl3);
        addResult(title4, desc4, img4, itemUrl4);

        lv=(ListView)findViewById(R.id.resultList);
        MyCustomAdapter adapter = new MyCustomAdapter(this,rList,jsonData);
        lv.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}