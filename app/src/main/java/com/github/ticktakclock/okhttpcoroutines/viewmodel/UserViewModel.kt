package com.github.ticktakclock.okhttpcoroutines.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.*
import coil.Coil
import coil.api.get
import com.github.ticktakclock.okhttpcoroutines.service.UserRepository
import com.github.ticktakclock.okhttpcoroutines.service.model.User
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User>
        get() = _user

    private var _userImage: MutableLiveData<Drawable> = MutableLiveData()
    val userImage: LiveData<Drawable>
        get() = _userImage

    fun fetch(userId: String): LiveData<User> {
        viewModelScope.launch {
            try {
                val user = userRepository.getUser(userId)
                _user.value = user
                imageResource(user.avatar_url)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return user
    }

    suspend fun imageResource(url: String?) {
        url ?: return
        val drawable = Coil.get(url, {})
        _userImage.value = drawable
    }

    class Factory(private val repository: UserRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewModel(repository) as T
        }
    }
}
