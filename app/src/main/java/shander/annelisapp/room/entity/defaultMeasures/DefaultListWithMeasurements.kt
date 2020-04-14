package shander.annelisapp.room.entity.defaultMeasures

import androidx.room.Embedded
import androidx.room.Relation

data class DefaultListWithMeasurements(
    @Embedded
    var defaultListMeasures: DefaultListMeasures? = null,

    @Relation(parentColumn = "listId",
        entityColumn = "measuresListId")
    var measurement: List<DefaultMeasurement> = ArrayList()
)