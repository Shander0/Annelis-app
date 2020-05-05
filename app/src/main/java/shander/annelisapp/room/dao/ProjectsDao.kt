package shander.annelisapp.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import shander.annelisapp.room.entity.Project
import shander.annelisapp.room.entity.ProjectWithAllNested

@Dao
interface ProjectsDao : BaseDao<Project>{

    @Transaction
    @Query("SELECT * FROM projects_table")
    fun getAll(): Flowable<List<ProjectWithAllNested>>

    @Transaction
    @Query("SELECT * FROM projects_table WHERE projectId = :projectId LIMIT 1")
    fun getProject(projectId: Int): Single<ProjectWithAllNested>

    @Transaction
    @Query("SELECT * FROM projects_table WHERE projectName LIKE :namePart")
    fun getSearched(namePart: String): Flowable<List<ProjectWithAllNested>>

    @Transaction
    @Query("SELECT * FROM projects_table WHERE projectFinished = :isActive")
    fun getActiveProjects(isActive: Boolean): Flowable<List<ProjectWithAllNested>>

    @Transaction
    @Query("DELETE FROM projects_table WHERE projectId = :id")
    fun deleteById(id: Int): Completable
}