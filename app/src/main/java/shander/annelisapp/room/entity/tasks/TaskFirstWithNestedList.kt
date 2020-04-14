package shander.annelisapp.room.entity.tasks

import androidx.room.Embedded
import androidx.room.Relation

data class TaskFirstWithNestedList(
    @Embedded
    var taskFirstLevel: TaskFirstLevel,

    @Relation(
        parentColumn = "firstTaskId",
        entityColumn = "parentFirstTaskId",
        entity = TaskSecondLevel::class
    )
    var secondsList: List<TaskSecondWithThirdsList> = ArrayList()
)