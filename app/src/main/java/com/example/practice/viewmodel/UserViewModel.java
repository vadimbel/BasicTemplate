package com.example.practice.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.practice.model.models.UserData;
import com.example.practice.model.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private final String TAG = "UserViewModel";
    private final UserRepository userRepository;
    private final MutableLiveData<List<UserData>> userListLiveData = new MutableLiveData<>();  // MutableLiveData for UI updates

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<List<UserData>> getUserListLiveData() {
        return userListLiveData;
    }

    public void fetchDataFromAPI(int page) {
        userRepository.fetchUsersFromAPI(page, new UserRepository.Callback<List<UserData>>() {
            @Override
            public void onResult(List<UserData> result) {
                Log.d(TAG, "Data fetched successfully from API");
                // Step 1: Store data in the database
                userRepository.storeUsersInDB(result, new UserRepository.Callback<Void>() {
                    @Override
                    public void onResult(Void storedResult) {
                        Log.d(TAG, "Data stored in DB successfully");
                        // Step 2: Read from the database after storing
                        fetchFromDB();
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "Failed to store data in DB: " + e.getMessage());
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "ERROR: UserViewModel - fetchDataFromAPI - " + e.getMessage());
            }
        });
    }

    // Fetch data from the database and update the MutableLiveData
    private void fetchFromDB() {
        userRepository.getUsersFromDB(new UserRepository.Callback<List<UserData>>() {
            @Override
            public void onResult(List<UserData> dbResult) {
                userListLiveData.postValue(dbResult);  // Update MutableLiveData
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Failed to fetch data from DB: " + e.getMessage());
            }
        });
    }
}


