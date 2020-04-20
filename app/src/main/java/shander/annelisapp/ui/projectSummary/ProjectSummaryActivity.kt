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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start_list)
        binding.viewPager.adapter = SectionsPagerAdapter(
            this,
            supportFragmentManager
        )
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        viewModel = ViewModelProvider(this).get(ProjectSummaryActivityVM::class.java)
        viewModel.setId(intent.getIntExtra("id", -1))
    }

}