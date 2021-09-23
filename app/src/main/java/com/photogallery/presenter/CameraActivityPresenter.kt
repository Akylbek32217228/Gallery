package com.photogallery.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.photogallery.data.DatabaseHelperImplementation
import com.photogallery.data.Entity.PhotoEntity
import com.photogallery.view.CameraActivityView
import kotlinx.coroutines.*


@InjectViewState
class CameraActivityPresenter(val dbHelper : DatabaseHelperImplementation) : MvpPresenter<CameraActivityView>() {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun addPhoto(model : PhotoEntity) {

        scope.launch {
            withContext(Dispatchers.IO) {
                dbHelper.addPhoto(model)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}