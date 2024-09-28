package com.example.practice.model.network;
import com.example.practice.model.models.UserData;
import com.example.practice.model.models.UsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface defining the API endpoints for interacting with the server.
 * This interface is used by Retrofit to generate the necessary code for making HTTP requests.
 */
public interface ApiService {

    @GET("api/users")
    Call<UsersResponse> getUsers(@Query("page") int page);

}
