package com.photogallery.data

import com.photogallery.data.Entity.PhotoEntity

class DatabaseHelperImplementation(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getPhotos(): List<PhotoEntity> {
        return appDatabase.reposDao().getPhotos()
    }

    override suspend fun addPhoto(photoEntity: PhotoEntity) {
        appDatabase.reposDao().addPhoto(photoEntity)
    }

    override suspend fun delete(id: Int) {
        appDatabase.reposDao().deletePhoto(id)
    }

    override suspend fun getPhotosCurrentDay(currentTime: Long): List<PhotoEntity> {
        return appDatabase.reposDao().getPhotosCurrentDay(currentTime)
    }

}