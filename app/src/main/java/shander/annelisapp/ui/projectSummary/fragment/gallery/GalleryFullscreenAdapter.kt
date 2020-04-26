package shander.annelisapp.ui.projectSummary.fragment.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import shander.annelisapp.databinding.GalleryItemFullscreenBinding
import shander.annelisapp.room.entity.Photo
import java.io.File

class GalleryFullscreenAdapter (private val photos: List<Photo>): PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = GalleryItemFullscreenBinding.inflate(
            LayoutInflater.from(container.context),container, false
        )
        Glide.with(binding.imageView.context)
            .load(File(photos[position].photoUri).path)
            .into(binding.imageView)

        container.addView(binding.root)
        return binding.root
    }

    override fun getCount(): Int {
        return photos.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container?.removeView(`object` as LinearLayout)
    }

}