package shander.annelisapp.ui.projectSummary.fragment.tasks

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import shander.annelisapp.room.db.ProjectsDatabase
import shander.annelisapp.room.entity.tasks.TaskFirstWithNestedList

class TasksViewModel : ViewModel(), TasksAdapter.TaskClickListener {
    private var subscription: CompositeDisposable = CompositeDisposable()
    private var projectId: Int = -1
    private var tasksList: List<TaskFirstWithNestedList>? = null
    private val db = ProjectsDatabase.getDatabase()
    val adapter: TasksAdapter = TasksAdapter(this)

    fun setId(id: Int) {
        projectId = id
        subscription.add(db.taskFirstDao().getTasksByProject(projectId)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                tasksList = it
                adapter.updateTasks(tasksList!!)
            })
    }

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }

    override fun taskAddClick(taskId: Int, taskLevel: Int) {
        TODO("Not yet implemented")
    }

    override fun taskInnerAddClick(taskId: Int) {
        TODO("Not yet implemented")
    }

    override fun taskClockClick(taskId: Int) {
        TODO("Not yet implemented")
    }

    override fun taskRemoveClick(taskId: Int) {
        TODO("Not yet implemented")
    }

    override fun taskChecked(taskId: Int, checked: Boolean) {
        TODO("Not yet implemented")
    }
}
