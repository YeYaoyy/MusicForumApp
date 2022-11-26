package edu.northeastern.numad22fa_team23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import edu.northeastern.numad22fa_team23.model.Moment;

public class MomentsAdapter extends RecyclerView.Adapter<MomentViewHolder> {

    List<Moment> list = Collections.emptyList();
    Context context;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onButtonClicked(View view, int position, int momentId);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.mOnItemClickListener = clickListener;
    }

    public MomentsAdapter(List<Moment> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public MomentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View photoView = inflater.inflate(R.layout.recycler_moments, parent, false);
        MomentViewHolder viewHolder = new MomentViewHolder(context, photoView, mOnItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MomentViewHolder viewHolder, final int position) {
        final int index= viewHolder.getAdapterPosition();
        viewHolder.userName.setText(list.get(position).getUserName());
        viewHolder.musicName.setText(list.get(position).getMusicName());
        viewHolder.thought.setText(list.get(position).getThought());
        viewHolder.momentID = list.get(position).getMomentId();
        viewHolder.showComment(list.get(position).getCommentList());

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        viewHolder.recyclerView.setLayoutManager(linearLayoutManager);
//        CommentAdapter commentAdapter = new CommentAdapter(list.get(position).getCommentList());
//        viewHolder.recyclerView.setAdapter(commentAdapter);
    }

    @Override
    public int getItemCount() {
        if(list == null || list.size() == 0){
            return 0;
        }else{
            return list.size();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
