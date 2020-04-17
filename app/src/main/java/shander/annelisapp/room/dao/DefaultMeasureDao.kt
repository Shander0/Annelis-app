package shander.annelisapp.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import io.reactivex.Single
import shander.annelisapp.room.entity.defaultMeasures.DefaultMeasurement

@Dao
interface DefaultMeasureDao : BaseDao<DefaultMeasurement>{

    @Transaction
    @Query("SELECT * FROM DefaultMeasurement")
    fun getAll(): Flowable<List<DefaultMeasurement>>

    @Transaction
    @Query("SELECT * FROM DefaultMeasurement WHERE defMeasureId = :measureId LIMIT 1")
    fun getMeasure(measureId: Int): Single<DefaultMeasurement>

    @Transaction
    @Query("SELECT * FROM DefaultMeasurement WHERE defMeasuresListId = :listId")
    fun getMeasuresFromList(listId: Int): Flowable<List<DefaultMeasurement>>

    @Transaction
    @Query("SELECT * FROM DefaultMeasurement WHERE defMeasureTag LIKE :tag")
    fun getMeasuresByTag(tag: String): Flowable<List<DefaultMeasurement>>
}