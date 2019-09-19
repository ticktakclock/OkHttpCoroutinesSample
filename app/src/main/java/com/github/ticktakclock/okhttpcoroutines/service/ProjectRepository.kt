package com.github.ticktakclock.okhttpcoroutines.service

import com.github.ticktakclock.okhttpcoroutines.service.model.Project

class ProjectRepository(private val gitHubService: GitHubService) {


    suspend fun getProjects(userId: String): List<Project> {
        return gitHubService.getProjects(userId)
    }
}