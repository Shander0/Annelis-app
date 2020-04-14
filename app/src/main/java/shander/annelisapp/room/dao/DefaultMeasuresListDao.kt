package shander.annelisapp.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import io.reactivex.Single
import shander.annelisapp.room.entity.defaultMeasures.DefaultListMeasures
import shander.annelisapp.room.entity.defaultMeasures.DefaultListWithMeasurements

@Dao
interface DefaultMeasuresListDao : BaseDao<DefaultListMeasures>{

    @Transaction
    @Query("SELECT * FROM DefaultListMeasures")
    fun getAll(): Flowable<List<DefaultListWithMeasurements>>

    @Transaction
    @Query("SELECT * FROM DefaultListMeasures WHERE listId = :listId LIMIT 1")
    fun getListById(listId: Int): Single<DefaultListWithMeasurements>

    @Transaction
    @Query("SELECT * FROM DefaultListMeasures WHERE name LIKE :tag")
    fun getListByName(tag: String): Flowable<List<DefaultListWithMeasurements>>
}