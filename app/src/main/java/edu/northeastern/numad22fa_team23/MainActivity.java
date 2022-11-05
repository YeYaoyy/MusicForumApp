package edu.northeastern.numad22fa_team23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        int theId = view.getId();
        if(theId == R.id.atyourservice){
            Intent intent = new Intent(this, AtYourService.class);
            startActivity(intent);
        }
        if (theId == R.id.stickittoem) {
            Intent intent = new Intent(this, StickItToEm.class);
            startActivity(intent);
        }
    }
}