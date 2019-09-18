package com.github.ticktakclock.okhttpcoroutines.viewmodel

import androidx.lifecycle.*
import com.github.ticktakclock.okhttpcoroutines.service.ProjectRepository
import com.github.ticktakclock.okhttpcoroutines.service.model.Project
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProjectListViewModel(private val repository: ProjectRepository) : ViewModel() {
    private val _projects: MutableLiveData<List<Project>> = MutableLiveData()
    val projects: LiveData<List<Project>>
        get() = _projects

    init {
        viewModelScope.launch(Dispatchers.Main) {
            runBlocking {
                try {
                    val response = repository.getProjects("ticktakclock")
                    _projects.value = response
                } catch (e: Exception) {
                    e.printStackTrace()
                    _projects.value = emptyList()
                }

            }
        }
    }

    class Factory(private val repository: ProjectRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ProjectListViewModel(repository) as T
        }
    }

}
