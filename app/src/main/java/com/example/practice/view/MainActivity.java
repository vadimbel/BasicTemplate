package com.example.practice.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.R;
import com.example.practice.model.models.UserData;
import com.example.practice.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private UserViewModel userViewModel;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Setup RecyclerView and Adapter without holding a separate list
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this);
        recyclerView.setAdapter(myAdapter);

        // Observe the LiveData from ViewModel and update the adapter when data changes
        userViewModel.getUserListLiveData().observe(this, users -> {
            if (users != null) {
                Log.d(TAG, "LiveData changed, updating RecyclerView");
                myAdapter.updateData(users);  // Update adapter with new data
            }
        });

        // Observe LiveData changes - print the data to log
        userViewModel.getUserListLiveData().observe(this, userList -> {
            if (userList != null) {
                Log.d(TAG, "Number of users in DB: " + userList.size());
                for (UserData user : userList) {
                    Log.d(TAG, "User ID: " + user.getId() + ", Name: " + user.getFirstName() + " " + user.getLastName());
                }
            } else {
                Log.d(TAG, "No users found in DB.");
            }
        });

        // Fetch data from API from 'page'
        userViewModel.fetchDataFromAPI(1);
    }


}



