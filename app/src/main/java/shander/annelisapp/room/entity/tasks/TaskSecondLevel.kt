package shander.annelisapp.room.entity.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = TaskFirstLevel::class,
        parentColumns = ["firstTaskId"],
        childColumns = ["parentFirstTaskId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )
])
data class TaskSecondLevel(
    @ColumnInfo(index = true)
    val parentFirstTaskId: Int,
    val secondTaskDescription: String,
    val secondTaskStartDate:Long,
    val secondTaskPlannedDuration:Long,
    val secondTaskEndedDate:Long
) {
    @PrimaryKey(autoGenerate = true)
    var secondTaskId = 0
}