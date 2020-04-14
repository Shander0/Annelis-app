package shander.annelisapp.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import io.reactivex.Single
import shander.annelisapp.room.entity.tasks.TaskSecondLevel
import shander.annelisapp.room.entity.tasks.TaskSecondWithThirdsList

@Dao
interface TaskSecondLevelDao: BaseDao<TaskSecondLevel> {

    @Transaction
    @Query("SELECT * FROM TaskSecondLevel WHERE secondTaskEndedDate = 0")
    fun getAllSecondUnfinished(): Flowable<List<TaskSecondWithThirdsList>>

    @Transaction
    @Query("SELECT * FROM TaskSecondLevel WHERE secondTaskId = :taskId LIMIT 1")
    fun getSecondTaskById(taskId: Int): Single<TaskSecondWithThirdsList>

    @Transaction
    @Query("SELECT * FROM TaskSecondLevel WHERE parentFirstTaskId = :tag")
    fun getSecondTasksByFirst(tag: Int): Flowable<List<TaskSecondWithThirdsList>>

}