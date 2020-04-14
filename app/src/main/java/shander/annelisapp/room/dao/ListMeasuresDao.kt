package shander.annelisapp.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import io.reactivex.Single
import shander.annelisapp.room.entity.measurements.ListMeasures
import shander.annelisapp.room.entity.measurements.ListWithMeasurements

@Dao
interface ListMeasuresDao : BaseDao<ListMeasures>{

    @Transaction
    @Query("SELECT * FROM ListMeasures")
    fun getAll(): Flowable<List<ListWithMeasurements>>

    @Transaction
    @Query("SELECT * FROM ListMeasures WHERE listId = :listId LIMIT 1")
    fun getListById(listId: Int): Single<ListWithMeasurements>

    @Transaction
    @Query("SELECT * FROM ListMeasures WHERE name LIKE :tag")
    fun getListByName(tag: String): Flowable<List<ListWithMeasurements>>
}