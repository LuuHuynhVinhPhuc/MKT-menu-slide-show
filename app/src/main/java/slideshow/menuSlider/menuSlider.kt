package slideshow.menuSlider

import admin_Settings.schedule_foods.showEvent.Adapter.menu.menu_Adapter
import admin_Settings.schedule_foods.showEvent.Adapter.slider.slider_Adapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menu_mkt.R
import com.example.myapp.recycleView.program_Item
import com.google.gson.Gson
import signin.sign_in

class menuSlider : AppCompatActivity() {

    private val DELAY_TIME_MS: Long = 90000 // 1mins 30 seconds
    private lateinit var runnable: Runnable
    private val handler = Handler(Looper.getMainLooper())

    // recycler view items


    private lateinit var  menuRecyclerView: RecyclerView

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var menuListString : List<String>

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_slider)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gotoSignin: AppCompatImageButton = findViewById(R.id.btnChangetoSignin)
        gotoSignin.setOnClickListener {
            val i = Intent(this, sign_in::class.java)
            startActivity(i)
        }


        // shared references value
        sharedPreferences = getSharedPreferences("Even_ID", Context.MODE_PRIVATE)
        // get data from shared references
        val storageID = sharedPreferences.getString("nodeIDEvent","1")
        // get item from it
        val jsonString = sharedPreferences.getString("Jsonstring_$storageID", "")
        if (jsonString != null) {
            JsonReader(jsonString)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun JsonReader(jsonString: String) {
        // Kiểm tra và xử lý dữ liệu rỗng
        val jsonData: String = if (jsonString.isNullOrEmpty()) {
            // Nếu jsonString2 rỗng hoặc null, cung cấp giá trị mặc định
            """
  {
  "storageID": "1",
  "nodeIDEvent": "1",
  "startDayTimeStamp": 1717520400000,
  "endDayTimeStamp": 1717606800000,
  "menuList": "[content://com.android.externalstorage.documents/document/primary%3ADownload%2Fhinh2.png, content://com.android.externalstorage.documents/document/primary%3ADownload%2Fhinhmain.jpg, content://com.android.externalstorage.documents/document/primary%3ADownload%2Fmain.jpg]",
  "sliderList": "[content://com.android.externalstorage.documents/document/primary%3ADownload%2Fslider1.jpg, content://com.android.externalstorage.documents/document/primary%3ADownload%2Fslider3.jpg, content://com.android.externalstorage.documents/document/primary%3ADownload%2Fslider2.jpg]"
}
    """ // Dữ liệu mặc định
        } else {
            jsonString
        }

        // Sử dụng Gson để phân tích JSON
        val gson = Gson()
        val data = gson.fromJson(jsonData, program_Item::class.java)

        // Lọc dữ liệu từ chuỗi JSON và lưu vào danh sách các chuỗi
        menuListString = data.menuList.removeSurrounding("[", "]").split(", ")


        // Chuyển đổi danh sách chuỗi thành danh sách các Uri
        val menuListUris = menuListString.map { Uri.parse(it) }

        // In danh sách các Uri ra log
        menuListUris.forEach { uri ->
            Log.d("MainActivity", "Menu Uri: $uri")
        }


        // tạo recycler view và đưa hình ảnh vào
        // Thiết lập RecyclerView cho menuList
        menuRecyclerView = findViewById(R.id.recyclerViewMenu)
        menuRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val menuUriAdapter = menu_Adapter_slideshow(menuListUris)
        menuRecyclerView.adapter = menuUriAdapter
        menuUriAdapter.setData(menuListUris)
    }

    fun indicatorDots(){
        val slideDotLL = findViewById<LinearLayout>(R.id.slideDot)
        val dotsImage = Array(menuListString.size) { ImageView(this) }

        dotsImage.forEach {
            it.setImageResource(
                R.drawable.non_active_dot
            )
            slideDotLL.addView(it,params)
        }

        // default first dot selected
        dotsImage[0].setImageResource(R.drawable.active_dot)

    }
}