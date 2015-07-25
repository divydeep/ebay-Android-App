package com.ebaysearch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Divydeep Agarwal on 4/15/2015.
 */
public class MyCustomAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<ResultList> resultList;
    private JSONObject jData;
    private Context con;
    public MyCustomAdapter(Context context, List<ResultList> rList, JSONObject jsonData) {
        inflater = LayoutInflater.from(context);
        resultList = rList;
        jData = jsonData;
        con = context;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        final ViewHolder holder;
        if(convertView == null){
            view = inflater.inflate(R.layout.activity_result_list_view, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView)view.findViewById(R.id.title);
            holder.description = (TextView)view.findViewById(R.id.description);
            holder.image = (ImageView)view.findViewById(R.id.image);
            view.setTag(holder);
        }
        else{
            view = convertView;
            holder = (ViewHolder)view.getTag();
        }

        ResultList rl = resultList.get(position);
        holder.title.setText(rl.getTitle());
        holder.title.setOnClickListener(new titleListener(position));
        holder.description.setText(rl.getDescription());
        holder.image.setImageBitmap(rl.getImage());
        holder.image.setOnClickListener(new itemListener(position));
        holder.image.setTag(rl.getItemUrl());
        return view;
    }


    private class ViewHolder {
        public ImageView image;
        public TextView title, description;
    }

    private class itemListener implements View.OnClickListener {
        int position;
        public itemListener(int pos) {
            this.position = pos;
        }

        @Override
        public void onClick(View v) {
            String url = (String)v.getTag();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(url));
            con.startActivity(intent);
        }
    }

    private class titleListener implements View.OnClickListener {
        int position;
        public titleListener(int pos) {
            this.position = pos;
        }

        @Override
        public void onClick(View v) {
            ResultList r = resultList.get(position);
            Intent intent2 = new Intent(con,DetailsActivity.class);
            intent2.putExtra("jsonData",jData.toString());
            intent2.putExtra("position",position);
            intent2.putExtra("title",r.getTitle());
            intent2.putExtra("desc",r.getDescription());
            intent2.putExtra("image",r.getImage());
            intent2.putExtra("itemUrl", r.getItemUrl());
            con.startActivity(intent2);

        }
    }
}
