package slideshow.autoSlider

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.menu_mkt.R

class sliderview_Adapter_slideshow (private var uriList: List<Uri>) : RecyclerView.Adapter<sliderview_Adapter_slideshow.ImageViewHolder>() {

    // Cập nhật dữ liệu mới cho Adapter
    fun setData(programs: List<Uri>) {
        this.uriList = programs
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: AppCompatImageView = itemView.findViewById(R.id.itemsSlider_images_items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slideshowitems, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: sliderview_Adapter_slideshow.ImageViewHolder, position: Int) {
        val imageUri = uriList[position]
        holder.imageView.setImageURI(imageUri)
    }

    override fun getItemCount(): Int {
        return uriList.size
    }

}