package edu.northeastern.numad22fa_team23;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;

public class proj_login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText email;
    private Button loginButton;
    private EditText password;
    private Button signup;
    private FirebaseAuth.AuthStateListener mAuthListener;

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
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(v -> {signUp();});
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                //User user = new User(userName.getText().toString());

                //mDatabase.child("users").child(user.getUsername()).setValue(user);
                //mDatabase.child("users").child(user.getUsername()).child("token").setValue(token);
//                Intent intent = new Intent(this, SendingMessage.class);
//                Bundle bundle = new Bundle();
               // mDatabase.child("final_project_users").child(email.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                 //   @Override
                  //  public void onComplete(@NonNull Task<DataSnapshot> task) {

                      //  if (!task.isSuccessful()) {
                        //    Log.e("firebase", "connection failed", task.getException());
                      //  }
                      //  else {
                            //Log.d("firebase", String.valueOf(task.getResult().getValue()));
                          //  String result = String.valueOf(task.getResult().getValue());
                           // if (result.equals("null")) {
//                                bundle.putInt("ifExit", 0);
                                //intent.putExtra("ifExit", 0);
                           // } else {
                                //intent.putExtra("ifExit", 1);
//                                bundle.putInt("ifExit", 1);
                           // }
//                            bundle.putString("username", userName.getText().toString());
//                            intent.putExtra("bundle", bundle);
//                            //intent.putExtra("username",userName.getText().toString());
//                            startActivity(intent);
//                        }
//                    }
//                });
//
//            }
//        });
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
               Toast.makeText(proj_login.this, "Authentication failed.",
                       Toast.LENGTH_SHORT).show();
           }
        });
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
                            Toast.makeText(proj_login.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                    }
                }
        );
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuthListener);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (mAuthListener != null) {
//            mAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
}
