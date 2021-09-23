package com.photogallery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.photogallery.data.DatabaseBuilder
import com.photogallery.data.DatabaseHelperImplementation
import com.photogallery.data.Entity.PhotoEntity
import com.photogallery.databinding.ActivityMainBinding
import com.photogallery.presenter.MainActivityPresenter
import com.photogallery.recycler.PhotoAdapter
import com.photogallery.utils.MyWorker
import com.photogallery.view.MainActivityView
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit


class MainActivity : MvpAppCompatActivity(), MainActivityView{

    private lateinit var binding: ActivityMainBinding

    lateinit private var adapter : PhotoAdapter

    @InjectPresenter
    lateinit var mainActivityPresenter : MainActivityPresenter

    @ProvidePresenter
    fun provideMainActivityPresenter() : MainActivityPresenter{
        return MainActivityPresenter(dbHelper = DatabaseHelperImplementation(DatabaseBuilder.getInstance(applicationContext)))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        binding.fab.setOnClickListener { view ->
            val intent = Intent(this, CameraActivity::class.java).apply {

            }
            startActivity(intent)
        }
    }


    override fun getPhotos() {
        mainActivityPresenter.getPhotos()
    }

    override fun showPhotos(list: List<PhotoEntity>) {
        adapter.photoList.clear()
        if(list.size == 0) {
            binding.emptyPackage.visibility = View.VISIBLE
            Log.d("ololo", "Empty")
            adapter.notifyDataSetChanged()
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.emptyPackage.visibility = View.INVISIBLE
            Log.d("ololo", "is not Empty " + list.get(0).name)
            adapter.setPhotos(list)
        }
    }

    override fun deletePhoto(id: Int) {
        getPhotos()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        adapter.photoList.clear()
        getPhotos()
    }

    private fun setupRecyclerView() {
        adapter = PhotoAdapter(context = applicationContext, onLongClick = { photo ->

            Log.d("ololo", "cliacked")

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete Photo?")
            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                mainActivityPresenter.deletePhoto(photo!!.id!!)

            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->

            }
            builder.show()


        }, onSelect = { photo->
            val intent = Intent(this, ClickPhotoActivity::class.java).apply {

            }
            intent.putExtra("root", photo!!.root)
            startActivity(intent)
        })
        val llm = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = llm
        binding.recyclerView.adapter = adapter

    }

}