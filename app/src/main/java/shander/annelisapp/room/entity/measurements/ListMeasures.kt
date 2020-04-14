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
    @PrimaryKey (autoGenerate = true)
    val listId: Int,
    val name: String,
    val description: String,
    val isDefault: Int,
    @ColumnInfo(index = true)
    val parentProjectId: Int
)