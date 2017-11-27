package com.example.wudelin.cstudy.recyclerviewadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wudelin.cstudy.DetailLearnActivity;
import com.example.wudelin.cstudy.R;

import java.util.List;

/**
 * Created by wudelin on 2017/11/26.
 */

public class LearnAdapter extends RecyclerView.Adapter<LearnAdapter.ViewHolder>{

    private List<Learn> mList;
    private Context mContext;

    public LearnAdapter(List<Learn> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_learn_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Learn learn = mList.get(position);
                Intent intent = new Intent(mContext, DetailLearnActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("imageView",learn.getImageViewId());
                //Log.d("wdl", "onClick: ");
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Learn learn = mList.get(position);
            holder.learnTitle.setText(learn.getTitle());
            holder.learnTime.setText(learn.getTime());
            Glide.with(mContext).load(learn.getImageViewId()).into(holder.learnImageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView learnTitle;
        TextView learnTime;
        ImageView learnImageView;
        LinearLayout mLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout) itemView;
            learnTitle = itemView.findViewById(R.id.recycler_learn_title);
            learnTime = itemView.findViewById(R.id.recycler_learn_time);
            learnImageView = itemView.findViewById(R.id.learn_cardview_imageview);
        }
    }
}
