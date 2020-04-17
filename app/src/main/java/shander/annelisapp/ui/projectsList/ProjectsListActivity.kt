package shander.annelisapp.ui.projectsList

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import shander.annelisapp.R
import shander.annelisapp.databinding.ActivityProjectsListBinding
import shander.annelisapp.room.db.ProjectsDatabase
import shander.annelisapp.ui.addProject.AddProjectActivity

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

        binding.fab.setOnClickListener {
            val i = Intent(this, AddProjectActivity::class.java)
            startActivity(i)
        }
    }
}
