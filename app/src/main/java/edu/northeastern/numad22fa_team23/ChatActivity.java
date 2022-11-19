package edu.northeastern.numad22fa_team23;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    List<Chat> chatList;
    EditText content;
    Button send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


    }
}
