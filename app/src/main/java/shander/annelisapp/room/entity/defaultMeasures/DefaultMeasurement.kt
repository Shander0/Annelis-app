package shander.annelisapp.room.entity.defaultMeasures

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import shander.annelisapp.room.entity.measurements.ListMeasures

@Entity(
    indices = [Index("measureId"), Index("measuresListId")],
    foreignKeys = [
        ForeignKey(
            entity = ListMeasures::class,
            parentColumns = ["listId"],
            childColumns = ["measuresListId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DefaultMeasurement(
    @PrimaryKey(autoGenerate = true)
    val measureId: Int,
    val measuresListId: Int,
    val measureName: String,
    val measureDescription: String,
    val measureImage: String,
    val measureValue: Double,
    val measureTag: String
)