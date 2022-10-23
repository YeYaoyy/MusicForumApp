package edu.northeastern.numad22fa_team23;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.northeastern.numad22fa_team23.model.IDataHolder;
import edu.northeastern.numad22fa_team23.model.Responses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AtYourService extends AppCompatActivity {
    private static final String TAG = "RetrofitActivity";
    private Retrofit retrofit;
    private IDataHolder api;
    private List<Responses.Places> place;
    PlaceServiceAdapter adapter;
    PlaceServiceAdapter.ClickListener listener;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);
        place = new ArrayList<>();


        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.zippopotam.us/")
//                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(IDataHolder.class);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        listener = new PlaceServiceAdapter.ClickListener() {
            @Override
            public void click(int index){
            }
        };
        getResponse();



        /*
        adapter = new PlaceServiceAdapter(place, getApplication(),listener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AtYourService.this));

         */
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    // Sample data for RecyclerView
    /*
    private List<Responses.Places> getData()
    {
        List<Responses.Places> list = new ArrayList<>();
        list.add(new examData("First Exam",
                "May 23, 2015",
                "Best Of Luck"));
        list.add(new examData("Second Exam",
                "June 09, 2015",
                "b of l"));
        list.add(new examData("My Test Exam",
                "April 27, 2017",
                "This is testing exam .."));

        return list;
    }

     */


    private void getResponse() {
        Call<Responses> call = api.getResponse("us","ma", "boston");

        call.enqueue(new Callback<Responses>() {

            @Override
            public void onResponse(Call<Responses> call, retrofit2.Response<Responses> response) {

                if(!response.isSuccessful()){
                    System.out.println("Call failed!" + response.code());
                    return;
                }
                Responses res = response.body();
                place = res.getPlace();
                adapter = new PlaceServiceAdapter(place, getApplication(),listener);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AtYourService.this));
                System.out.println(res.getAbb() + " " + res.getCountry());
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                System.out.println("error");
            }
        });
        //return place;
    }

//    private void getResponses() {
////        Call<List<Response>> call = api.getResponse("90210");
//        Call<List<PostModel_demo>> call = api.getPost();
//        call.enqueue(new Callback<List<PostModel_demo>>() {
//            @Override
//            public void onResponse(Call<List<PostModel_demo>> call, retrofit2.Response<List<PostModel_demo>> response) {
//
//                if(!response.isSuccessful()){
//                    System.out.println("Call failed!" + response.code());
//                    return;
//                }
//                List<PostModel_demo> res = response.body();
//                for(PostModel_demo i : res) {
//                    StringBuffer str = new StringBuffer();
//                    str.append(i.getId());
//                    System.out.println(str);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<PostModel_demo>> call, Throwable t) {
//                System.out.println("error");
//            }
//        });
//    }

    // Get requests Example
    private void getPosts(){
        // to execute the call
        Call<Responses> call = api.getPostModels();
        //call.execute() runs on the current thread, which is main at the momement. This will crash
        // use Retrofit's method enque. This will automaically push the network call to background thread
        call.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                //This gets called when atleast the call reaches a server and there was a response BUT 404 or any legitimate error code from the server, also calls this
                // check response code is between 200-300 and API was found
                //JSONArray js = Json.parseArray(response.getString().toString());
                if(!response.isSuccessful()){
                    Log.d(TAG, "Call failed!" + response.code());
                    return;
                }

                Log.d(TAG, "Call Successed!");
                Responses postModels = response.body();
//                for(Responses post : postModels){
//                    StringBuffer  str = new StringBuffer();
//                    str.append(post.getAbb());
//                    Log.d(TAG, str.toString());
//
//
//                }
                System.out.println(postModels.getPostCode() + " " + postModels.getCountry());
            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                // this gets called when url is wrong and therefore calls can't be made OR processing the request goes wrong.
                Log.d(TAG, "Call failed!" + t.getMessage());
            }
        });
    }

}
