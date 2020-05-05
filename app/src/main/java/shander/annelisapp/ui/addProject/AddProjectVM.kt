package shander.annelisapp.ui.addProject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import shander.annelisapp.room.db.ProjectsDatabase
import shander.annelisapp.room.entity.Project

class AddProjectVM: ViewModel() {

    val projectName = MutableLiveData("")
    val projectDescription = MutableLiveData("")
    private val projectAvatar= MutableLiveData("")
    private lateinit var listener: ProjectCreateListener

    fun getProjectAvatar(): MutableLiveData<String> {
        return projectAvatar
    }

    fun projectAvatarSelected(uri: String) {
        projectAvatar.value = uri
    }

    fun setListener(listener: ProjectCreateListener) {
        this.listener = listener
    }

    fun apply() {
        ProjectsDatabase.getDatabase().projectsDao().insert(
            Project(projectName.value!!, projectAvatar.value!!, projectDescription.value!!, 0)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(object : SingleObserver<Long> {
                override fun onSuccess(t: Long) {
                    listener.created(t)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    listener.error(e)
                }

            })
    }

    interface ProjectCreateListener{
        fun created(id: Long)
        fun error(e: Throwable)
    }

}