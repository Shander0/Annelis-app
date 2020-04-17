package shander.annelisapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity (
    foreignKeys = [
        ForeignKey(
            entity = Project::class,
            parentColumns = ["projectId"],
            childColumns = ["parentProjectId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Photo(
    @ColumnInfo(index = true)
    val parentProjectId: Int,
    val photoUri: String
) {
    @PrimaryKey(autoGenerate = true)
    var photoId = 0
}