package com.github.ticktakclock.okhttpcoroutines.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.ticktakclock.okhttpcoroutines.R
import com.github.ticktakclock.okhttpcoroutines.service.GitHubService
import com.github.ticktakclock.okhttpcoroutines.service.ProjectRepository
import com.github.ticktakclock.okhttpcoroutines.service.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), ProjectListFragment.FragmentInteractionListener {

    companion object {
        private const val HTTPS_API_GITHUB_URL = "https://api.github.com/"
    }

    private val gitHubService: GitHubService
    private val projectRepository: ProjectRepository
    private val userRepository: UserRepository


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(HTTPS_API_GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        gitHubService = retrofit.create(GitHubService::class.java)
        projectRepository = ProjectRepository(gitHubService)
        userRepository = UserRepository(gitHubService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        moveToProjects()
    }

    override fun moveToUser(userName: String) {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.addToBackStack(null)
        val fragment = UserFragment.newInstance(userRepository, userName)
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private fun moveToProjects() {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.addToBackStack(null)
        val fragment = ProjectListFragment.newInstance(projectRepository)
        fragment.fragmentInteractionListener = this
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}
