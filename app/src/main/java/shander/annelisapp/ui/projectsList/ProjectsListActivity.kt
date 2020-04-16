package shander.annelisapp.ui.projectsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import shander.annelisapp.R
import shander.annelisapp.databinding.ActivityProjectsListBinding

class ProjectsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProjectsListBinding
    private lateinit var viewModel: ListActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_projects_list)
        binding.projectsList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        viewModel = ViewModelProvider(this).get(ListActivityVM::class.java)
        binding.viewModel = viewModel
        binding.projectsList.adapter = viewModel.projectsAdapter
    }
}
