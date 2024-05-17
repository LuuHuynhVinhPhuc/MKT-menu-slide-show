package admin_Settings.schedule_foods.showEvent.Adapter.menu

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.menu_mkt.R
import com.example.myapp.recycleView.program_Item

class menu_Adapter(private var uriList: List<Uri>) : RecyclerView.Adapter<menu_Adapter.ViewHolder>() {

    fun setData(programs: List<Uri>) {
        this.uriList = programs
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imagesView : AppCompatImageView = itemView.findViewById(R.id.itemsMenu_images)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): menu_Adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUri = uriList[position]
        holder.imagesView.setImageURI(imageUri)
    }

    override fun getItemCount(): Int {
        return uriList.size
    }
}