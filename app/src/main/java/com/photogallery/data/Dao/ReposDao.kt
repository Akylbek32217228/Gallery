package com.photogallery.data.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.photogallery.data.Entity.PhotoEntity

@Dao
interface ReposDao {

    @Query("SELECT * FROM photo_gallery_entity order by id DESC")
    suspend fun getPhotos(): List<PhotoEntity>

    @Query("SELECT * FROM photo_gallery_entity where created_time>(:currentTime - 86400000)")
    suspend fun getPhotosCurrentDay(currentTime : Long): List<PhotoEntity>

    @Insert
    suspend fun addPhoto(photoEntity: PhotoEntity)

    @Query("DELETE FROM photo_gallery_entity WHERE id=:id")
    suspend fun deletePhoto(id : Int)


}