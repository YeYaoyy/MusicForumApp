package edu.northeastern.numad22fa_team23;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

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
import java.util.Collections;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProjectGroupFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private GridView gridView;
    private ArrayList<String> groupNames;
    private int groupCount = 1;
    private Button addGroupButton;
    private String username;

    public static ProjectGroupFragment newInstance() {
        return new ProjectGroupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //find this fragment
        View view = inflater.inflate(R.layout.activity_project_group_fragment, container, false);
        //a gridview to show all groups
        gridView = view.findViewById(R.id.gridview);
        //button to add a new group
        addGroupButton = view.findViewById(R.id.addGroupbtn);
        //all added groups
        groupNames = new ArrayList<>();

        //data
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = mAuth.getCurrentUser();
        final String uid = curUser.getUid();

        //adapter
        final GroupUIAdapter groupAdapter = new GroupUIAdapter();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Project_Users");

                mDatabase.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {

//                            Log.d("firebase", String.valueOf(task.getResult().getValue()));

                            //get corresponding username from database to interact among different activities
                            HashMap<String, Object> map = (HashMap<String, Object>) task.getResult().getValue();
                            for (String s : map.keySet()) {
                                HashMap<String, String> pu = (HashMap<String, String>) map.get(s);
                                String userid = pu.get("uid");
                                if (userid.equals(uid)) {
                                    username = pu.get("username");
                                    break;
                                }
                            }

                            // Passing the groupname to ProjectGroupChatMoment,
                            // so that ProjectGroupChatMoment can display its corresponding information.
                            Intent intent = new Intent(getActivity(), ProjectGroupChatMoment.class);
                            Bundle b = new Bundle();
                            b.putString("groupname", groupNames.get(position));
                            b.putString("username", username);
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    }
                });


            }
        });

        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectCreateGroup.class);
                startActivity(intent);
            }
        });


        // Retrieve an instance of database using reference the location
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Groups");
        //A ValueEventListener listens for data changes to a specific location in database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupNames.clear();
                groupCount = 1;
                //add group names to the arraylist
                for (DataSnapshot child : snapshot.getChildren()) {
                    if (!child.getKey().equals("DefaultGroup")) {
                        groupNames.add(child.getKey());
                    }
                }
                Collections.sort(groupNames);
                gridView.setAdapter(groupAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    //set circleImageView for every group
    private class GroupUIAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return groupNames.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View groupGridView = getLayoutInflater().inflate(R.layout.group_circle, null);
            TextView name = groupGridView.findViewById(R.id.groupname);
            //put group name in corresponding name
            name.setText(groupNames.get(position));
            //circleImageView
            CircleImageView profileCircleImageView = groupGridView.findViewById(R.id.profileCircleImageView);
            //group image
            String group_icon_x = "group_icon_" + (position%20+3)%20;
            profileCircleImageView.setImageResource(getResources().getIdentifier(group_icon_x, "drawable", "edu.northeastern.numad22fa_team23"));
            return groupGridView;
        }
    }
}