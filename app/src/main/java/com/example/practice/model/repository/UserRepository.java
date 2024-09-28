package com.example.practice.model.repository;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.example.practice.model.database.AppDatabase;
import com.example.practice.model.models.UserData;
import com.example.practice.model.models.UsersResponse;
import com.example.practice.model.network.ApiService;
import com.example.practice.model.network.RetrofitClient;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class UserRepository {
    private final String TAG = "UserRepository";
    private final AppDatabase db;  // Reference to Room Database
    private final ApiService apiService;  // Retrofit service
    private final ExecutorService executorService;

    public UserRepository(Context context) {
        this.db = Room.databaseBuilder(context, AppDatabase.class, "user-database").build();
        this.apiService = RetrofitClient.getApiService();   // Initialize Retrofit API service
        this.executorService = Executors.newSingleThreadExecutor(); // Executor for background operations
    }

    // Fetch data from API
    public void fetchUsersFromAPI(int page, Callback<List<UserData>> callback) {
        Call<UsersResponse> call = apiService.getUsers(page);  // Call API
        call.enqueue(new retrofit2.Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(response.body().getUserData());
                } else {
                    callback.onError(new Exception("Failed to fetch users"));
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                callback.onError(new Exception("Network error: " + t.getMessage()));
            }
        });
    }

    // Store fetched users in the local database
    public void storeUsersInDB(List<UserData> users, Callback<Void> callback) {
        executorService.execute(() -> {
            try {
                db.userDao().insertUsers(users);  // Insert users in DB
                callback.onResult(null);  // Success callback
            } catch (Exception e) {
                callback.onError(new Exception("UserRepository - storeUsersInDB - Failed to store users: " + e.getMessage()));
            }
        });
    }

    // Fetch users from the local database
    public void getUsersFromDB(Callback<List<UserData>> callback) {
        executorService.execute(() -> {
            try {
                List<UserData> userList = db.userDao().getAllUsers();  // Retrieve all users from the database
                callback.onResult(userList);
            } catch (Exception e) {
                callback.onError(new Exception("UserRepository - getUsersFromDB - Failed to get users: " + e.getMessage()));
            }
        });
    }

    public interface Callback<T> {
        void onResult(T result);
        void onError(Exception e);
    }
}

