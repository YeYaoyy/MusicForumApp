package edu.northeastern.numad22fa_team23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.northeastern.numad22fa_team23.model.Moment;

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
    List<Moment> momentList;
    private DatabaseReference mDatabase;
    private DatabaseReference reference;


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

        //Get the group name passed by GroupsUI
        Intent intent = getIntent();
        final String groupname = intent.getStringExtra("groupname");
        //Fill chat and moment list
        chatRecyclerView = findViewById(R.id.chatList);
        momentRecyclerView = findViewById(R.id.momentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProjectGroupInfo.this);
        //set dividers in recyclerview
        chatRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration cdividerItemDecoration = new DividerItemDecoration(chatRecyclerView.getContext(),
                layoutManager.getOrientation());
        chatRecyclerView.addItemDecoration(cdividerItemDecoration);

        momentRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration mdividerItemDecoration = new DividerItemDecoration(momentRecyclerView.getContext(),
                layoutManager.getOrientation());
        chatRecyclerView.addItemDecoration(mdividerItemDecoration);

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

        chatList = new ArrayList<Chat>();
        momentList = new ArrayList<Moment>();
        assert groupname != null;
        reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                joined.clear();
                chatList.clear();
                momentList.clear();
                for(DataSnapshot sn: snapshot.getChildren()){
                    if(sn.getKey().equals("Users")){
                        for(DataSnapshot tk: sn.child(uid).child("Groups").child(groupname).getChildren()){
                            joined.add(tk.getKey());
                        }
                    }
//                    joined.add(sn.getKey());
                    if(sn.getKey().equals("Groups")){
                        for(DataSnapshot tk: sn.child(groupname).child("Chats").getChildren()){
                            Chat chat = tk.child("ChatInfo").getValue(Chat.class);
                            if(chat!=null){
                                chatList.add(chat);
                            }
                        }
                    }
                    if(sn.getKey().equals("Groups")){
                        for(DataSnapshot tk: sn.child(groupname).child("Moments").getChildren()){
                            Moment moment = tk.child("MomentInfo").getValue(Moment.class);
                            if(moment!=null){
                                momentList.add(moment);
                            }
                        }
                    }
                }
                //to restore the position before click
//                chatRecyclerView.getLayoutManager().onRestoreInstanceState(chatRecyclerView.getLayoutManager().onSaveInstanceState());
//                chatsAdapter = new EventsAdapter(context, mEvents, groupname, joined);
//                chatRecyclerView.setAdapter(chatsAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Set group name
        this.groupNameTitle.setText(groupname);
        // Retrieve an instance of database using reference the location
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