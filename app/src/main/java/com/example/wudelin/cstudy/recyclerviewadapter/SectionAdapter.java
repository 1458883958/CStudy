package com.example.wudelin.cstudy.recyclerviewadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wudelin.cstudy.DetailTaskActivity;
import com.example.wudelin.cstudy.R;

import java.util.List;

/**
 * Created by wudelin on 2017/11/24.
 */

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder>{

    private List<Section> mSection;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView mCardView;
        ImageView sectionImage;
        TextView sectionName;
        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            sectionImage = itemView.findViewById(R.id.section_image);
            sectionName = itemView.findViewById(R.id.section_name);
        }
    }

    public SectionAdapter(List<Section> mSection) {
        this.mSection = mSection;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_task_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Section section = mSection.get(position);
                Intent intent = new Intent(mContext, DetailTaskActivity.class);
                intent.putExtra(DetailTaskActivity.TASK_NAME,section.getSectionName());
                intent.putExtra(DetailTaskActivity.TASK_IMAGE_ID,section.getSectionImageId());
                intent.putExtra(DetailTaskActivity.TASK_CONTENT_ID,position);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Section mSectionItem = mSection.get(position);
        holder.sectionName.setText(mSectionItem.getSectionName());
        Glide.with(mContext).load(mSectionItem.getSectionImageId()).into(holder.sectionImage);

    }

    @Override
    public int getItemCount() {
        return mSection.size();
    }


}
