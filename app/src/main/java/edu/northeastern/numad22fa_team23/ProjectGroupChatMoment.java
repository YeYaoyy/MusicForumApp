package edu.northeastern.numad22fa_team23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProjectGroupChatMoment extends AppCompatActivity {
    private Button createChat;
    private Button createMoment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_group_chat_moment);
        createChat = findViewById(R.id.createChatGBtn);
        createMoment = findViewById(R.id.createMomentGBtn);
        createChat.setOnClickListener((v) ->{
            Intent intent = new Intent(this, ProjectGroupUI.class);
            startActivity(intent);
        });

        createMoment.setOnClickListener((v) ->{
            Intent intent = new Intent(this, ProjectGroupUI.class);
            startActivity(intent);
        });
    }
}