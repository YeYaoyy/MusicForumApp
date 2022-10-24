package edu.northeastern.numad22fa_team23;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad22fa_team23.model.IDataHolder;
import edu.northeastern.numad22fa_team23.model.Responses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityOuput extends AppCompatActivity {
    private Retrofit retrofit;
    private IDataHolder api;
    private List<Responses.Places> place;
    PlaceServiceAdapter adapter;
    PlaceServiceAdapter.ClickListener listener;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();

        place = new ArrayList<>();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.zippopotam.us/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(IDataHolder.class);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listener = new PlaceServiceAdapter.ClickListener() {
            @Override
            public void click(int index) {
            }
        };

        if (data.getInt("flag") == 0) {
            getResponseForCity(data.getString("state"), data.getString("city"));
        } else {
            getResponseForPostCode(data.getString("postCode"));
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you sure you want to dismiss?")
                .setPositiveButton("Yes",
                        (dialog, id) -> {
                            super.onBackPressed();
                        })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                .create();
        builder.show();

    }


    private void getResponseForCity(String state, String city) {

        Call<Responses> call = api.getResponseForCity(state, city);

        call.enqueue(new Callback<Responses>() {

            @Override
            public void onResponse(Call<Responses> call, retrofit2.Response<Responses> response) {

                if (!response.isSuccessful()) {
//                    System.out.println("Call failed!" + response.code());
                    Toast.makeText(ActivityOuput.this, "Invailid input!", Toast.LENGTH_LONG).show();
                    return;
                }
//                progressBar.setVisibility(View.GONE);
                Responses res = response.body();
                place = res.getPlace();
                adapter = new PlaceServiceAdapter(place, getApplication(), listener);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ActivityOuput.this));
                System.out.println(res.getAbb() + " " + res.getCountry());
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                System.out.println("error");
            }
        });

//        progressBar.setVisibility(View.GONE);
        //return place;
    }

    private void getResponseForPostCode(String postCode) {
//        progressBar.setVisibility(View.GONE);
        Call<Responses> call = api.getResponseForPostCode(postCode);

        call.enqueue(new Callback<Responses>() {

            @Override
            public void onResponse(Call<Responses> call, retrofit2.Response<Responses> response) {

                if (!response.isSuccessful()) {

                    Toast.makeText(ActivityOuput.this, "Invailid input!", Toast.LENGTH_LONG).show();

                    return;
                }
//                progressBar.setVisibility(View.GONE);
                Responses res = response.body();
                place = res.getPlace();
                adapter = new PlaceServiceAdapter(place, getApplication(), listener);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ActivityOuput.this));
                System.out.println(res.getAbb() + " " + res.getCountry());
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                System.out.println("error");
            }
        });
        //return place;
    }
}
