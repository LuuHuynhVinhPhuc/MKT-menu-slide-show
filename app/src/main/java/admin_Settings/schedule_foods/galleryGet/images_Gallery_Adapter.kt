package com.example.myapp.gallery_RCV

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.menu_mkt.R

class images_Gallery_Adapter(private var images: List<Uri>) : RecyclerView.Adapter<images_Gallery_Adapter.ImageViewHolder>() {

    // Cập nhật dữ liệu mới cho Adapter
    fun updateData(newImages: List<Uri>) {
        images = newImages
        notifyDataSetChanged()
    }


    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: AppCompatImageView = itemView.findViewById(R.id.imagePhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_gallery_get, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUri = images[position]
        holder.imageView.setImageURI(imageUri)
    }
}