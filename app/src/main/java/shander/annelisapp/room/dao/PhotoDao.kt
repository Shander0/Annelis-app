package shander.annelisapp.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import io.reactivex.Single
import shander.annelisapp.room.entity.Photo

@Dao
interface PhotoDao : BaseDao<Photo> {

    @Transaction
    @Query("SELECT * FROM photo WHERE photoId = :photoId LIMIT 1")
    fun getSinglePhoto(photoId: Int): Single<Photo>

    @Transaction
    @Query("SELECT * FROM photo WHERE parentProjectId = :parentId")
    fun getPhotosByProject(parentId: Int): Flowable<List<Photo>>
}