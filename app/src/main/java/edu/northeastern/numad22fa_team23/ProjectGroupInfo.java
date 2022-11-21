package edu.northeastern.numad22fa_team23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ProjectGroupInfo extends AppCompatActivity {
    private TextView groupNameTitle;
    private TextView groupDescription;
    private TextView createChat;
    private TextView createMoment;
    private Button showMore;
    private RecyclerView chatRecyclerView;
    private RecyclerView momentRecyclerView;
    private FirebaseAuth mAuth;
    String uid;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proj_group_info);
        groupNameTitle = findViewById(R.id.groupname2);
        groupDescription = findViewById(R.id.groupDescription);
        createChat = findViewById(R.id.createChat);
        createMoment = findViewById(R.id.createMoment);
        showMore = findViewById(R.id.grpbtShowmore);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currUser = mAuth.getCurrentUser();
        uid = currUser.getUid();

        //Get the group name passed by GroupsFragment
        Intent intent = getIntent();
        final String groupname = intent.getStringExtra("groupname");
        //Fill event list
        chatRecyclerView = findViewById(R.id.chatList);
        momentRecyclerView = findViewById(R.id.momentList);
    }





}