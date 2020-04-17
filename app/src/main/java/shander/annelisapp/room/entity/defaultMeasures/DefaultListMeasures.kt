package shander.annelisapp.room.entity.defaultMeasures

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DefaultListMeasures(
    val defListName: String,
    val defListDescription: String
) {
    @PrimaryKey (autoGenerate = true)
    var defListId = 0
}