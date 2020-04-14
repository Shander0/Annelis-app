package shander.annelisapp.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import io.reactivex.Single
import shander.annelisapp.room.entity.tasks.TaskFirstLevel
import shander.annelisapp.room.entity.tasks.TaskFirstWithNestedList

@Dao
interface TaskFirstLevelDao: BaseDao<TaskFirstLevel> {

    @Transaction
    @Query("SELECT * FROM TaskFirstLevel WHERE firstTaskEndedDate = 0")
    fun getAllUnfinished(): Flowable<List<TaskFirstWithNestedList>>

    @Transaction
    @Query("SELECT * FROM TaskFirstLevel WHERE firstTaskId = :taskId LIMIT 1")
    fun getFirstTaskById(taskId: Int): Single<TaskFirstWithNestedList>

    @Transaction
    @Query("SELECT * FROM TaskFirstLevel WHERE parentProjectId = :tag")
    fun getTasksByProject(tag: Int): Flowable<List<TaskFirstWithNestedList>>

    @Transaction
    @Query("SELECT * FROM TaskFirstLevel WHERE parentProjectId = :tag AND firstTaskEndedDate = 0")
    fun getUnfinishedTasksByProject(tag: Int): Flowable<List<TaskFirstWithNestedList>>

}