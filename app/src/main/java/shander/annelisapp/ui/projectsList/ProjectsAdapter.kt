package shander.annelisapp.ui.projectsList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import shander.annelisapp.R
import shander.annelisapp.databinding.ItemProjectsListBinding
import shander.annelisapp.innerEntity.SimpleProjectItem

class ProjectsAdapter(projectClickListener: ((Int) -> Unit)): RecyclerView.Adapter<ProjectsAdapter.ProjectsViewHolder>() {

    private lateinit var projects: List<SimpleProjectItem>
    private var listener = projectClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        val binding: ItemProjectsListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_projects_list, parent, false)
        return ProjectsViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return if(::projects.isInitialized) projects.size else 0
    }

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        holder.bind(projects[position])
    }

    fun updateProjectsList(projectItems: List<SimpleProjectItem>) {
        projects = projectItems
        notifyDataSetChanged()
    }

    class ProjectsViewHolder(private val binding: ItemProjectsListBinding,
                             private val listener: ((Int) -> Unit))
        : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = ProjectsListVM()
        fun bind(item: SimpleProjectItem) {
            viewModel.bind(item)
            binding.viewModel = viewModel
            binding.root.setOnClickListener {
                listener.invoke(item.projectId)
            }
        }
    }
}