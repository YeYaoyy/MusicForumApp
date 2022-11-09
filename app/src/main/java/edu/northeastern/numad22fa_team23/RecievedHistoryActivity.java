package edu.northeastern.numad22fa_team23;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;

import edu.northeastern.numad22fa_team23.R;


public class RecievedHistoryActivity extends AppCompatActivity {
    private DatabaseReference myDataBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieved_history);

    }



}