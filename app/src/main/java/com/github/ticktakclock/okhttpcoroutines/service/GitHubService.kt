package com.github.ticktakclock.okhttpcoroutines.service

import com.github.ticktakclock.okhttpcoroutines.service.model.Project
import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubService {

    @GET("/users/{userId}/repos")
    suspend fun getProjects(@Path(value = "userId") userId: String): List<Project>

}