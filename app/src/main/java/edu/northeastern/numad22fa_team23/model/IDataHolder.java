package edu.northeastern.numad22fa_team23.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IDataHolder {

    @GET("us/{state}/{city}")
    Call<Responses> getResponseForCity(
            @Path("state") String state, @Path("city") String city);

    @GET("us/{postCode}")
    Call<Responses>  getResponseForPostCode(@Path("postCode") String postCode);

}
