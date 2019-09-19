package com.github.ticktakclock.okhttpcoroutines.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.ticktakclock.okhttpcoroutines.R
import com.github.ticktakclock.okhttpcoroutines.databinding.UserFragmentBinding
import com.github.ticktakclock.okhttpcoroutines.service.UserRepository
import com.github.ticktakclock.okhttpcoroutines.viewmodel.UserViewModel

class UserFragment(private val userRepository: UserRepository, private val userId: String) :
    Fragment() {

    companion object {
        fun newInstance(userRepository: UserRepository, userId: String) = UserFragment(
            userRepository, userId
        )
    }

    private lateinit var viewModel: UserViewModel
    private lateinit var binding: UserFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.user_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, UserViewModel.Factory(userRepository))
            .get(UserViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.fetch(userId)
            .observe(this, Observer { user ->
                binding.user = user
            })
        viewModel.userImage.observe(this, Observer { drawable ->
            binding.userImage = drawable
        })
    }

}
