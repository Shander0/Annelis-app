package shander.annelisapp.ui.projectSummary.fragment.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import shander.annelisapp.room.entity.Photo

class GalleryItemVM: ViewModel() {
    val photoUri = MutableLiveData<String>()
    var photoId: Int = 0

    fun bind(photo: Photo) {
        photoUri.value = photo.photoUri
        photoId = photo.photoId
    }
}