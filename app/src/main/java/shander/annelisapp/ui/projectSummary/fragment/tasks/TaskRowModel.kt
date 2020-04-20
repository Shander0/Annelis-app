package shander.annelisapp.ui.projectSummary.fragment.tasks

import androidx.annotation.IntDef
import shander.annelisapp.room.entity.tasks.TaskFirstWithNestedList
import shander.annelisapp.room.entity.tasks.TaskSecondWithThirdsList
import shander.annelisapp.room.entity.tasks.TaskThirdLevel

class TaskRowModel {

    companion object {
        @IntDef(FIRST, SECOND, THIRD)
        @Retention(AnnotationRetention.SOURCE)
        annotation class RowLevel

        const val FIRST = 1
        const val SECOND = 2
        const val THIRD = 3
    }

    @RowLevel var level: Int

    var id: Int

    var isExpanded: Boolean = false

    lateinit var taskFirst: TaskFirstWithNestedList

    lateinit var taskSecond: TaskSecondWithThirdsList

    lateinit var taskThird: TaskThirdLevel

    constructor(@RowLevel level: Int, task: TaskFirstWithNestedList)
    {
        this.level = level
        this.taskFirst = task
        this.id = task.taskFirstLevel!!.firstTaskId
    }

    constructor(@RowLevel level: Int, task: TaskSecondWithThirdsList)
    {
        this.level = level
        this.taskSecond = task
        this.id = task.taskSecondLevel!!.secondTaskId
    }

    constructor(@RowLevel level: Int, task: TaskThirdLevel)
    {
        this.level = level
        this.taskThird = task
        this.id = task.thirdTaskId
    }

}