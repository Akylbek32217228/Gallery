package com.photogallery.view

import com.arellomobile.mvp.MvpView
import com.photogallery.data.Entity.PhotoEntity

interface CameraActivityView : MvpView{

    fun addPhoto(model : PhotoEntity)

}