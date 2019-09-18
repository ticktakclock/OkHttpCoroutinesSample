package com.github.ticktakclock.okhttpcoroutines.view.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.ticktakclock.okhttpcoroutines.R
import com.github.ticktakclock.okhttpcoroutines.databinding.ProjectsItemBinding
import com.github.ticktakclock.okhttpcoroutines.service.model.Project


class ProjectsAdapter(private var projects: List<Project>) :
    RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ProjectsItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.projects_item, parent, false)
        return ProjectsViewHolder(binding)
    }

    override fun getItemCount(): Int = projects.size

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        if (position >= projects.size) return
        val project = projects[position]
        holder.bind(project)
    }

    fun update(items: List<Project>) {
        projects = items
        notifyDataSetChanged()
    }

    class ProjectsViewHolder(private val binding: ProjectsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Project) {
            binding.project = item
        }
    }
}