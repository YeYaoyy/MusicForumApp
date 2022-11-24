package edu.northeastern.numad22fa_team23;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad22fa_team23.model.Moment;

public class MomentsActivity extends AppCompatActivity{

    MomentsAdapter adapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);
        List<Moment> list = new ArrayList<>();
        list = getData();
        fab = (FloatingActionButton) findViewById(R.id.fab_addMoment);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMoment);
        adapter = new MomentsAdapter(list, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MomentsActivity.this));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddMomentDialog();
            }
        });
    }

    private void showAddMomentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MomentsActivity.this);
        final View dialogView = getLayoutInflater().inflate(R.layout.add_moment_dialog, null);
        builder.setTitle("Add new moment");
        builder.setView(dialogView);
        Button addMoment = (Button)dialogView.findViewById(R.id.button_addMoment);
        addMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add new moment
            }
        });
        Button cancelAddMoment = (Button)dialogView.findViewById(R.id.button_cancelAddMoment);
        cancelAddMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        builder.setCancelable(true);
        AlertDialog alertDialog = builder.create();
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

    private List<Moment> getData() {
        List<Moment> list = new ArrayList<>();
        //TODO: get specified group's all moment data from firebase
        return list;
    }
}