package com.nguyen.wifibruteforce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyen.wifibruteforce.R;
import com.nguyen.wifibruteforce.model.WifiDetail;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WifiListAdapter extends BaseAdapter {
    Context context;
    ArrayList<WifiDetail> data;

    public WifiListAdapter(Context context, ArrayList<WifiDetail> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (null != data) {
            count = data.size();
        }
        return count;
    }

    @Override
    public WifiDetail getItem(int i) {
        WifiDetail wifiDetail = null;
        if (null != data) {
            wifiDetail = data.get(i);
        }
        return wifiDetail;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(this.context);
        if (view == null) {
            view = inflater.inflate(R.layout.networklist_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        WifiDetail wifiDetail = getItem(i);
        holder.txtitem.setText(wifiDetail.getName() + " (" + wifiDetail.getBSSID() + ")");
        holder.imgitem.setImageResource(wifiDetail.getSignalIcon());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.txtitem)
        TextView txtitem;
        @BindView(R.id.imgitem)
        ImageView imgitem;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }

    public ArrayList<WifiDetail> getData() {
        return data;
    }

    public void setData(ArrayList<WifiDetail> data) {
        this.data = data;
    }


}
