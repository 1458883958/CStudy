package com.example.wudelin.cstudy.recyclerviewadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wudelin.cstudy.LookNoteActivity;
import com.example.wudelin.cstudy.R;
import com.example.wudelin.cstudy.db.Diary;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by wudelin on 2017/12/14.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> mListNote;
    private Context mContext;

    public interface OnItemOnClickListener {
        void onItemOnClick(View view, int pos);

        void onItemLongOnClick(View view, int pos);
    }

    private OnItemOnClickListener mOnItemOnClickListener;

    public void setmOnItemOnClickListener(OnItemOnClickListener mOnItemOnClickListener) {
        this.mOnItemOnClickListener = mOnItemOnClickListener;
    }

    public NoteAdapter(List<Note> mListNote) {
        this.mListNote = mListNote;
    }

    public void removeItem(int pos){
        DataSupport.deleteAll(Diary.class,"date = ?",mListNote.get(pos).getDate().toString());
        mListNote.remove(pos);
        notifyItemRemoved(pos);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mContext == null)
            mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_note_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        if (mOnItemOnClickListener != null) {
            viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemOnClickListener.onItemOnClick(view, position);
                }
            });
            viewHolder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemOnClickListener.onItemLongOnClick(view, position);
                    return true;
                }
            });
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = mListNote.get(position);
        holder.noteDate.setText(note.getDate());
        holder.noteTitle.setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return mListNote.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView noteDate;
        TextView noteTitle;
        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView;
            this.noteDate = itemView.findViewById(R.id.note_date);
            this.noteTitle = itemView.findViewById(R.id.note_title);
        }
    }
}
