package fr.webnicol.mondoapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by patex on 08/04/16.
 */
public class TransactionsAdapter extends ArrayAdapter<Transaction> {

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

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TransactionHolder();
            holder.amount = (TextView)row.findViewById(R.id.itemMoney);
            holder.merchantName = (TextView)row.findViewById(R.id.itemTitle);

            row.setTag(holder);
        }
        else
        {
            holder = (TransactionHolder)row.getTag();
        }

        Transaction weather = data[position];
        holder.amount.setText(weather.getAmount());
        holder.merchantName.setText(weather.getMerchantName());

        return row;
    }

    static class TransactionHolder
    {
        TextView amount;
        TextView merchantName;
    }
}
