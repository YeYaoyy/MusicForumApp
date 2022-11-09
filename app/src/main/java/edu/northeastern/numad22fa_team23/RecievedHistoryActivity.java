package edu.northeastern.numad22fa_team23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.List;

import edu.northeastern.numad22fa_team23.R;


public class RecievedHistoryActivity extends AppCompatActivity {
    private DatabaseReference myDataBase;
    private List<HashMap<String, String>> messages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieved_history);
        messages = (List<HashMap<String, String>>) getIntent().getExtras().get("history");
        RecyclerView receivedHistory = findViewById(R.id.history);
        receivedHistory.setLayoutManager(new LinearLayoutManager(this));
        receivedHistory.setAdapter(new MessageAdapter(messages));
    }

}