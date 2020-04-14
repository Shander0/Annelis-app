package shander.annelisapp.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import io.reactivex.Single
import shander.annelisapp.room.entity.measurements.Measurement

@Dao
interface MeasurementDao : BaseDao<Measurement>{

    @Transaction
    @Query("SELECT * FROM Measurement")
    fun getAll(): Flowable<List<Measurement>>

    @Transaction
    @Query("SELECT * FROM Measurement WHERE measureId = :measureId LIMIT 1")
    fun getMeasure(measureId: Int): Single<Measurement>

    @Transaction
    @Query("SELECT * FROM Measurement WHERE measuresListId = :listId")
    fun getMeasuresFromList(listId: Int): Flowable<List<Measurement>>

    @Transaction
    @Query("SELECT * FROM Measurement WHERE measureTag LIKE :tag")
    fun getMeasuresByTag(tag: String): Flowable<List<Measurement>>
}