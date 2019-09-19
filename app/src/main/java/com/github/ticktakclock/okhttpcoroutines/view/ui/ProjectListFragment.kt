package com.github.ticktakclock.okhttpcoroutines.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.ticktakclock.okhttpcoroutines.R
import com.github.ticktakclock.okhttpcoroutines.databinding.RepoListFragmentBinding
import com.github.ticktakclock.okhttpcoroutines.service.ProjectRepository
import com.github.ticktakclock.okhttpcoroutines.service.model.Project
import com.github.ticktakclock.okhttpcoroutines.viewmodel.ProjectListViewModel


class ProjectListFragment(private val projectRepository: ProjectRepository) : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(projectRepository: ProjectRepository) =
            ProjectListFragment(projectRepository)
    }

    private var _fragmentInteractionListener: FragmentInteractionListener? = null

    var fragmentInteractionListener: FragmentInteractionListener?
        get() = _fragmentInteractionListener
        set(value) {
            _fragmentInteractionListener = value
        }

    private lateinit var viewModel: ProjectListViewModel
    private lateinit var binding: RepoListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.repo_list_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, ProjectListViewModel.Factory(projectRepository))
            .get(ProjectListViewModel::class.java)
        binding.viewModel = viewModel
        val adapter = object : ProjectsAdapter(emptyList()) {
            override fun onProjectClicked(project: Project) {
                super.onProjectClicked(project)
                val userName = project.owner?.login
                if (userName != null) {
                    fragmentInteractionListener?.moveToUser(userName)
                }
            }
        }

        binding.recyclerView.adapter = adapter

        binding.floatingActionButton.setOnClickListener { v ->
            observeViewModel(viewModel)
        }
        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: ProjectListViewModel) {
        viewModel.projects.observe(this, Observer {
            if (it.isEmpty()) {
                Toast.makeText(activity, "データがありません", Toast.LENGTH_LONG).show()
            }
            val adapter = binding.recyclerView.adapter as? ProjectsAdapter
            adapter?.update(it)
        })
    }

    interface FragmentInteractionListener {
        fun moveToUser(userName: String)
    }
}
