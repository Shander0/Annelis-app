package shander.annelisapp.ui.projectSummary

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import shander.annelisapp.R
import shander.annelisapp.databinding.ActivityStartListBinding

class ProjectSummaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartListBinding
    private lateinit var viewModel: ProjectSummaryActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProjectSummaryActivityVM::class.java)
        val id = intent.getIntExtra("id", -1)
        viewModel.setId(id)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start_list)
        binding.lifecycleOwner = this
        binding.viewPager.adapter = SectionsPagerAdapter(
            this,
            supportFragmentManager,
            id
        )
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.viewModel = viewModel
    }

}