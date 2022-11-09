package edu.northeastern.numad22fa_team23;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;

public class MessageViewHolder extends RecyclerView.ViewHolder{
    private final ImageView receivedHistory;
    private final TextView fromUser;
    private final TextView sendTime;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        this.receivedHistory = (ImageView) itemView.findViewById(R.id.receivedHistory);
        this.fromUser = (TextView)itemView.findViewById(R.id.fromUser);
        this.sendTime = (TextView) itemView.findViewById(R.id.sendTime);
    }
    public void bindThisData(Message messageToBind) {
        HashMap<Integer, Integer> emoji = new HashMap<>();
        int imageResource = messageToBind.getImageId();
        emoji.put(R.id.image01, R.drawable.image01);
        emoji.put(R.id.image02, R.drawable.image02);
        emoji.put(R.id.image03, R.drawable.image03);
        fromUser.setText(messageToBind.getSender());
        sendTime.setText(messageToBind.getTime());
        for(int i : emoji.keySet()){
            if(imageResource == i){
                receivedHistory.setImageResource(emoji.get(i));
                break;
            }
        }
    }
}
