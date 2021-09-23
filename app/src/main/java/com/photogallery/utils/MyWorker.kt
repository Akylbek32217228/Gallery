package com.photogallery.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.photogallery.data.DatabaseBuilder
import com.photogallery.data.DatabaseHelperImplementation
import com.photogallery.data.Entity.PhotoEntity
import kotlinx.coroutines.*

class MyWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val dbHelper = DatabaseHelperImplementation(DatabaseBuilder.getInstance(applicationContext))
    var list = listOf<PhotoEntity>()
    override fun doWork(): Result {

        scope.launch {
            list  = withContext(Dispatchers.IO) {
                dbHelper.getPhotosCurrentDay(System.currentTimeMillis())
            }


        }
        if(list.isEmpty()) {

        } else {
            var list2 = arrayListOf<String>()
            for(l in list ) {
                list2.add(l.root)
            }
            ZipManager.compress(list2, System.currentTimeMillis().toString() + ".zip")
        }
        return Result.success()
    }


}