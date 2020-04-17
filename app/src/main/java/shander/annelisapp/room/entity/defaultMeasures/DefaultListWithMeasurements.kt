package shander.annelisapp.room.entity.defaultMeasures

import androidx.room.Embedded
import androidx.room.Relation

data class DefaultListWithMeasurements(
    @Embedded
    var defaultListMeasures: DefaultListMeasures? = null,

    @Relation(parentColumn = "defListId",
        entityColumn = "defMeasuresListId")
    var measurement: List<DefaultMeasurement> = ArrayList()
)