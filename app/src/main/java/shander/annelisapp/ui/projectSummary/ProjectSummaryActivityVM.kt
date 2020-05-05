package shander.annelisapp.ui.projectSummary

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import shander.annelisapp.room.db.ProjectsDatabase
import shander.annelisapp.room.entity.ProjectWithAllNested

class ProjectSummaryActivityVM: ViewModel() {
    private lateinit var subscription: CompositeDisposable
    private var projectId = -1
    private val projectName = MutableLiveData<String>()
    private val projectDescription= MutableLiveData<String>()
    private val projectAvatar= MutableLiveData<String>()
    private var project: ProjectWithAllNested? = null
    private val db: ProjectsDatabase = ProjectsDatabase.getDatabase()

    fun setId(id: Int) {
        projectId = id
        subscription = CompositeDisposable()
        if (projectId > -1) {
            subscription.add(db.projectsDao().getProject(projectId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe {it ->
                    project = it
                        projectName.value = it.project!!.projectName
                        projectDescription.value = it.project!!.projectDescription
                        projectAvatar.value = it.project!!.projectAvatar
                })
        }
    }

    fun projectAvatarSelected(uri: String) {
        projectAvatar.value = uri
        project?.project?.projectAvatar = uri
        db.projectsDao().update(project?.project!!).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getProjectName(): MutableLiveData<String> {
        return projectName
    }

    fun getProjectAvatar(): MutableLiveData<String> {
        return projectAvatar
    }

    fun getProjectDesc(): MutableLiveData<String> {
        return projectDescription
    }

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }
}