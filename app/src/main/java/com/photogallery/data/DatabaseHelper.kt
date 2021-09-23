package com.photogallery.data

import com.photogallery.data.Entity.PhotoEntity

interface DatabaseHelper {

    suspend fun getPhotos(): List<PhotoEntity>

    suspend fun addPhoto(photoEntity: PhotoEntity)

    suspend fun delete(id : Int)

    suspend fun getPhotosCurrentDay(currentTime : Long): List<PhotoEntity>

}