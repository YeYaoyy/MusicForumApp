package edu.northeastern.numad22fa_team23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProjectGroupChatMoment extends AppCompatActivity {
    private Button createChat;
    private Button createMoment;
    private DatabaseReference mDatabase;
    private TextView groupDescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_group_chat_moment);

        //data
        Intent i = getIntent();
        Bundle data = i.getExtras();
        final String groupname = i.getStringExtra("groupname");

        //textview to show description of this group
        groupDescription = findViewById(R.id.textView_groupDescription_ui);

        //button to create a new chat in the group
        createChat = findViewById(R.id.createChatGBtn);
        createChat.setOnClickListener((v) ->{
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtras(data);
            startActivity(intent);
        });

        //button to create a new moment in the group
        createMoment = findViewById(R.id.createMomentGBtn);
        createMoment.setOnClickListener((v) ->{
            Intent intent = new Intent(this, ProjectMomentsActivity.class);
            intent.putExtras(data);
            startActivity(intent);
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