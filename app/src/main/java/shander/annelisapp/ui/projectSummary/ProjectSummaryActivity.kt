package shander.annelisapp.ui.projectSummary

import android.app.Activity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import shander.annelisapp.App
import shander.annelisapp.R
import shander.annelisapp.databinding.ActivityStartListBinding
import java.io.File

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
        binding.projectAvatar.setOnClickListener {
            ImagePicker.with(this)
                .compress(2048)
                .maxResultSize(1080, 1080)
                .crop()
                .saveDir(App.instance.filesDir)
                .start { resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            viewModel.projectAvatarSelected(data?.data.toString())
                        }
                        ImagePicker.RESULT_ERROR -> {
                            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }

}