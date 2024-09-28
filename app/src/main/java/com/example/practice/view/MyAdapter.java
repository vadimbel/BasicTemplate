package com.example.practice.view;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.practice.R;
import com.example.practice.model.models.UserData;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;    // Context of the activity where the adapter is used
    List<UserData> users;   // List of user items to be displayed
    private final String TAG = "MyAdapter";

    /**
     * Constructor for the MyAdapter class.
     *
     * @param context The context of the activity where the adapter is used.
     */
    // Constructor for the MyAdapter class with only Context
    public MyAdapter(Context context) {
        this.context = context;
        this.users = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserData currentUser = users.get(position);

        // Bind the user data to the views in the ViewHolder
        holder.userId.setText(String.valueOf(currentUser.getId()));
        holder.firstNameView.setText(currentUser.getFirstName());
        holder.lastNameView.setText(currentUser.getLastName());
        holder.emailView.setText(currentUser.getEmail());

        // Reset the text color to default (assuming black is the default)
        holder.firstNameView.setTextColor(Color.BLACK);
        holder.lastNameView.setTextColor(Color.BLACK);
        holder.emailView.setTextColor(Color.BLACK);

        // Load the user's avatar image using Glide
        if (!currentUser.getAvatar().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(currentUser.getAvatar())
                    .into(holder.imageView);

        } else {
            // Set a default avatar image if the user does not have an avatar
            holder.imageView.setImageResource(R.drawable.ic_launcher_foreground); // Replace with your default avatar
        }

        // Set click listener for the Update button of the specific user in 'position'
        holder.btnUpdate.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            Log.d(TAG, "Update button clicked at position: " + currentPosition);
            Log.d(TAG, "User ID: " + currentUser.getId() + ", Name: " + currentUser.getFirstName() + " " + currentUser.getLastName());
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    // Method to update data dynamically
    public void updateData(List<UserData> newItems) {
        users.addAll(newItems);
        notifyDataSetChanged();  // Notify adapter that data has changed
    }

}
