package shander.annelisapp.room.entity.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import shander.annelisapp.room.entity.tasks.TaskSecondLevel

@Entity(foreignKeys = [
    ForeignKey(
        entity = TaskSecondLevel::class,
        parentColumns = ["secondTaskId"],
        childColumns = ["parentSecondTaskId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )
])
data class TaskThirdLevel(
    @ColumnInfo(index = true)
    val parentSecondTaskId: Int,
    val thirdTaskDescription: String,
    val thirdTaskStartDate:Long,
    val thirdTaskPlannedDuration:Long,
    val thirdTaskEndedDate:Long
) {
    @PrimaryKey(autoGenerate = true)
    var thirdTaskId = 0
}