package shander.annelisapp.ui.projectSummary.fragment.tasks

import androidx.annotation.IntDef
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import shander.annelisapp.room.db.ProjectsDatabase
import shander.annelisapp.room.entity.tasks.TaskFirstWithNestedList
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.FIRST

class TasksViewModel : ViewModel(), TasksAdapter.TaskClickListener {
    private var subscription: CompositeDisposable = CompositeDisposable()
    private var projectId: Int = -1
    private var tasksList: List<TaskFirstWithNestedList>? = null
    private val db = ProjectsDatabase.getDatabase()
    val adapter: TasksAdapter = TasksAdapter(this)
    private var listener: TaskListener? = null

    companion object {
        @IntDef(INNER_IN_FIRST, INNER_IN_SECOND, INSERT, CLOCK, REMOVE)
        @Retention(AnnotationRetention.SOURCE)
        annotation class EventType

        const val INNER_IN_FIRST = 1
        const val INNER_IN_SECOND = 2
        const val INSERT = 3
        const val CLOCK = 4
        const val REMOVE = 5
    }

    fun init(id: Int, listener: TaskListener) {
        projectId = id
        subscription.add(db.taskFirstDao().getTasksByProject(projectId)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                tasksList = it
                adapter.updateTasks(tasksList!!)
            })
        this.listener = listener
    }

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }

    override fun taskAddClick(taskId: Int, taskLevel: Int) {
        listener?.taskEvent(INSERT, taskId, taskLevel)
    }

    override fun taskInnerAddClick(taskId: Int, level: Int) {
        listener?.taskEvent(if (level == FIRST) INNER_IN_FIRST else INNER_IN_SECOND, taskId, level)
    }

    override fun taskClockClick(taskId: Int, level: Int) {
        listener?.taskEvent(CLOCK, taskId, level)
    }

    override fun taskRemoveClick(taskId: Int, level: Int) {
        listener?.taskEvent(REMOVE, taskId, level)
    }

    override fun taskChecked(taskId: Int, checked: Boolean, level: Int) {
        TODO("Not yet implemented")
    }

    interface TaskListener {
        fun taskEvent(@EventType type: Int, taskId: Int, level: Int)
    }
}
