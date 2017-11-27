package com.example.wudelin.cstudy.listviewadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wudelin.cstudy.R;

import java.util.List;

/**
 * Created by wudelin on 2017/11/27.
 */

public class ItemListViewAdapter extends ArrayAdapter<Item> {

    private int resoureId;
    public ItemListViewAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
        resoureId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item item = getItem(position);
        ViewHolder viewHolder;
        View view;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resoureId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.itemImage = view.findViewById(R.id.listview_item_image);
            viewHolder.itemText = view.findViewById(R.id.listview_item_text);
            viewHolder.itemIcon = view.findViewById(R.id.listview_item_icon);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(getContext()).load(item.getItemImageId()).into(viewHolder.itemImage);
        viewHolder.itemText.setText(item.getItemText());
        Glide.with(getContext()).load(item.getItemIcon()).into(viewHolder.itemIcon);
        return view;
    }
    static class ViewHolder{
        ImageView itemImage;
        TextView itemText;
        ImageView itemIcon;
    }
}
