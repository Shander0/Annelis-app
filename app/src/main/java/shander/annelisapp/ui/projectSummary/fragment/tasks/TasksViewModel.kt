package shander.annelisapp.ui.projectSummary.fragment.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import shander.annelisapp.room.db.ProjectsDatabase
import shander.annelisapp.room.entity.tasks.TaskFirstWithNestedList

class TasksViewModel : ViewModel() {
    private var subscription: CompositeDisposable = CompositeDisposable()
    private var projectId: Int = -1
    private var tasksList: List<TaskFirstWithNestedList>? = null
    private val db = ProjectsDatabase.getDatabase()

    fun setId(id: Int) {
        projectId = id
        subscription.add(db.taskFirstDao().getTasksByProject(projectId)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ tasksList = it }))
    }

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }
}
