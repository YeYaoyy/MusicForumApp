package edu.northeastern.numad22fa_team23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProjectGroupUI extends AppCompatActivity {

    private Button addGroupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_group_fragment);


        addGroupButton = findViewById(R.id.addGroupbtn);
        addGroupButton.setOnClickListener(v -> {add();});
    }

    public void add(){
        Intent intent = new Intent(this, ProjectCreateGroup.class);
        startActivity(intent);
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