package shander.annelisapp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects_table")
data class Project(
    @ColumnInfo(index = true)
    val projectName: String,
    val projectAvatar: String,
    val projectDescription: String,
    val projectFinished: Int
) {
    @PrimaryKey(autoGenerate = true)
    var projectId = 0
}