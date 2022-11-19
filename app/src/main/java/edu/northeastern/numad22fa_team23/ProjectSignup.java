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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProjectSignup extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText username;
    private Button signup;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(v -> {signUp();});
    }

    private void signUp() {
        String email_input = email.getText().toString();
        String pw = password.getText().toString();
        mAuth.createUserWithEmailAndPassword(email_input, pw).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "sign up:failure", task.getException());
                            Toast.makeText(ProjectSignup.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseUser user = mAuth.getCurrentUser();
                            ProjectUser pu = new ProjectUser(username.getText().toString());
                            mDatabase.child("Project_Users").child(username.getText().toString()).setValue(user);
                        }
                    }
                }
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
