package edu.northeastern.numad22fa_team23;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StickItToEm extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText userName;
    private Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        userName = findViewById(R.id.username);
        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(userName.getText().toString());
                mDatabase.child("users").child(user.getUsername()).setValue(user);
                Intent intent = new Intent(StickItToEm.this, SendingMessage.class);
                startActivity(intent);
            }
        });
    }

}
