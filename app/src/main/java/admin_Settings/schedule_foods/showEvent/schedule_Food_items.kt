package admin_Settings.schedule_foods.showEvent

import admin_Settings.schedule_foods.galleryGet.upload_Images
import admin_Settings.schedule_foods.showEvent.Adapter.menu.menu_Adapter
import admin_Settings.schedule_foods.showEvent.Adapter.slider.slider_Adapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.menu_mkt.R
import com.example.myapp.recycleView.program_Item
import com.google.gson.Gson
import slideshow.autoSlider.sliderView
import slideshow.menuSlider.menuSlider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class schedule_Food_items : AppCompatActivity() {
    // shared references
    private lateinit var sharedPreferences: SharedPreferences

    // recycler view items
    private lateinit var  menuRecyclerView: RecyclerView
    private lateinit var  sliderRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_schedule_food_items)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // shared references value
        sharedPreferences = getSharedPreferences("Even_ID", Context.MODE_PRIVATE)

        sharedPreferences = getSharedPreferences("JsonString_Final", Context.MODE_PRIVATE)

        // edit for cancel : go to choosing page
        val btnCancel = findViewById<Button>(R.id.buttonSkip)
        btnCancel.setOnClickListener {
            val i = Intent(this, sliderView::class.java)
            startActivity(i)
        }


        // check Event and send key to shared references
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)
        val btnUpload: AppCompatButton = findViewById(R.id.uploadPicture)
        var nodeEventID = "1"

        // get Event to run
        val btnChooseEventtoShow : AppCompatButton = findViewById(R.id.buttonSaveSchedule)

        // radio event
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButton1 -> {
                    // Option 1 selected
                    Toast.makeText(this, "Option 1 selected", Toast.LENGTH_SHORT).show()
                    nodeEventID = "1"
                    Log.d("Node Event ID", nodeEventID)

                    val jsonString = sharedPreferences.getString("Jsonstring_$nodeEventID", "")
                    if (jsonString != null) {
                        JsonReader(jsonString)
                    }

                    btnChooseEventtoShow.setOnClickListener{
                        val i = Intent(this, sliderView::class.java)
                        startActivity(i)
                        if (jsonString != null) {
                            navigateToNextActivity(jsonString)
                        }
                    }
                }

                R.id.radioButton2 -> {
                    // Option 2 selected
                    Toast.makeText(this, "Option 2 selected", Toast.LENGTH_SHORT).show()
                    nodeEventID = "2"
                    Log.d("Node Event ID", nodeEventID)

                    val jsonString = sharedPreferences.getString("Jsonstring_$nodeEventID", "")
                    if (jsonString != null) {
                        JsonReader(jsonString)
                    }

                    btnChooseEventtoShow.setOnClickListener{
                        val i = Intent(this, sliderView::class.java)
                        startActivity(i)
                        if (jsonString != null) {
                            navigateToNextActivity(jsonString)
                        }
                    }
                }

                R.id.radioButton3 -> {
                    // Option 3 selected
                    Toast.makeText(this, "Option 3 selected", Toast.LENGTH_SHORT).show()
                    nodeEventID = "3"
                    Log.d("Node Event ID", nodeEventID)

                    val jsonString = sharedPreferences.getString("Jsonstring_$nodeEventID", "")
                    if (jsonString != null) {
                        JsonReader(jsonString)
                    }

                    btnChooseEventtoShow.setOnClickListener{
                        val i = Intent(this, sliderView::class.java)
                        startActivity(i)
                        if (jsonString != null) {
                            navigateToNextActivity(jsonString)
                        }
                    }
                }
            }
        }
        // for upload button
        btnUpload.setOnClickListener {
            //val idEvent = findViewById<TextView>(R.id.idEvent)

            // for upload button
            val btnUpload: AppCompatButton = findViewById(R.id.uploadPicture)
            btnUpload.setOnClickListener {

                val sharedPreferences = getSharedPreferences("Even_ID", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()

                editor.putString("nodeIDEvent", nodeEventID)

                editor.apply()

                val i = Intent(this, upload_Images::class.java)
                startActivity(i)
            }
        }
    }
    fun navigateToNextActivity(jsonString: String) {
        val intent = Intent(this, sliderView::class.java)
        intent.putExtra("jsonString", jsonString)
        startActivity(intent)

        val intent2 = Intent(this, menuSlider::class.java)
        intent2.putExtra("jsonString", jsonString)
        startActivity(intent)
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
        val menuListString = data.menuList.removeSurrounding("[", "]").split(", ")
        val sliderListString = data.sliderList.removeSurrounding("[", "]").split(", ")

        // Lưu dữ liệu time stamp
        val startDay = data.startDayTimeStamp
        val endDay = data.endDayTimeStamp

        // Chuyển đổi danh sách chuỗi thành danh sách các Uri
        val menuListUris = menuListString.map { Uri.parse(it) }
        val sliderListUris = sliderListString.map { Uri.parse(it) }

        // In danh sách các Uri ra log
        menuListUris.forEach { uri ->
            Log.d("MainActivity", "Menu Uri: $uri")
        }
        sliderListUris.forEach { uri ->
            Log.d("MainActivity", "Slider Uri: $uri")
        }

        // convert từ long timeStamp sang String
        val startDayString = timestampToString(startDay, "dd-MM-yyyy")
        val endDayString = timestampToString(endDay, "dd-MM-yyyy")


        // tạo recycler view và đưa hình ảnh vào
        // Thiết lập RecyclerView cho menuList
        menuRecyclerView = findViewById(R.id.menuRCV)
        menuRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val menuUriAdapter = menu_Adapter(menuListUris)
        menuRecyclerView.adapter = menuUriAdapter
        menuUriAdapter.setData(menuListUris)


        // Thiết lập RecyclerView cho sliderList
        sliderRecyclerView = findViewById(R.id.sliderRCV)
        sliderRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val sliderUriAdapter = slider_Adapter(sliderListUris)
        sliderRecyclerView.adapter = sliderUriAdapter
        sliderUriAdapter.setData(sliderListUris)

        // update start and end days
        val startDayText: AppCompatTextView = findViewById(R.id.startTimeTxt_schedule)
        val endDayText: AppCompatTextView = findViewById(R.id.endTimeTxt_schedule)
        // updates data
        startDayText.text = startDayString
        endDayText.text = endDayString
    }

    fun timestampToString(timestamp: Long, pattern: String): String {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val date = Date(timestamp)
        return dateFormat.format(date)
    }
}
