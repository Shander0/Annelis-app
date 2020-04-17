package shander.annelisapp.room.entity.tasks

import androidx.room.Embedded
import androidx.room.Relation

data class TaskSecondWithThirdsList(
    @Embedded
    var taskSecondLevel: TaskSecondLevel? = null,

    @Relation(
        parentColumn = "secondTaskId",
        entityColumn = "parentSecondTaskId"
    )
    var thirdsList: List<TaskThirdLevel> = ArrayList()
)