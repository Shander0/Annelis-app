package shander.annelisapp.ui.projectSummary.fragment.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import shander.annelisapp.R
import shander.annelisapp.databinding.ItemPhotoBinding
import shander.annelisapp.room.entity.Photo


class GalleryAdapter(val listener: PhotoClickListener): RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private var data: MutableList<Photo> = mutableListOf()

    fun updateGallery(list: List<Photo>) {
        data.clear()
        list.forEach { data.add(it) }
        val lastElement = Photo(-1, "drawable://" + R.drawable.ic_add)
        lastElement.photoId = -1
        data.add(lastElement)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        return GalleryViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_photo, parent, false
            ))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class GalleryViewHolder(private val binding: ItemPhotoBinding):
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = GalleryItemVM()
        fun bind(photo: Photo) {
            viewModel.bind(photo)
            binding.viewModel = viewModel
            binding.itemPhoto.setOnClickListener{
                listener.photoClick(data[adapterPosition].photoId, adapterPosition)
            }
        }
    }

    interface PhotoClickListener {
        fun photoClick(id: Int, position: Int)
    }
}