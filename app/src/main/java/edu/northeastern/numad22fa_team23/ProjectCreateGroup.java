package edu.northeastern.numad22fa_team23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProjectCreateGroup extends AppCompatActivity {
    private EditText addGroupEditText, groupDescriptionEditText;
    private Button createGroupBtn;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String uid;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj_create_group);

        //button to create a new group
        createGroupBtn = findViewById(R.id.createGroup_button);

        //new group name Edit Text
        addGroupEditText = findViewById(R.id.createGroup_editText);

        //description of new group name Edit Text
        groupDescriptionEditText = findViewById(R.id.createGroupDescription_editText);

        //data
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Button to create a new group
        createGroupBtn.setOnClickListener((view)-> {

            createGroupBtn.setEnabled(false);
            createGroupBtn.setText("Processing...");

            //get the input value from user
            final String newGroup = addGroupEditText.getText().toString().trim();
            final String newGroupDescription = groupDescriptionEditText.getText().toString();

            //if the name of new group is null, give a toast.
            if(newGroup.equals("")){
                createGroupBtn.setText("Create");
                createGroupBtn.setEnabled(true);
                Toast.makeText(ProjectCreateGroup.this, "Group name must at least one characters with no leading and trailing spaces.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            mDatabase.child("Groups").addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot group : snapshot.getChildren()) {
                        //if the name already exists
                        if(group.getKey().equals(newGroup)){
                            Toast.makeText(ProjectCreateGroup.this, "Failed: The group name" + newGroup + "already exists.",
                                    Toast.LENGTH_SHORT).show();
                            createGroupBtn.setText("Create");
                            createGroupBtn.setEnabled(true);
                            return;
                        }
                    }
                    //store the data to database
                    //new groupname, description
                    mDatabase.child("Groups").child(newGroup).child("GroupInfo").child("Admin").setValue("MLEFRubFlwNrvCs95RJ38ph5SBD2");
                    mDatabase.child("Groups").child(newGroup).child("GroupInfo").child("Description").setValue(newGroupDescription);
                    mDatabase.child("Users").child(uid).child("Groups").child(newGroup).setValue(false);

                    //reset the input to be null for next creating group activity
                    addGroupEditText.setText("");
                    groupDescriptionEditText.setText("");
                    createGroupBtn.setText("Create");
                    createGroupBtn.setEnabled(true);

                    //snackbar to show successfully the user create the group
                    Snackbar.make(findViewById(android.R.id.content), "Successfully created the group" + newGroup, Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        });

    }
}