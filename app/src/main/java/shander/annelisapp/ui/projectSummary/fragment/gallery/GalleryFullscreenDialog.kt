package shander.annelisapp.ui.projectSummary.fragment.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import shander.annelisapp.R
import shander.annelisapp.databinding.GalleryFullscreenFragmentBinding
import shander.annelisapp.room.db.ProjectsDatabase

class GalleryFullscreenDialog: DialogFragment() {

    private var objectsMap: MutableMap<Int, String> = mutableMapOf()
    private var startPosition = 0
    private var currentPosition = 0
    private lateinit var adapter: GalleryFullscreenAdapter

    companion object {

        const val TAG = "gallery_fullscreen_dlg"

        const val KEY_POSITION = "position"
        const val KEY_PROJECT_ID = "project"

        @JvmStatic
        fun newInstance(projectId: Int, position: Int) =
            GalleryFullscreenDialog().apply {
                arguments = Bundle().apply {
                    putInt(KEY_POSITION, position)
                    putInt(KEY_PROJECT_ID, projectId)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startPosition = arguments!!.getInt(KEY_POSITION)

    }

    override fun getTheme(): Int {
        return R.style.FullscreenDialogTheme
    }

    //TODO fix last photo deletion issue

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = GalleryFullscreenFragmentBinding.inflate(inflater, container, false)
        ProjectsDatabase.getDatabase().photoDao()
            .getPhotosByProject(arguments!!.getInt(KEY_PROJECT_ID))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isEmpty()) {
                    dismiss()
                } else {
                    objectsMap.clear()
                    Log.wtf("INSERTED", ""+it.size)
                    it.forEach { photo ->
                        objectsMap[photo.photoId] = photo.photoUri
                    }
                    adapter = GalleryFullscreenAdapter(it)
                    binding.fullscreenPager.adapter = adapter
                    binding.fullscreenPager.currentItem = startPosition
                }
            }
        binding.fullscreenPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding.tvCounter.text = "${position+1}/${objectsMap.size}"
                currentPosition = position
            }

            override fun onPageSelected(position: Int) {
                binding.tvCounter.text = "${position+1}/${objectsMap.size}"
                currentPosition = position
            }

        })
        binding.tvDelete.setOnClickListener {
            Log.wtf("DEL CALL", "" + objectsMap.size + " " + currentPosition)
            if (objectsMap.size > currentPosition) {
                ProjectsDatabase.getDatabase().photoDao()
                    .deleteById(objectsMap.keys.elementAt(currentPosition))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        }
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

}