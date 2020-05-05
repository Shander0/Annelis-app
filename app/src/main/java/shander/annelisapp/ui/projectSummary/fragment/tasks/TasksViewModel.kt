package shander.annelisapp.ui.projectSummary.fragment.tasks

import android.util.Log
import androidx.annotation.IntDef
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import shander.annelisapp.room.db.ProjectsDatabase
import shander.annelisapp.room.entity.tasks.TaskFirstLevel
import shander.annelisapp.room.entity.tasks.TaskFirstWithNestedList
import shander.annelisapp.room.entity.tasks.TaskSecondLevel
import shander.annelisapp.room.entity.tasks.TaskThirdLevel
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.FIRST
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.SECOND
import shander.annelisapp.ui.projectSummary.fragment.tasks.TaskRowModel.Companion.THIRD

class TasksViewModel : ViewModel(), TasksAdapter.TaskClickListener {
    private var subscription: CompositeDisposable = CompositeDisposable()
    private var projectId: Int = -1
    private var tasksList: List<TaskFirstWithNestedList>? = null
    private val db = ProjectsDatabase.getDatabase()
    val adapter: TasksAdapter = TasksAdapter(this)
    private lateinit var listener: TaskListener

    companion object {
        @IntDef(INNER_IN_FIRST, INNER_IN_SECOND, INSERT, CLOCK, REMOVE)
        @Retention(AnnotationRetention.SOURCE)
        annotation class EventType

        const val INNER_IN_FIRST = 1
        const val INNER_IN_SECOND = 2
        const val INSERT = 3
        const val CLOCK = 4
        const val REMOVE = 5
        const val INIT = 6
    }

    fun init(id: Int, listener: TaskListener) {
        projectId = id
        subscription.add(db.taskFirstDao().getTasksByProject(projectId)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isEmpty()) {
                    listener.taskEvent(INIT, 0, 0)
                } else {
                    tasksList = it
                    adapter.updateTasks(tasksList!!)
                    listener.taskEvent(INIT, 1, 1)
                }
            })
        this.listener = listener
    }

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }

    override fun taskAddClick(taskId: Int, taskLevel: Int) {
        listener.taskEvent(INSERT, taskId, taskLevel)
    }

    override fun taskInnerAddClick(taskId: Int, level: Int) {
        listener.taskEvent(if (level == FIRST) INNER_IN_FIRST else INNER_IN_SECOND, taskId, level)
    }

    override fun taskClockClick(taskId: Int, level: Int) {
        listener.taskEvent(CLOCK, taskId, level)
    }

    override fun taskRemoveClick(taskId: Int, level: Int) {
        listener.taskEvent(REMOVE, taskId, level)
    }

    override fun taskChecked(taskId: Int, checked: Boolean, level: Int) {
        val endDate = if (checked) System.currentTimeMillis() else 0
        when (level) {
            FIRST -> {
                db.taskFirstDao()
                    .getFirstTaskById(taskId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { it ->
                        it.taskFirstLevel!!.firstTaskEndedDate = endDate
                        db.taskFirstDao().update(it.taskFirstLevel!!).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                        it.secondsList.forEach { second ->
                            second.taskSecondLevel!!.secondTaskEndedDate = endDate
                            db.taskSecondDao().update(second.taskSecondLevel!!)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe()
                            second.thirdsList.forEach { third ->
                                third.thirdTaskEndedDate = endDate
                                db.taskThirdDao().update(third).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe()
                            }
                        }
                    }
            }
            SECOND -> {
                db.taskSecondDao()
                    .getSecondTaskById(taskId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { second ->
                        second.taskSecondLevel!!.secondTaskEndedDate = endDate
                        db.taskSecondDao().update(second.taskSecondLevel!!)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                        second.thirdsList.forEach { third ->
                            third.thirdTaskEndedDate = endDate
                            db.taskThirdDao().update(third).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe()
                        }
                    }
            }
            THIRD -> {
                db.taskThirdDao()
                    .getThirdTaskById(taskId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { third ->
                        third.thirdTaskEndedDate = endDate
                        db.taskThirdDao().update(third).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                    }
            }

        }
    }

    fun dlgAddConfirmed(level: Int, parentID: Int, name: String) {
        when (level) {
            FIRST -> {
                db.taskFirstDao()
                    .insert(TaskFirstLevel(projectId, name, System.currentTimeMillis(), 0, 0))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
            SECOND -> {
                db.taskSecondDao()
                    .insert(TaskSecondLevel(parentID, name, System.currentTimeMillis(), 0, 0))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
            THIRD -> {
                db.taskThirdDao()
                    .insert(TaskThirdLevel(parentID, name, System.currentTimeMillis(), 0, 0))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }

        }
    }

    fun dlgDeleteConfirmed(level: Int, id: Int) {
        when (level) {
            FIRST -> {
                db.taskFirstDao()
                    .deleteById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
            SECOND -> {
                db.taskSecondDao()
                    .deleteById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
            THIRD -> {
                db.taskThirdDao()
                    .deleteById(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }

        }
    }

    interface TaskListener {
        fun taskEvent(@EventType type: Int, taskId: Int, level: Int)
    }
}
