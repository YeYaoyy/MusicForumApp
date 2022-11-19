package edu.northeastern.numad22fa_team23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ProjectCreateGroup extends AppCompatActivity {
    private EditText addGroupEditText, groupDescpritionEditText;
    private Button createGroupBtn;
    private DatabaseReference mDatabase;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj_create_group);
        createGroupBtn = findViewById(R.id.createGroup_button);
        addGroupEditText = findViewById(R.id.createGroup_editText);
        groupDescpritionEditText = findViewById(R.id.createGroupDescription_editText);

        //Button to create a new group
        createGroupBtn.setOnClickListener((view)-> {
            createGroupBtn.setEnabled(false);
            createGroupBtn.setText("Processing...");
            final String newGroup = addGroupEditText.getText().toString().trim();
            final String newGroupDescription = groupDescpritionEditText.getText().toString();
            if(newGroup.equals("")){
                createGroupBtn.setText("Create");
                createGroupBtn.setEnabled(true);
                Toast.makeText(ProjectCreateGroup.this, "Group name must at least one characters with no leading and trailing spaces.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            mDatabase.child("Group").addListenerForSingleValueEvent(new ValueEventListener(){

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot group : snapshot.getChildren()) {
                        if(group.getKey().equals(newGroup)){

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

    }
}