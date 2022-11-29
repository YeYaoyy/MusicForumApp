package edu.northeastern.numad22fa_team23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.numad22fa_team23.model.ProjectComment;

public class ProjectCommentAdapter extends RecyclerView.Adapter<ProjectCommentAdapter.ViewHolder> {

    private List<ProjectComment> projectCommentList;
    private Context context;

    public ProjectCommentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ProjectComment> projectCommentList) {
        this.projectCommentList = projectCommentList;
    }

    @NonNull
    @Override
    public ProjectCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylcer_comment, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectCommentAdapter.ViewHolder holder, int position) {
        holder.userName.setText(projectCommentList.get(position).getUserName());
        holder.content.setText(projectCommentList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if(projectCommentList == null || projectCommentList.size() == 0){
            return 0;
        }else{
            return projectCommentList.size();
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
