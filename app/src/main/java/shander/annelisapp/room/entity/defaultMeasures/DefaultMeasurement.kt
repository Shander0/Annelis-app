package shander.annelisapp.room.entity.defaultMeasures

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import shander.annelisapp.room.entity.measurements.ListMeasures

@Entity(
    indices = [Index("defMeasureId"), Index("defMeasuresListId")],
    foreignKeys = [
        ForeignKey(
            entity = DefaultListMeasures::class,
            parentColumns = ["defListId"],
            childColumns = ["defMeasuresListId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DefaultMeasurement(
    val defMeasuresListId: Int,
    val defMeasureName: String,
    val defMeasureDescription: String,
    val defMeasureImage: String,
    val defMeasureValue: Double,
    val defMeasureTag: String
) {
    @PrimaryKey(autoGenerate = true)
    var defMeasureId = 0
}