package shander.annelisapp.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import io.reactivex.Single
import shander.annelisapp.room.entity.Material

@Dao
interface MaterialDao: BaseDao<Material> {

    @Transaction
    @Query("SELECT * FROM Material")
    fun getAll(): Flowable<List<Material>>

    @Transaction
    @Query("SELECT * FROM Material WHERE id = :taskId LIMIT 1")
    fun getMaterialById(taskId: Int): Single<Material>

    @Transaction
    @Query("SELECT * FROM Material WHERE parentProjectId = :tag")
    fun getMaterialsByProject(tag: Int): Flowable<List<Material>>

    @Transaction
    @Query("SELECT * FROM Material WHERE 'exists' = 0")
    fun getMaterialsUnexisted(): Flowable<List<Material>>

}