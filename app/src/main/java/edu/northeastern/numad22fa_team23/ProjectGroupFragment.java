package edu.northeastern.numad22fa_team23;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProjectGroupFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private GridView gridView;
    private ArrayList<String> groupNames;
    private int groupCount = 1;
    private Button addGroupButton;
    private ProjectGroupsViewModel mViewModel;

    public static ProjectGroupFragment newInstance() {
        return new ProjectGroupFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_project_group_fragment, container, false);
        gridView = view.findViewById(R.id.gridview);
        addGroupButton = view.findViewById(R.id.addGroupbtn);
        groupNames = new ArrayList<>();

        final GroupUIAdapter groupAdapter = new GroupUIAdapter();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ProjectGroupChatMoment.class);
                // Passing the groupname to GroupInfoActivity,
                // so that ProjectGroupInfo can display its corresponding information.
                intent.putExtra("groupname", groupNames.get(position));
                startActivity(intent);
            }
        });

        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProjectCreateGroup.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser curUser = mAuth.getCurrentUser();
        //？uid
        final String uid = curUser.getUid();

        // Retrieve an instance of database using reference the location
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Groups");
        //A ValueEventListener listens for data changes to a specific location in database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupNames.clear();
                groupCount = 1;
                //add group names to the arraylist
                for (DataSnapshot child : snapshot.getChildren()) {
                    //?? defaultgroup
                    if (!child.getKey().equals("DefaultGroup")) {
                        groupNames.add(child.getKey());
                    }
//                    groupNames.add(child.getKey());
                }
                Collections.sort(groupNames);
                //
                gridView.setAdapter(groupAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(ProjectGroupsViewModel::class.java);
        // TODO: Use the ViewModel

    }

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

            CircleImageView profileCircleImageView = groupGridView.findViewById(R.id.profileCircleImageView);
            String group_icon_x = "group_icon_" + (position%20+3)%20;
            profileCircleImageView.setImageResource(getResources().getIdentifier(group_icon_x, "drawable", "edu.northeastern.numad22fa_team23"));
            return groupGridView;
        }
    }
}