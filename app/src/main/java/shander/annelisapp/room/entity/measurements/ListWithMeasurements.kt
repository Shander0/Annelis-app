package shander.annelisapp.room.entity.measurements

import androidx.room.Embedded
import androidx.room.Relation

data class ListWithMeasurements(
    @Embedded
    var listMeasures: ListMeasures? = null,

    @Relation(parentColumn = "listId",
        entityColumn = "measuresListId")
    var measurement: List<Measurement> = ArrayList()
)