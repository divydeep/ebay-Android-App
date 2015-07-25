package com.ebaysearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;


public class DetailsActivity extends ActionBarActivity {
    Integer position;
    JSONObject jsonData, itemX, itemX_basic, itemX_seller, itemX_shipping;
    String title, description, itemUrl;
    Bitmap itemImage;
    TextView dTitle, dPrice, dLocation;
    ImageView dImage, dTopRated, dFb;
    Button buynow_btn;
    Button button1, button2, button3;
    Context context;
    RelativeLayout rl;
    LayoutInflater inflater;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

                if (null == result.getPostId())
                {
                    Toast toast = Toast.makeText(context,"Post Cancelled",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(context,"Posted Story, ID: " + result.getPostId(),Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 0);
                    toast.show();
                }
            }

            @Override
            public void onCancel() {
                Toast toast = Toast.makeText(context,"Post Cancelled",Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0 ,0);
                toast.show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast toast = Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
            }
        });

        context = this;
        try {
            jsonData = new JSONObject(getIntent().getStringExtra("jsonData"));
            position = getIntent().getIntExtra("position",0);
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("desc");
            itemUrl = getIntent().getStringExtra("itemUrl");
            itemImage = getIntent().getParcelableExtra("image");

            dTitle = (TextView)findViewById(R.id.itemTitle);
            dPrice = (TextView)findViewById(R.id.itemPrice);
            dLocation = (TextView)findViewById(R.id.itemLocation);
            dImage = (ImageView)findViewById(R.id.itemImage);
            dTopRated = (ImageView)findViewById(R.id.topRated);
            dFb = (ImageView)findViewById(R.id.fbLogo);
            buynow_btn = (Button)findViewById(R.id.buynow_btn);
            button1 = (Button)findViewById(R.id.button1);
            button2 = (Button)findViewById(R.id.button2);
            button3 = (Button)findViewById(R.id.button3);
            rl = (RelativeLayout)findViewById(R.id.tabContent);
            inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.layout1,null);
            rl.addView(v);

            dTitle.setText(title);
            dPrice.setText(description);
            dTopRated.setVisibility(View.INVISIBLE);
            dImage.setImageBitmap(itemImage);
            String itemNum = "item";
            switch(position)
            {
                case 0:
                    itemNum += "0";
                    break;
                case 1:
                    itemNum += "1";
                    break;
                case 2:
                    itemNum += "2";
                    break;
                case 3:
                    itemNum += "3";
                    break;
                case 4:
                    itemNum += "4";
                    break;
            }

            itemX = jsonData.getJSONObject(itemNum);
            itemX_basic = itemX.getJSONObject("basicInfo");
            itemX_seller = itemX.getJSONObject("sellerInfo");
            itemX_shipping = itemX.getJSONObject("shippingInfo");
            if (itemX_basic.getString("topRatedListing").equals("true")){
                dTopRated.setVisibility(View.VISIBLE);
            }
            dLocation.setText(itemX_basic.getString("location").toString());
            TextView t1 = (TextView)v.findViewById(R.id.category);
            TextView t2 = (TextView)v.findViewById(R.id.condition);
            TextView t3 = (TextView)v.findViewById(R.id.buyingformat);
            try {
                t1.setText(itemX_basic.getString("categoryName"));
                t2.setText(itemX_basic.getString("conditionDisplayName"));
                t3.setText(itemX_basic.getString("listingType"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            buynow_btn.setOnClickListener(buynowListener);
            button1.setOnClickListener(btn1Listener);
            button1.setBackgroundResource(R.drawable.button2);
            button2.setOnClickListener(btn2Listener);
            button3.setOnClickListener(btn3Listener);
            dFb.setOnClickListener(facebookListener);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private View.OnClickListener btn1Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            button1.setBackgroundResource(R.drawable.button2);
            button2.setBackgroundResource(R.drawable.button1);
            button3.setBackgroundResource(R.drawable.button1);
            rl = (RelativeLayout)findViewById(R.id.tabContent);
            rl.removeAllViews();
            v = inflater.inflate(R.layout.layout1,null);
            rl.addView(v);

            TextView t1 = (TextView)v.findViewById(R.id.category);
            TextView t2 = (TextView)v.findViewById(R.id.condition);
            TextView t3 = (TextView)v.findViewById(R.id.buyingformat);
            try {
                t1.setText(itemX_basic.getString("categoryName"));
                t2.setText(itemX_basic.getString("conditionDisplayName"));
                t3.setText(itemX_basic.getString("listingType"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener btn2Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            button2.setBackgroundResource(R.drawable.button2);
            button3.setBackgroundResource(R.drawable.button1);
            button1.setBackgroundResource(R.drawable.button1);
            rl = (RelativeLayout)findViewById(R.id.tabContent);
            rl.removeAllViews();
            v = inflater.inflate(R.layout.layout2,null);
            rl.addView(v);

            TextView t1 = (TextView)v.findViewById(R.id.username);
            TextView t2 = (TextView)v.findViewById(R.id.feedbackscore);
            TextView t3 = (TextView)v.findViewById(R.id.posfeedback);
            TextView t4 = (TextView)v.findViewById(R.id.feedbackrating);
            ImageView i1 = (ImageView)v.findViewById(R.id.toprated);
            TextView t5 = (TextView)v.findViewById(R.id.store);

            try {
                if (itemX_seller.getString("sellerUserName").isEmpty()){
                    t1.setText("N/A");
                }
                else{
                    t1.setText(itemX_seller.getString("sellerUserName"));
                }
                if (itemX_seller.getString("feedbackScore").isEmpty()){
                    t2.setText("N/A");
                }
                else{
                    t2.setText(itemX_seller.getString("feedbackScore"));
                }
                if(itemX_seller.getString("positiveFeedbackPercent").isEmpty()){
                    t3.setText("N/A");
                }
                else{
                    t3.setText(itemX_seller.getString("positiveFeedbackPercent"));
                }
                if (itemX_seller.getString("feedbackRatingStar").isEmpty()){
                    t4.setText("N/A");
                }
                else{
                    t4.setText(itemX_seller.getString("feedbackRatingStar"));
                }
                if (itemX_seller.getString("topRatedSeller").equals("false")){
                    i1.setImageResource(R.drawable.error);
                }
                else{
                    i1.setImageResource(R.drawable.tick);
                }
                if (itemX_seller.getString("sellerStoreName").isEmpty()){
                    t5.setText("N/A");
                }
                else{
                    t5.setText(itemX_seller.getString("sellerStoreName"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener btn3Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            button3.setBackgroundResource(R.drawable.button2);
            button1.setBackgroundResource(R.drawable.button1);
            button2.setBackgroundResource(R.drawable.button1);
            rl = (RelativeLayout)findViewById(R.id.tabContent);
            rl.removeAllViews();

            v = inflater.inflate(R.layout.layout3,null);
            rl.addView(v);

            TextView t1 = (TextView)v.findViewById(R.id.shippingtype);
            TextView t2 = (TextView)v.findViewById(R.id.handlingtime);
            TextView t3 = (TextView)v.findViewById(R.id.shippinglocations);
            ImageView i1 = (ImageView)v.findViewById(R.id.expedited);
            ImageView i2 = (ImageView)v.findViewById(R.id.oneday);
            ImageView i3 = (ImageView)v.findViewById(R.id.returns);

            try {
                if (itemX_shipping.getString("shippingType").isEmpty()){
                    t1.setText("N/A");
                }
                else{
                    String abc = itemX_shipping.getString("shippingType");
                    t1.setText(splitString(abc));
                    //t1.setText(itemX_shipping.getString("shippingType"));
                }
                if (itemX_shipping.getString("handlingTime").isEmpty()){
                    t2.setText("N/A");
                }
                else{
                    t2.setText(itemX_shipping.getString("handlingTime") + " day(s)");
                }
                if (itemX_shipping.getString("shipToLocations").isEmpty()){
                    t3.setText("N/A");
                }
                else{
                    t3.setText(itemX_shipping.getString("shipToLocations"));
                }
                if (itemX_shipping.getString("expeditedShipping").equals("false")){
                    i1.setImageResource(R.drawable.error);
                }
                else{
                    i1.setImageResource(R.drawable.tick);
                }
                if (itemX_shipping.getString("oneDayShippingAvailable").equals("false")){
                    i2.setImageResource(R.drawable.error);
                }
                else{
                    i2.setImageResource(R.drawable.tick);
                }
                if (itemX_shipping.getString("returnsAccepted").equals("false")){
                    i3.setImageResource(R.drawable.error);
                }
                else{
                    i3.setImageResource(R.drawable.tick);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener facebookListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String fb_desc = "";
            String fb_img_url = "";
            try {
                fb_img_url = itemX_basic.getString("galleryURL");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            fb_desc = description + ", Location: " + dLocation.getText();

            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(title)
                        .setContentDescription(fb_desc)
                        .setContentUrl(Uri.parse(itemUrl))
                        .setImageUrl(Uri.parse(fb_img_url))
                        .build();

                shareDialog.show(linkContent);

            }
        }
    };
    private View.OnClickListener buynowListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(itemUrl));
            context.startActivity(intent);
        }
    };

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

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    static String splitString(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
