package com.photogallery.presenter

import android.util.Log
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.photogallery.data.DatabaseHelperImplementation
import com.photogallery.data.Entity.PhotoEntity
import com.photogallery.view.MainActivityView
import kotlinx.coroutines.*
import java.io.IOException


@InjectViewState
class MainActivityPresenter(val dbHelper : DatabaseHelperImplementation) : MvpPresenter<MainActivityView>() {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    var list = listOf<PhotoEntity>()

    fun getPhotos() {
        scope.launch(Dispatchers.Main){
            try {
                Log.d("ololo", "isSuccessful")

                list = withContext(Dispatchers.IO) {
                    dbHelper.getPhotos()

                }
                viewState.showPhotos(list)

            } catch (e: IOException) {
                Log.d("ololo", e.message.toString())
            }
        }
    }

    fun deletePhoto(id : Int) {
        scope.launch{
            withContext(Dispatchers.IO) {
                dbHelper.delete(id)
            }

            getPhotos()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

}