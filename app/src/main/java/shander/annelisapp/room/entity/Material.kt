package shander.annelisapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = Project::class,
        parentColumns = ["projectId"],
        childColumns = ["parentProjectId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )
])
data class Material (
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val name: String,
    val price: Double,
    val amount: Double,
    val exists: Int,
    val description: String,
    @ColumnInfo(index = true)
    val parentProjectId: Int
)