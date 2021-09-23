package com.photogallery

import android.app.Application
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.photogallery.utils.MyWorker
import java.util.concurrent.TimeUnit

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        val myWorkRequest = PeriodicWorkRequest.Builder(MyWorker::class.java, 24, TimeUnit.HOURS)
            .build()
        WorkManager.getInstance().enqueue(myWorkRequest);
    }

}