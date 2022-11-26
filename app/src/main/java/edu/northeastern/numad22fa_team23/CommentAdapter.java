package edu.northeastern.numad22fa_team23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.numad22fa_team23.model.Comment;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<Comment> commentList;
    private Context context;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylcer_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        holder.userName.setText(commentList.get(position).getUserName());
        holder.content.setText(commentList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if(commentList == null || commentList.size() == 0){
            return 0;
        }else{
            return commentList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView content;

        public ViewHolder(View itemView) {
            super(itemView);
            this.userName = itemView.findViewById(R.id.commentUserName);
            this.content = itemView.findViewById(R.id.comment);
        }
    }
}
