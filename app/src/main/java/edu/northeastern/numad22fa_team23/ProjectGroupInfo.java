package edu.northeastern.numad22fa_team23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.northeastern.numad22fa_team23.model.ProjectMoment;

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
    Set<String> joined;
    List<Chat> chatList;
    List<ProjectMoment> momentList;
    private DatabaseReference mDatabase;
    private DatabaseReference reference;
    Context context;
    ChatListAdapter chatListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_proj_group_info);
        groupNameTitle = findViewById(R.id.groupname2);
        groupDescription = findViewById(R.id.groupDescription);
        createChat = findViewById(R.id.createChat);
        createMoment = findViewById(R.id.createMoment);
        showMore = findViewById(R.id.grpbtShowmore);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currUser = mAuth.getCurrentUser();
        uid = currUser.getUid();
        joined = new HashSet<>();

        //Get the group name passed by GroupsUI
        Intent intent = getIntent();
        final String groupname = intent.getStringExtra("groupname");

        chatList = new ArrayList<Chat>();
        momentList = new ArrayList<ProjectMoment>();

        chatList= (List<Chat>) getIntent().getExtras().get("Chats");
        momentList = (List<ProjectMoment>) getIntent().getExtras().get("Moments");


        //Fill chat and moment list
        chatRecyclerView = findViewById(R.id.chatList);
        momentRecyclerView = findViewById(R.id.momentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProjectGroupInfo.this);
        //set dividers in recyclerview
        chatRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration cdividerItemDecoration = new DividerItemDecoration(chatRecyclerView.getContext(),
                layoutManager.getOrientation());
        chatRecyclerView.addItemDecoration(cdividerItemDecoration);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(ProjectGroupInfo.this);
        momentRecyclerView.setLayoutManager(layoutManager2);
        DividerItemDecoration mdividerItemDecoration = new DividerItemDecoration(momentRecyclerView.getContext(),
                layoutManager.getOrientation());
        momentRecyclerView.addItemDecoration(mdividerItemDecoration);


        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showMore.getText().toString().equalsIgnoreCase("Show more..."))
                {
                    groupDescription.setMaxLines(Integer.MAX_VALUE);//your TextView
                    showMore.setText("Show less");
                }
                else
                {
                    groupDescription.setMaxLines(5);//your TextView
                    showMore.setText("Show more...");
                }
            }
        });

        createChat.setOnClickListener((v) ->{
//            Intent intent = new Intent();
//            intent.putExtra("groupName", groupname);
//            startActivity(intent);
        });

        createMoment.setOnClickListener((v) ->{
//            Intent intent = new Intent();
//            intent.putExtra("groupName", groupname);
//            startActivity(intent);
        });





//        assert groupname != null;
//        reference = FirebaseDatabase.getInstance().getReference();
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                joined.clear();
//                chatList.clear();
//                momentList.clear();
//                for(DataSnapshot sn: snapshot.getChildren()){
//                    if(sn.getKey().equals("Users")){
//                        for(DataSnapshot tk: sn.child(uid).child("Groups").child(groupname).getChildren()){
//                            joined.add(tk.getKey());
//                        }
//                    }
////                    joined.add(sn.getKey());
//                    if(sn.getKey().equals("Groups")){
//                        for(DataSnapshot tk: sn.child(groupname).child("Chats").getChildren()){
//                            Chat chat = tk.getValue(Chat.class);
//                            if(chat != null){
//                                chatList.add(chat);
//                            }
//                        }
//                    }
//                    if(sn.getKey().equals("Groups")){
//                        for(DataSnapshot tk: sn.child(groupname).child("Moments").getChildren()){
////                            Moment moment = tk.child("MomentInfo").getValue(Moment.class);
//                            Moment moment = tk.getValue(Moment.class);
//                            if(moment != null){
//                                momentList.add(moment);
//                            }
//                        }
//                    }
//                }
//                //to restore the position before click
//                chatRecyclerView.getLayoutManager().onRestoreInstanceState(chatRecyclerView.getLayoutManager().onSaveInstanceState());
//                chatListAdapter = new ChatListAdapter(context, chatList);
//                chatRecyclerView.setAdapter(chatListAdapter);
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


//        // Set group name
//        this.groupNameTitle.setText(groupname);
//        // Retrieve an instance of database using reference the location
//        mDatabase = FirebaseDatabase.getInstance().getReference("Groups").child(groupname);
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // Get description of this group from database
//                String description = dataSnapshot.child("Description").getValue().toString();
//                // Set description
//                groupDescription.setText(description);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }
}