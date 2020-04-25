package shander.annelisapp.utils

import android.R
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.io.File


@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value?: View.VISIBLE})
    }
}

@BindingAdapter("loadedImage")
fun loadImage(view:ImageView, uri:MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && uri != null) {
        uri.observe(parentActivity, Observer { value ->
            Glide.with(view.context)
                .load(File(value).path)
                .apply(RequestOptions().placeholder(shander.annelisapp.R.drawable.ic_add))
                .apply(RequestOptions().transforms(CenterCrop(), RoundedCorners(20)))
                .into(view)})
    }
}

@BindingAdapter("resImage")
fun setResImage(view: ImageView, res: MutableLiveData<Int>) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    res.observe(parentActivity!!, Observer {
        Glide.with(view)
            .load(res)
            .into(view)
    })
}
