package shander.annelisapp.room.entity.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import shander.annelisapp.room.entity.Project

@Entity(foreignKeys = [
    ForeignKey(
        entity = Project::class,
        parentColumns = ["projectId"],
        childColumns = ["parentProjectId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )
])
data class TaskFirstLevel(
    @ColumnInfo(index = true)
    val parentProjectId: Int,
    val firstTaskDescription: String,
    val firstTaskStartDate:Long,
    val firstTaskPlannedDuration:Long,
    val firstTaskEndedDate:Long
) {
    @PrimaryKey(autoGenerate = true)
    var firstTaskId = 0
}