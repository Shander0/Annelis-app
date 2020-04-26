package shander.annelisapp.ui.projectSummary.fragment.gallery

import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import shander.annelisapp.room.db.ProjectsDatabase
import shander.annelisapp.room.entity.Photo

class GalleryViewModel : ViewModel(), GalleryAdapter.PhotoClickListener {
    private val db = ProjectsDatabase.getDatabase()
    private var projectId: Int = -1
    private lateinit var listener: PictureClickListener
    val adapter = GalleryAdapter(this)
    private var subscription: CompositeDisposable = CompositeDisposable()


    fun init(id: Int, listener: PictureClickListener) {
        this.listener = listener
        projectId = id
        subscription.add(db.photoDao().getPhotosByProject(id)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.updateGallery(it)
            })
    }

    fun imagePicked(uri: String) {
        subscription.add(db.photoDao().insert(Photo(projectId, uri))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    interface PictureClickListener{
        fun pictureClick(picId: Int, position: Int)
    }

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }

    override fun photoClick(id: Int, position: Int) {
        listener.pictureClick(id, position)
    }
}
