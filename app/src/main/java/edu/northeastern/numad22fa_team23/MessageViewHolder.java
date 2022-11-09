package edu.northeastern.numad22fa_team23;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageViewHolder extends RecyclerView.ViewHolder{
    private final ImageView receivedHistory;
//    private final TextView



    public MessageViewHolder(@NonNull View itemView, ImageView receivedHistory) {
        super(itemView);
        this.receivedHistory = receivedHistory;
    }
}
