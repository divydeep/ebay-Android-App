package com.ebaysearch;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final String TAG_ERROR = "errorCode";

    EditText keyword;
    EditText priceFrom;
    EditText priceTo;
    Spinner sortBy;
    Button clear_btn;
    Button search_btn;
    TextView errorMessage;
    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorMessage = (TextView)findViewById(R.id.errorMsg);
        errorMessage.setVisibility(View.INVISIBLE);

        keyword = (EditText)findViewById(R.id.keyword);
        priceFrom = (EditText)findViewById(R.id.priceFrom);
        priceTo =(EditText)findViewById(R.id.priceTo);
        sortBy = (Spinner)findViewById(R.id.sortBy);

        search_btn = (Button)findViewById(R.id.search_btn);
        clear_btn  = (Button)findViewById(R.id.clear_btn);
        search_btn.setOnClickListener(searchbtnListener);
        clear_btn.setOnClickListener(clearbtnListener);
    }

    private View.OnClickListener clearbtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            keyword.setText("");
            priceFrom.setText("");
            priceTo.setText("");
            errorMessage.setText("");
            errorMessage.setVisibility(View.INVISIBLE);
            sortBy.setSelection(0);
        }
    };

    private View.OnClickListener searchbtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Boolean isKeywordEmpty = isEmpty(keyword);
            Boolean ispriceFromEmpty = isEmpty(priceFrom);
            Boolean ispriceToEmpty = isEmpty(priceTo);
            Boolean validInput = true;

            if(isKeywordEmpty)
            {
                errorMessage.setText("Please enter a keyword");
                validInput = false;
            }
            Float valPriceFrom = 0.0f;
            if(!ispriceFromEmpty)
            {
                valPriceFrom = Float.parseFloat(priceFrom.getText().toString());
                if (valPriceFrom < 0)
                {
                    errorMessage.setText("Price can only be a positive number");
                    validInput = false;
                }
            }

            if(!ispriceToEmpty)
            {
                Float valPriceTo = Float.parseFloat(priceTo.getText().toString());
                if (valPriceTo < 0)
                {
                    errorMessage.setText("Price can only be a positive number");
                    validInput = false;
                }
                if(valPriceTo > 0)
                {
                    if(!ispriceFromEmpty)
                    {
                        if(valPriceTo < valPriceFrom) {
                            errorMessage.setText("Price From should be less than or equal to Price To");
                            validInput = false;
                        }
                    }
                }
            }

            if(validInput)
            {
                errorMessage.setVisibility(View.INVISIBLE);
                String KEYWORD = String.valueOf(keyword.getText());
                String PRICE_FROM = String.valueOf(priceFrom.getText());
                String PRICE_TO = String.valueOf(priceTo.getText());
                Integer SORT = (int)sortBy.getSelectedItemId();
                String SORT_BY = "";
                String RESULTSPERPAGE = "5";
                String PAGENUMBER = "1";
                String url = "http://zephyr.elasticbeanstalk.com/?";

                switch (SORT)
                {
                    case 0:
                        SORT_BY = "bestmatch";
                        break;
                    case 1:
                        SORT_BY = "highprice";
                        break;
                    case 2:
                        SORT_BY = "pshigh";
                        break;
                    case 3:
                        SORT_BY = "pslow";
                        break;
                }

                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("keyword",KEYWORD));
                pairs.add(new BasicNameValuePair("pricemin",PRICE_FROM));
                pairs.add(new BasicNameValuePair("pricemax",PRICE_TO));
                pairs.add(new BasicNameValuePair("sorting",SORT_BY));
                pairs.add(new BasicNameValuePair("results",RESULTSPERPAGE));
                pairs.add(new BasicNameValuePair("pageNumber",PAGENUMBER));

                String params = URLEncodedUtils.format(pairs,"utf-8");
                url += params;

                new getDataTask().execute(url);

            }
            else
            {
                errorMessage.setVisibility(View.VISIBLE);
            }
        }
    };

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    private class getDataTask extends AsyncTask<String, Void, JSONObject>
    {
        @Override
        public JSONObject doInBackground(String... urls) {
            getJSON obj = new getJSON();
            JSONObject jsonObject = obj.getData(urls[0]);
            return jsonObject;
        }
        @Override
        public void onPostExecute(JSONObject jsonObject) {
            errorMessage = (TextView)findViewById(R.id.errorMsg);
            if(jsonObject.has(TAG_ERROR)){
                errorMessage.setVisibility(View.VISIBLE);
            }
            else{
                try {
                    String ack = jsonObject.getString("ack");
                    //Log.v(TAG,ack);
                    if(ack.equals("No results found"))
                    {
                        errorMessage.setText("No Results Found");
                        errorMessage.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        errorMessage.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                        intent.putExtra("json", jsonObject.toString());
                        intent.putExtra("searchString", keyword.getText().toString());
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}