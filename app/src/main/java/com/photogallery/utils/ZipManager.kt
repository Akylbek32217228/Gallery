package com.photogallery.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import com.photogallery.data.AppDatabase
import com.photogallery.data.DatabaseBuilder
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object ZipManager {

    private val BUFFER_SIZE = 6 * 1024


    @Throws(IOException::class)
    fun compress(folder: ArrayList<String>, compFile: String?) {
        var origin: BufferedInputStream? = null
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        Log.d("ololo", path.toString())
        val out = ZipOutputStream(BufferedOutputStream(FileOutputStream(path.toString() + "/" + compFile)))
        try {
            val data = ByteArray(BUFFER_SIZE)
            for (i in folder.indices) {
                val fi = FileInputStream(folder[i])
                origin = BufferedInputStream(fi, BUFFER_SIZE)
                try {
                    val entry = ZipEntry(folder[i].substring(folder[i].lastIndexOf("/") + 1))
                    out.putNextEntry(entry)
                    var count: Int
                    while (origin.read(data, 0, BUFFER_SIZE).also { count = it } != -1) {
                        out.write(data, 0, count)
                    }
                } finally {
                    origin.close()
                }
            }
        } finally {
            out.close()
        }
    }


}