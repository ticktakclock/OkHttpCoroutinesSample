package com.github.ticktakclock.okhttpcoroutines.service

import com.github.ticktakclock.okhttpcoroutines.service.model.User

class UserRepository(private val gitHubService: GitHubService) {

    suspend fun getUser(userId: String): User =
        gitHubService.getUser(userId)

}