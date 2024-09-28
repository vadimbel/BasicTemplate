package com.example.practice.model.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersResponse {

    private int page;
    @SerializedName("per_page")
    private int perPage;
    private int total;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("data")
    private List<UserData> userData;
    private Support support;

    public int getPage() {
        return page;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<UserData> getUserData() {
        return userData;
    }

    public Support getSupport() {
        return support;
    }
}
