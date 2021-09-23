package com.photogallery.recycler

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.photogallery.R
import com.photogallery.data.Entity.PhotoEntity
import com.photogallery.databinding.ItemPhotoBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PhotoViewHolder(val binding: ItemPhotoBinding)  : RecyclerView.ViewHolder(binding.root) {


    fun onBind(onLongClick : (PhotoEntity?) -> Unit,onSelect : (PhotoEntity?) -> Unit,
               model : PhotoEntity, context : Context) {

        val fileName = model.root

        Log.d("ololo", "file coordinates " + model.latitude + " + " + model.longitude)
        val geocoder = Geocoder(context, Locale.getDefault())
        var addresses: List<Address>? = null
        try {
            addresses = geocoder.getFromLocation(model.latitude, model.longitude, 1)
            binding.photoLocation.setText(addresses.get(0).adminArea)
        } catch (e : IOException) {
            binding.photoLocation.setText("")
        }


        val file = File(fileName)
        val imageUri: Uri = Uri.fromFile(file)
        val dateString: String = SimpleDateFormat("MM/dd/yyyy hh:mm a").format(Date(model.created_time))
        binding.photoName.setText("name : " + model.name)
        binding.photoDate.setText(dateString)
        Glide.with(context)
            .load(imageUri)
            .placeholder(R.drawable.progress_animation)
            .into(binding.imageViewPhoto)



        binding.photoItem.setOnLongClickListener(View.OnLongClickListener {
            onLongClick(model)
            true
        })

        binding.photoItem.setOnClickListener(View.OnClickListener {

            onSelect(model)

        })
    }


}