package shander.annelisapp.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import shander.annelisapp.room.entity.tasks.TaskThirdLevel

@Dao
interface TaskThirdLevelDao: BaseDao<TaskThirdLevel> {

    @Transaction
    @Query("SELECT * FROM TaskThirdLevel WHERE thirdTaskEndedDate = 0")
    fun getAllThirdUnfinished(): Flowable<List<TaskThirdLevel>>

    @Transaction
    @Query("SELECT * FROM TaskThirdLevel WHERE thirdTaskId = :taskId LIMIT 1")
    fun getThirdTaskById(taskId: Int): Single<TaskThirdLevel>

    @Transaction
    @Query("SELECT * FROM TaskThirdLevel WHERE parentSecondTaskId = :tag")
    fun getThirdTasksBySecond(tag: Int): Flowable<List<TaskThirdLevel>>

    @Transaction
    @Query("DELETE FROM TaskThirdLevel WHERE thirdTaskId = :id")
    fun deleteById(id: Int): Completable

}