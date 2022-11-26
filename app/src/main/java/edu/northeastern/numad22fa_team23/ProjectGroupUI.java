package edu.northeastern.numad22fa_team23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ProjectGroupUI extends AppCompatActivity {
    private Button addGroupButton;
    private ProjectGroupFragment groupFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_group_fragment);
        addGroupButton = findViewById(R.id.addGroupbtn);
        addGroupButton.setOnClickListener(v -> {add();});

        addDetailFragment(savedInstanceState);

    }

    public void add(){
        Intent intent = new Intent(this, ProjectCreateGroup.class);
        startActivity(intent);
    }

    public void addDetailFragment(Bundle savedInstanceState){
        if(savedInstanceState == null){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if(fm.findFragmentById(R.id.fragment) == null){
                groupFragment = new ProjectGroupFragment();
            }else{
                groupFragment = (ProjectGroupFragment) fm.findFragmentById(R.id.fragment);
                ft.remove(groupFragment);
                fm.popBackStack();
                ft.commit();
                ft = fm.beginTransaction();
            }
            ft.add(R.id.fragment, groupFragment);
            ft.commit();
        }
    }

//    private void loadFragment(Fragment fragment) {
//// create a FragmentManager
//        FragmentManager fm = getFragmentManager();
//// create a FragmentTransaction to begin the transaction and replace the Fragment
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//// replace the FrameLayout with new Fragment
//        fragmentTransaction.replace(R.id.fragment, fragment);
//        fragmentTransaction.commit(); // save the changes
//    }
}