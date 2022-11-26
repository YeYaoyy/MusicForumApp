package edu.northeastern.numad22fa_team23;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MomentViewHolder extends RecyclerView.ViewHolder {

    TextView userName;
    TextView musicName;
    TextView thought;
    View view;

    MomentViewHolder(View itemView) {
        super(itemView);
        userName = (TextView) itemView.findViewById(R.id.userName);
        musicName = (TextView) itemView.findViewById(R.id.musicName);
        thought = (TextView) itemView.findViewById(R.id.thought);
        view = itemView;
    }
}
