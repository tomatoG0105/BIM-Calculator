package com.example.bmicalculator;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Interface for the Retrofit API call
public interface BmiApiService {
    @GET("calculateBMI")
    Call<BmiResponse> calculateBMI(@Query("height") int height, @Query("weight") int weight);
}
