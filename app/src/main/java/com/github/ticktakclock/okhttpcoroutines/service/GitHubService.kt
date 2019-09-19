package com.github.ticktakclock.okhttpcoroutines.service

import com.github.ticktakclock.okhttpcoroutines.service.model.Project
import com.github.ticktakclock.okhttpcoroutines.service.model.User
import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubService {

    @GET("/users/{userId}")
    suspend fun getUser(@Path(value = "userId") userId: String): User

    @GET("/users/{userId}/repos")
    suspend fun getProjects(@Path(value = "userId") userId: String): List<Project>
}