package edu.northeastern.numad22fa_team23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Chat> chatList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final int MESSAGE_SENT = 1;
    private static final int MESSAGE_RECEIVED = 2;
    int temp = MESSAGE_SENT;

    public ChatListAdapter(Context context, List<Chat> chat) {
        mContext = context;
        chatList = chat;
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = (Chat) chatList.get(position);

        if (chat.getUid().equals(mAuth.getCurrentUser().getUid())) {
            return MESSAGE_SENT;
        } else {
            return MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_SENT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == MESSAGE_RECEIVED) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = (Chat) chatList.get(position);

        switch (holder.getItemViewType()) {
            case MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(chat);
                break;
            case MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(chat);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_send_content);
            timeText = (TextView) itemView.findViewById(R.id.message_send_time);
        }

        void bind(Chat chat) {
            messageText.setText(chat.getContent());

            // Format the stored timestamp into a readable String using method.
            String ts = chat.getTime();
            timeText.setText(ts.substring(0,19));
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.message_receive_body);
            timeText = (TextView) itemView.findViewById(R.id.message_receive_time);
            nameText = (TextView) itemView.findViewById(R.id.message_name);
        }

        void bind(Chat chat) {
            messageText.setText(chat.getContent());

            // Format the stored timestamp into a readable String using method.
            String ts = chat.getTime();
            timeText.setText(ts.substring(0, 19));
            //TODO if within today then display time only otherwise display date too
            nameText.setText(chat.getUsername());

        }
    }
}
