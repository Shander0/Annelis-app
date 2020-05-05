package shander.annelisapp.ui.addProject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.ImagePicker.Companion.with
import shander.annelisapp.App
import shander.annelisapp.R
import shander.annelisapp.databinding.ActivityAddProjectBinding
import shander.annelisapp.ui.projectSummary.ProjectSummaryActivity

class AddProjectActivity : AppCompatActivity(), AddProjectVM.ProjectCreateListener {

    private lateinit var binding: ActivityAddProjectBinding
    private lateinit var viewModel: AddProjectVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_project)
        viewModel = ViewModelProvider(this).get(AddProjectVM::class.java)
        viewModel.setListener(this)
        binding.viewModel = viewModel
        binding.projectImage.setOnClickListener {
            with(this)
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
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        binding.tvApply.setOnClickListener {
            viewModel.apply()
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun created(id: Long) {
        val i = Intent(this, ProjectSummaryActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        i.putExtra("id", id.toInt())
        startActivity(i)
    }

    override fun error(e: Throwable) {
        Log.wtf("Creation ERROR", e.localizedMessage)
    }
}
