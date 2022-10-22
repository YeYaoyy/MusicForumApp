package edu.northeastern.numad22fa_team23.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IDataHolder {

    @GET("{country}/{zipCode}/{city}")
    Call<Responses> getResponse(@Path("country") String country,
            @Path("zipCode") String zipCode, @Path("city") String city);

    @GET("us/90210")
    Call<Responses>  getPostModels();

    @GET("posts")   // "post/" is the relative Url of your api. We define base Url at a common place later
    Call<List<PostModel_demo>>  getPost();
}
