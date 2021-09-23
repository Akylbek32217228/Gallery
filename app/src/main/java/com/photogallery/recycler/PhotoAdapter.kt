package com.photogallery.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.photogallery.data.Entity.PhotoEntity
import com.photogallery.databinding.ItemPhotoBinding


class PhotoAdapter(private val onLongClick : (PhotoEntity?) -> Unit, val context : Context,
                   private val onSelect : (PhotoEntity?) -> Unit): RecyclerView.Adapter<PhotoViewHolder>() {

    var photoList = arrayListOf<PhotoEntity>()

    fun setPhotos(list : List<PhotoEntity>) {
        photoList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        /*val currentItem = list[position]
        holder.binding.name.text = currentItem.country
        holder.binding.region.text = currentItem.region
        holder.bind(currentItem,onSelect)*/

        holder.onBind(onLongClick,onSelect, photoList.get(position), context)
    }

    override fun getItemCount(): Int {
        return photoList.size;
    }
}