package fr.webnicol.mondoapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by patex on 08/04/16.
 */
/*public class TransactionsAdapter extends ArrayAdapter<Transaction> {

    Context context;
    int layoutResourceId;
    Transaction data[] = null;

    public TransactionsAdapter(Context context, int resource, Transaction[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TransactionHolder holder = null;
        Log.d("DEBUG", "get view");
        Log.d("DEBUG", String.valueOf(position));
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TransactionHolder();
            holder.amount = (TextView)row.findViewById(R.id.itemMoney);
            holder.merchantName = (TextView)row.findViewById(R.id.itemTitle);
            holder.imageView = (ImageView) row.findViewById(R.id.itemImage);

            row.setTag(holder);
        }
        else
        {
            holder = (TransactionHolder)row.getTag();
        }

        Transaction transaction = data[position];
        if (transaction.getAmount() < 0 ){
            holder.amount.setText(Double.toString(transaction.getAmount()/-100.0));
        } else {
            holder.amount.setText("+"+Double.toString(transaction.getAmount()/100.0));
        }
        holder.merchantName.setText(transaction.getMerchantName());
        Log.d("DEBUG", holder.amount.getText().toString());
        Log.d("DEBUG", holder.imageView.getDrawable().toString());
        if (transaction.getImageUrl() != "" ) {
            Log.d("URL", transaction.getImageUrl());
            new DownloadImageTask(holder.imageView)
                    .execute(transaction.getImageUrl());


        }

        return row;
    }

    static class TransactionHolder
    {
        TextView amount;
        TextView merchantName;
        ImageView imageView;
    }
}*/


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionsAdapter extends BaseAdapter {

    ArrayList myList = new ArrayList();
    LayoutInflater inflater;
    Context context;


    public TransactionsAdapter(Context context, ArrayList myList) {
        this.myList = myList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Transaction getItem(int position) {
        return (Transaction)myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_transactions, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        Transaction currentListData = getItem(position);


        if (currentListData.getAmount() < 0 ){
            mViewHolder.amount.setText(Double.toString(currentListData.getAmount()/-100.0));
        } else {
            mViewHolder.amount.setText("+"+Double.toString(currentListData.getAmount()/100.0));
        }
        mViewHolder.merchantName.setText(currentListData.getMerchantName());
        Log.d("DEBUG", mViewHolder.amount.getText().toString());
        Log.d("DEBUG", mViewHolder.imageView.getDrawable().toString());
        if (currentListData.getImageUrl() != "" ) {
            Log.d("URL", currentListData.getImageUrl());
            new DownloadImageTask(mViewHolder.imageView)
                    .execute(currentListData.getImageUrl());


        }

        return convertView;
    }

    private class MyViewHolder {
        TextView amount;
        TextView merchantName;
        ImageView imageView;

        public MyViewHolder(View item) {
            amount = (TextView) item.findViewById(R.id.itemMoney);
            merchantName = (TextView) item.findViewById(R.id.itemTitle);
            imageView = (ImageView) item.findViewById(R.id.itemImage);
        }
    }
}
