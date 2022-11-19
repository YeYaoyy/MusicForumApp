package edu.northeastern.numad22fa_team23;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class ChatActivity extends AppCompatActivity {
    List<Chat> chatList;
    EditText content;
    Button send;
    Context context;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        content = findViewById(R.id.edittext_chatbox);
        send = findViewById(R.id.button_chatbox_send);
        context = this;

        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Groups").child(groupName)
                .child("Chats").child(eventtoken)
                .child("Messages");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = content.getText().toString();
                if(msg.isEmpty()){
                    Toast.makeText(context, "Input cannot be empty!", Toast.LENGTH_SHORT).show();
                }else{
                    Date date = new Date();
                    TimeZone.setDefault(TimeZone.getTimeZone("GMT-7"));
                    Calendar cal = Calendar.getInstance(TimeZone.getDefault());
                    date = cal.getTime();
                    long t = date.getTime();
                    final String timeStamp = new Timestamp(t).toString().
                            replace(".", "");

                    Log.d("timeStamp", timeStamp);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("name", myname);
                    hashMap.put("content", msg);
                    hashMap.put("time", timeStamp);
                    hashMap.put("uid", uid);
                    mDatabase.child(timeStamp).setValue(hashMap);
                    editText.setText("");
                }
            }
        });

    }
}
