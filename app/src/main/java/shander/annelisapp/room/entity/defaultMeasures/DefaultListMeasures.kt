package shander.annelisapp.room.entity.defaultMeasures

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DefaultListMeasures(
    @PrimaryKey (autoGenerate = true)
    val listId: Int,
    val name: String,
    val description: String
)