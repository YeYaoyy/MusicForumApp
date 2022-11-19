package edu.northeastern.numad22fa_team23;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ProjectLogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText email;
    private Button loginButton;
    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_login);

        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference();
        email = findViewById(R.id.email);
        loginButton = findViewById(R.id.login);
        password = findViewById(R.id.password);
        loginButton.setOnClickListener(v -> {login();});

    }

    private void login() {
        String email_input = email.getText().toString();
        String pw = password.getText().toString();
        mAuth.signInWithEmailAndPassword(email_input, pw).addOnCompleteListener(this, task -> {
           if (task.isSuccessful()) {
               Log.d("TAG", "signInWithEmail:success");
               FirebaseUser user = mAuth.getCurrentUser();
           } else {
               Log.w("TAG", "signInWithEmail:failure", task.getException());
               Toast.makeText(ProjectLogin.this, "Authentication failed.",
                       Toast.LENGTH_SHORT).show();
           }
        });
    }

}
