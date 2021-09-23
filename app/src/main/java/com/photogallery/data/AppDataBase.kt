package com.photogallery.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.photogallery.data.Dao.ReposDao
import com.photogallery.data.Entity.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun reposDao(): ReposDao

}