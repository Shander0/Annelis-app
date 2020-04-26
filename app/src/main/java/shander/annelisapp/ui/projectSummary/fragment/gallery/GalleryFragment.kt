package shander.annelisapp.ui.projectSummary.fragment.gallery

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.github.dhaval2404.imagepicker.ImagePicker

import shander.annelisapp.R
import shander.annelisapp.databinding.GalleryFragmentBinding
import shander.annelisapp.room.entity.Photo

class GalleryFragment : Fragment(), GalleryViewModel.PictureClickListener {

    companion object {
        fun newInstance(id: Int) =
            GalleryFragment()
                .apply {
                    arguments = Bundle().apply {
                        putInt("id", id)
                    }
                }
    }

    private lateinit var viewModel: GalleryViewModel
    private lateinit var binding: GalleryFragmentBinding
    private var projectId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
        projectId = arguments?.getInt("id", -1)!!
        viewModel.init(projectId, this)
        binding = GalleryFragmentBinding.inflate(inflater, container, false)
        binding.galleryRecycler.apply {
            // Displaying data in a Grid design
            layoutManager = GridLayoutManager(activity, 3)
            adapter = viewModel.adapter
        }
        return binding.root
    }

    override fun pictureClick(picId: Int, position: Int) {
        if (picId == -1) {
            ImagePicker.with(this)
                .compress(2048)
                .maxResultSize(1080, 1080)
                .crop()
                .start { resultCode, data ->
                    when (resultCode) {
                        Activity.RESULT_OK -> {
                            viewModel.imagePicked(data?.data.toString())
                        }
                        ImagePicker.RESULT_ERROR -> {
                            Toast.makeText(activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        } else {
            val transaction = childFragmentManager.beginTransaction()
            val previous = childFragmentManager.findFragmentByTag(GalleryFullscreenDialog.TAG)
            if (previous != null) {
                transaction.remove(previous)
            }
            transaction.addToBackStack(null)

            val dialogFragment = GalleryFullscreenDialog.newInstance(projectId, position)
            dialogFragment.show(transaction, GalleryFullscreenDialog.TAG)
        }
    }
}
