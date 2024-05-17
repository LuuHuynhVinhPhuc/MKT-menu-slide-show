package slideshow.autoSlider

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menu_mkt.R
import com.example.myapp.recycleView.program_Item
import com.google.gson.Gson
import slideshow.menuSlider.menuSlider
import slideshow.menuSlider.menu_Adapter_slideshow

class sliderView : AppCompatActivity() {

    // recycler view items
    private lateinit var  sliderRecyclerView: RecyclerView

    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_slider_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val jsonString = intent.getStringExtra("jsonString")
        jsonString?.let {
            Log.d("Json String", jsonString)


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
        val menuListString = data.sliderList.removeSurrounding("[", "]").split(", ")


        // Chuyển đổi danh sách chuỗi thành danh sách các Uri
        val menuListUris = menuListString.map { Uri.parse(it) }

        // In danh sách các Uri ra log
        menuListUris.forEach { uri ->
            Log.d("MainActivity", "Menu Uri: $uri")
        }

        // tạo recycler view và đưa hình ảnh vào
        // Thiết lập RecyclerView cho menuList
        sliderRecyclerView = findViewById(R.id.rcvAUTOitem)
        sliderRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val menuUriAdapter = sliderview_Adapter_slideshow(menuListUris)
        sliderRecyclerView.adapter = menuUriAdapter
        menuUriAdapter.setData(menuListUris)
    }
    // Handle touch events and navigate to the next page
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val intent = Intent(this, menuSlider::class.java)
            startActivity(intent)
            return true // Consume the event to prevent further handling
        }
        return super.onTouchEvent(event)
    }
}