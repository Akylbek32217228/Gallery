package com.photogallery.data.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_gallery_entity")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "created_time") val created_time : Long,
    @ColumnInfo(name = "longitude")val longitude : Double,
    @ColumnInfo(name = "latitude")val latitude : Double,
    @ColumnInfo(name = "root")val root : String


)
