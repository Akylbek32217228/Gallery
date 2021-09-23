package com.photogallery

import android.R
import android.media.AudioRecord.MetricsConstants.SOURCE
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.photogallery.databinding.ActivityClickPhotoBinding
import com.photogallery.databinding.ActivityMainBinding
import java.io.File


class ClickPhotoActivity : AppCompatActivity() {

    val EXTRA_SPACE_PHOTO = "SpacePhotoActivity.SPACE_PHOTO"
    private lateinit var binding: ActivityClickPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClickPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val root = intent.getStringExtra("root")

        val file = File(root)
        val imageUri: Uri = Uri.fromFile(file)
        //val spacePhoto: SpacePhoto = intent.getParcelableExtra(EXTRA_SPACE_PHOTO)

        Glide.with(this)
            .load(imageUri)
            .into(binding.spaceImage)

    }
}