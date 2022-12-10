package edu.northeastern.numad22fa_team23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.northeastern.numad22fa_team23.model.ProjectComment;
import edu.northeastern.numad22fa_team23.model.ProjectMoment;
import edu.northeastern.numad22fa_team23.model.ProjectUser;

public class ProjectGroupChatMoment extends AppCompatActivity {
    private Button createChat;
    private Button createMoment;
    private Button joinGroup;
    private DatabaseReference mDatabase;
    private TextView groupDescription;
    private List<String> groupnameList;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private String uid;
    private FirebaseUser currentUser;
    private List<String> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_group_chat_moment);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        //data
        Intent i = getIntent();
        Bundle data = i.getExtras();
        final String groupname = i.getStringExtra("groupname");
        final String username = i.getStringExtra("username");

        //textview to show description of this group
        groupDescription = findViewById(R.id.textView_groupDescription_ui);

        //button to create a new chat in the group
        createChat = findViewById(R.id.createChatGBtn);
        createChat.setOnClickListener((v) -> {
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtras(data);
            startActivity(intent);
        });

        //button to create a new moment in the group
        createMoment = findViewById(R.id.createMomentGBtn);
        createMoment.setOnClickListener((v) -> {
            Intent intent = new Intent(this, ProjectMomentsActivity.class);
            intent.putExtras(data);
            startActivity(intent);
        });

        //button to join this group
        joinGroup = findViewById(R.id.join);
        reference = FirebaseDatabase.getInstance().getReference("Groups").child(groupname).child("GroupInfo").child("GroupUserName");
        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        //store the user in the same group in a list
                        userList = (List<String>) task.getResult().getValue();
                        //if the group doesn't contain any user
                        if (userList == null) {
                            List<String> newList = new ArrayList<>();
                            newList.add(username);
                            reference.setValue(newList);
                            Toast.makeText(ProjectGroupChatMoment.this, "Congratulations! You have successfully joined this group!", Toast.LENGTH_LONG).show();
                        } else {
                            //add the user to the group
                            if (!userList.contains(username)) {
                                userList.add(username);
                                reference.setValue(userList);
                                Toast.makeText(ProjectGroupChatMoment.this, "Congratulations! You have successfully joined this group!", Toast.LENGTH_LONG).show();
                            } else {
                                //If the user already joined the group
                                Toast.makeText(ProjectGroupChatMoment.this, "You have already joined this group!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });

        //get the description in database
        mDatabase = FirebaseDatabase.getInstance().getReference("Groups").child(groupname).child("GroupInfo");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get description of this group from database
                String description = dataSnapshot.child("Description").getValue().toString();
                // Set description
                groupDescription.setText(description);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}