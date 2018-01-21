package com.example.androidstarter.data.api;

import com.example.androidstarter.data.models.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by samvedana on 17/1/18.
 */

public interface ZestService {
    /*
    //Ideally call should include user id since server will have data for n users not just one
    //but for initial simplicity, let's just keep it simple

    @GET("users/{id}/tasks")
    Call<List<Task>> getUserTasks(
            @Path("id") long id
    );
    */

    @GET("tasks")
    Call<List<Task>> getTasks();
}
