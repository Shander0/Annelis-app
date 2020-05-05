package shander.annelisapp.ui.projectsList

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import shander.annelisapp.R
import shander.annelisapp.databinding.ItemProjectsListBinding
import shander.annelisapp.innerEntity.SimpleProjectItem

class ProjectsAdapter(val projectClickListener: ProjectClickListener): RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>() {

    private lateinit var projects: List<SimpleProjectItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        val binding: ItemProjectsListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_projects_list, parent, false)
        return ProjectsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if(::projects.isInitialized) projects.size else 0
    }

    fun getIdByPosition(position: Int): Int {
        return projects[position].projectId
    }

    fun getNameByPosition(position: Int): String {
        return projects[position].projectName
    }

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        holder.bind(projects[position])
    }

    fun updateProjectsList(projectItems: List<SimpleProjectItem>) {
        projects = projectItems
        notifyDataSetChanged()
    }

    inner class ProjectsViewHolder(private val binding: ItemProjectsListBinding)
        : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ProjectsItemVM()
        fun bind(item: SimpleProjectItem) {
            viewModel.bind(item)
            binding.viewModel = viewModel
            binding.root.setOnClickListener {
                projectClickListener.onProjectClicked(item.projectId)
            }
        }
    }

    interface ProjectClickListener{
        fun onProjectClicked(id: Int)
    }
}