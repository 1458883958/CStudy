package com.example.wudelin.cstudy.recyclerviewadapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.wudelin.cstudy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wudelin on 2017/11/4.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {

    private List<Msg> msgList = new ArrayList<>();

    public MsgAdapter(List<Msg> msgList){
        this.msgList = msgList;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = msgList.get(position);
        if(msg.getType()==Msg.TYPE_RECEICE){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftText.setText(msg.getContain());
        }else if (msg.getType()==Msg.TYPE_SEND){
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.rightText.setText(msg.getContain());
        }

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftText;
        TextView rightText;


        public ViewHolder(View itemView) {
            super(itemView);
            this.leftLayout =  itemView.findViewById(R.id.left_layout);
            this.rightLayout =  itemView.findViewById(R.id.right_layout);
            this.leftText =  itemView.findViewById(R.id.left_msg);
            this.rightText =  itemView.findViewById(R.id.right_msg);
        }
    }
}
