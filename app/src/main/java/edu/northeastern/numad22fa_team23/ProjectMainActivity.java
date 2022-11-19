package edu.northeastern.numad22fa_team23;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProjectMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_main);
    }

    public void onClick(View view){
        int theId = view.getId();
        if(theId == R.id.login){
            Intent intent = new Intent(this, ProjectLogin.class);
            startActivity(intent);
        }
        if (theId == R.id.signup) {
            Intent intent = new Intent(this, ProjectSignup.class);
            startActivity(intent);
        }

    }
}
