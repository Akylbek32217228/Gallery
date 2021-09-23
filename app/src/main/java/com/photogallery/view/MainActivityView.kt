package com.photogallery.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.photogallery.data.Entity.PhotoEntity


interface MainActivityView : MvpView {

    fun getPhotos()
    fun showPhotos(list : List<PhotoEntity>)
    fun deletePhoto(id : Int)

}