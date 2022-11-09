package edu.northeastern.numad22fa_team23;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder>{
    public final List<Message> messages;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_received_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        //HashMap<String, String> m = messages.get(position);
        System.out.println(messages);
        System.out.println(messages.get(0));
        holder.bindThisData(messages.get(position));
    }

    @Override
    public int getItemCount() {
        if(messages.size() == 0 || messages == null){
            return 0;
        }else{
            return messages.size();
        }
    }
}
