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
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.northeastern.numad22fa_team23.model.Moment;

public class MomentsActivity extends AppCompatActivity{

    MomentsAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private String groupName;
    private String userName;
    FirebaseAuth auth;
    private List<HashMap<String, String>> list;

    private static String SERVER_KEY = "key=AAAAYVMPBrg:APA91bFcn3zDzceEIocqvzaKlPRBN1dKIdThGYeYK443c1A96HrITFGU8J3-VIj1u5ymAHbau-AsH3rpEsrUcN6E7FpCpz9XJjPGFuXDBx33-N_o-I2JLgepGt3qfMudTuCKGnWLKVy3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);

//        Intent i = getIntent();
//        Bundle bundle = i.getBundleExtra("bundle");
//        userName = bundle.getString("userName");
//        groupName = bundle.getString("groupName");

        userName = "testUser1";
        groupName = "testGroup1";

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
        AlertDialog.Builder builder = new AlertDialog.Builder(MomentsActivity.this);
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
                    Toast.makeText(MomentsActivity.this, "Please fill all EditText", Toast.LENGTH_LONG).show();
                    return;
                }
                mDatabase.child("Groups").child(groupName).child("moments").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if (!task.isSuccessful()) {
                            Log.e("firebase", "moments add connection failed", task.getException());
                        }
                        else {
                            list = (List<HashMap<String, String>>)task.getResult().getValue();

                            List<Moment> newList = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                Moment m = new Moment();
                                m.setGroupId(list.get(i).get("groupId"));
                                m.setMusicName(list.get(i).get("musicName"));
                                m.setThought(list.get(i).get("thought"));
                                m.setUserName(list.get(i).get("userName"));
                                newList.add(m);
                            }
                            Moment newMoment = new Moment();
                            newMoment.setGroupId(groupName);
                            newMoment.setMusicName(musicName.getText().toString());
                            newMoment.setThought(thought.getText().toString());
                            newMoment.setUserName(userName);
                            newList.add(newMoment);
                            mDatabase.child("Groups").child(groupName).child("moments").setValue(newList);
                            Toast.makeText(MomentsActivity.this, "Post New Moment successfully!", Toast.LENGTH_LONG).show();
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
                    list = (List<HashMap<String, String>>)task.getResult().getValue();
                    List<Moment> newList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        Moment m = new Moment();
                        m.setGroupId(list.get(i).get("groupId"));
                        m.setMusicName(list.get(i).get("musicName"));
                        m.setThought(list.get(i).get("thought"));
                        m.setUserName(list.get(i).get("userName"));
                        newList.add(m);
                    }
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMoment);
                    adapter = new MomentsAdapter(newList, getApplication());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MomentsActivity.this));
                }
            }
        });
    }

}