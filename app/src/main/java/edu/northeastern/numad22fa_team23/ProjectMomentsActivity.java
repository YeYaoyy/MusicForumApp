package edu.northeastern.numad22fa_team23;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.northeastern.numad22fa_team23.model.ProjectComment;
import edu.northeastern.numad22fa_team23.model.ProjectMoment;

public class ProjectMomentsActivity extends AppCompatActivity{

    ProjectMomentsAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private String groupName;
    private String userName;
    FirebaseAuth auth;
    private List<HashMap<String, Object>> list;

    private static String SERVER_KEY = "key=AAAAYVMPBrg:APA91bFcn3zDzceEIocqvzaKlPRBN1dKIdThGYeYK443c1A96HrITFGU8J3-VIj1u5ymAHbau-AsH3rpEsrUcN6E7FpCpz9XJjPGFuXDBx33-N_o-I2JLgepGt3qfMudTuCKGnWLKVy3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);

        Intent i = getIntent();
        Bundle data=i.getExtras();
        userName = data.getString("username");
        groupName = data.getString("groupname");

//        userName = "testUser1";
//        groupName = "testGroup1";

        mDatabase = FirebaseDatabase.getInstance().getReference();

        getData();
        fab = (FloatingActionButton) findViewById(R.id.fab_addMoment);
        /*
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMoment);
        adapter = new MomentsAdapter(list, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MomentsActivity.this));
         */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddMomentDialog();
            }
        });
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showAddMomentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProjectMomentsActivity.this);
        final View dialogView = getLayoutInflater().inflate(R.layout.add_moment_dialog, null);
        builder.setTitle("Add new moment");
        builder.setView(dialogView);
        Button addMoment = (Button)dialogView.findViewById(R.id.button_addMoment);
        EditText musicName = (EditText) dialogView.findViewById(R.id.musicDialogName);
        EditText thought = (EditText) dialogView.findViewById(R.id.thoughtContent);
        AlertDialog alertDialog = builder.create();
        addMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable test = musicName.getText();
                if ("".equals(musicName.getText().toString()) || "".equals(thought.getText().toString())) {
                    Toast.makeText(ProjectMomentsActivity.this, "Please fill all EditText", Toast.LENGTH_LONG).show();
                    return;
                }
                mDatabase.child("Groups").child(groupName).child("moments").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if (!task.isSuccessful()) {
                            Log.e("firebase", "moments add connection failed", task.getException());
                        }
                        else {
                            list = (List<HashMap<String, Object>>)task.getResult().getValue();

                            List<ProjectMoment> newList = new ArrayList<>();
                            if (list == null) {
                                list = new ArrayList<>();
                            }
                            for (int i = 0; i < list.size(); i++) {
                                ProjectMoment m = new ProjectMoment();
                                m.setGroupId((String) list.get(i).get("groupId"));
                                m.setMusicName((String) list.get(i).get("musicName"));
                                m.setThought((String) list.get(i).get("thought"));
                                m.setUserName((String) list.get(i).get("userName"));
                                m.setMomentId(((Long)list.get(i).get("momentId")).intValue());
                                List<HashMap<String, Object>> comm = (List<HashMap<String, Object>>)list.get(i).get("commentList");
                                if (comm == null) {
                                    comm = new ArrayList<>();
                                }
                                List<ProjectComment> newComm = new ArrayList<>();
                                for (int j = 0; j < comm.size(); j++) {
                                    ProjectComment c = new ProjectComment();
                                    c.setMomentId(((Long)comm.get(j).get("momentId")).intValue());
                                    c.setUserName((String) comm.get(j).get("userName"));
                                    c.setContent((String) comm.get(j).get("content"));
                                    newComm.add(c);
                                }
                                m.setCommentList(newComm);
                                newList.add(m);
                            }
                            ProjectMoment newMoment = new ProjectMoment();
                            newMoment.setMomentId(newList.size());
                            newMoment.setGroupId(groupName);
                            newMoment.setMusicName(musicName.getText().toString());
                            newMoment.setThought(thought.getText().toString());
                            newMoment.setUserName(userName);
                            newList.add(newMoment);
                            mDatabase.child("Groups").child(groupName).child("moments").setValue(newList);
                            Toast.makeText(ProjectMomentsActivity.this, "Post New Moment successfully!", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }
                    }
                });
                //getData();
            }
        });
        Button cancelAddMoment = (Button)dialogView.findViewById(R.id.button_cancelAddMoment);
        cancelAddMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        builder.setCancelable(true);
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you sure you want to dismiss?")
                .setPositiveButton("Yes",
                        (dialog, id) -> {
                            finish();
                        })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                .create();
        builder.show();
    }

    private void getData() {
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Groups").child(groupName).child("moments").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (!task.isSuccessful()) {
                    Log.e("firebase", "moments connection failed", task.getException());
                }
                else {
                    list = (List<HashMap<String, Object>>)task.getResult().getValue();
                    //List<HashMap<String, Object>> comm;
                    List<ProjectMoment> newList = new ArrayList<>();
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    for (int i = 0; i < list.size(); i++) {
                        ProjectMoment m = new ProjectMoment();
                        m.setGroupId((String)list.get(i).get("groupId"));
                        m.setMusicName((String)list.get(i).get("musicName"));
                        m.setThought((String)list.get(i).get("thought"));
                        m.setUserName((String)list.get(i).get("userName"));
                        m.setMomentId(((Long)list.get(i).get("momentId")).intValue());
                        List<HashMap<String, Object>> comm = (List<HashMap<String, Object>>)list.get(i).get("commentList");
                        if (comm == null) {
                            comm = new ArrayList<>();
                        }
                        List<ProjectComment> newComm = new ArrayList<>();
                        for (int j = 0; j < comm.size(); j++) {
                            ProjectComment c = new ProjectComment();
                            c.setMomentId(((Long)comm.get(j).get("momentId")).intValue());
                            c.setUserName((String) comm.get(j).get("userName"));
                            c.setContent((String) comm.get(j).get("content"));
                            newComm.add(c);
                        }
                        m.setCommentList(newComm);
                        newList.add(m);
                    }
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMoment);
                    adapter = new ProjectMomentsAdapter(newList, getApplication());
                    adapter.setOnItemClickListener(new ProjectMomentsAdapter.OnItemClickListener() {
                        @Override
                        public void onButtonClicked(View view, int position, int momentId) {
                            showAddCommentDialog(momentId);
                            Toast.makeText(ProjectMomentsActivity.this, position + ":" + momentId, Toast.LENGTH_LONG).show();
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ProjectMomentsActivity.this));
                }
            }
        });
    }
    private void showAddCommentDialog(int momentId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProjectMomentsActivity.this);
        final View dialogView = getLayoutInflater().inflate(R.layout.add_comment_dialog, null);
        builder.setTitle("Add new comment");
        builder.setView(dialogView);
        Button addComment = (Button)dialogView.findViewById(R.id.button_addComment);
        EditText comment = (EditText) dialogView.findViewById(R.id.commentContent);
        AlertDialog alertDialog = builder.create();
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable test = comment.getText();
                if ("".equals(comment.getText().toString())) {
                    Toast.makeText(ProjectMomentsActivity.this, "Please fill EditText", Toast.LENGTH_LONG).show();
                    return;
                }
                mDatabase.child("Groups").child(groupName).child("moments").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if (!task.isSuccessful()) {
                            Log.e("firebase", "comments add connection failed", task.getException());
                        }
                        else {
                            list = (List<HashMap<String, Object>>)task.getResult().getValue();
                            List<HashMap<String, Object>> comm;
                            List<ProjectMoment> newList = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                ProjectMoment m = new ProjectMoment();
                                m.setGroupId((String) list.get(i).get("groupId"));
                                m.setMusicName((String) list.get(i).get("musicName"));
                                m.setThought((String) list.get(i).get("thought"));
                                m.setUserName((String) list.get(i).get("userName"));
                                m.setMomentId(((Long)list.get(i).get("momentId")).intValue());
                                comm = (List<HashMap<String, Object>>)list.get(i).get("commentList");
                                if (comm == null) {
                                    comm = new ArrayList<>();
                                }
                                List<ProjectComment> newComm = new ArrayList<>();
                                for (int j = 0; j < comm.size(); j++) {
                                    ProjectComment c = new ProjectComment();
                                    c.setMomentId(((Long)comm.get(j).get("momentId")).intValue());
                                    c.setUserName((String) comm.get(j).get("userName"));
                                    c.setContent((String) comm.get(j).get("content"));
                                    newComm.add(c);
                                }
                                if (m.getMomentId() == momentId) {
                                    ProjectComment c = new ProjectComment();
                                    c.setMomentId(momentId);
                                    c.setUserName(userName);
                                    c.setContent(comment.getText().toString());
                                    newComm.add(c);
                                }
                                m.setCommentList(newComm);
                                newList.add(m);
                            }
                            mDatabase.child("Groups").child(groupName).child("moments").setValue(newList);
                            Toast.makeText(ProjectMomentsActivity.this, "Post New Comment successfully!", Toast.LENGTH_LONG).show();
                            alertDialog.dismiss();
                        }
                    }
                });
                //getData();
            }
        });
        Button cancelAddMoment = (Button)dialogView.findViewById(R.id.button_cancelAddComment);
        cancelAddMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        builder.setCancelable(true);
        alertDialog.show();
    }
}