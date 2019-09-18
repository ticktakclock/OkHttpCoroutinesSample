package com.github.ticktakclock.okhttpcoroutines.service

import com.github.ticktakclock.okhttpcoroutines.service.model.Project
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProjectRepository {
    companion object {
        private const val HTTPS_API_GITHUB_URL = "https://api.github.com/"
    }

    private val gitHubService: GitHubService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(HTTPS_API_GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        gitHubService = retrofit.create(GitHubService::class.java)
    }

    suspend fun getProjects(userId: String): List<Project> {
        return gitHubService.getProjects(userId)
    }
}