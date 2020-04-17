package shander.annelisapp.room.entity.measurements

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
data class ListMeasures(
    val name: String,
    val description: String,
    @ColumnInfo(index = true)
    val parentProjectId: Int
) {
    @PrimaryKey (autoGenerate = true)
    var listId = 0
}