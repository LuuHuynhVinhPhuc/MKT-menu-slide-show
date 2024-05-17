package admin_Settings.schedule_foods.galleryGet

import admin_Settings.schedule_foods.showEvent.schedule_Food_items
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.menu_mkt.R
import com.example.myapp.alert.alertDialog_OK
import com.example.myapp.gallery_RCV.images_Gallery_Adapter
import com.google.gson.Gson
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Locale


class upload_Images : AppCompatActivity() {

    // shared references
    private lateinit var sharedPreferences: SharedPreferences

    // get elements for time stamp
    private var startDay_TimeStamp: Long =0
    private var endDay_TimeStamp: Long =0

    // list images type
    // create two array string and convert into string array
    private val menuList = mutableListOf<Uri>()
    private val sliderList = mutableListOf<Uri>()

    private lateinit var recyclerView: RecyclerView
    private val images = mutableListOf<Uri>() // images list from Gallery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_upload_images)

        // get button
        val cancelUpload : AppCompatButton = findViewById(R.id.cancelBtnPicking)

        // get reference to the string array that we just created
        val type = resources.getStringArray(R.array.type)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, type)
        // get reference to the autocomplete text view
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)


        cancelUpload.setOnClickListener{
            val i = Intent(this@upload_Images, schedule_Food_items::class.java)
            startActivity(i)
        }

        // shared references get ID
        // shared references value
        sharedPreferences = getSharedPreferences("Even_ID", MODE_PRIVATE)

        // get data from shared references
        val storageID = sharedPreferences.getString("nodeIDEvent","1")

        // date - time picker
        val buttonDateTimePicker: AppCompatButton = findViewById(R.id.pickDatetime)
        buttonDateTimePicker.setOnClickListener {
            showDateTimePickerDialog()
        }

        //
        // Gallery management
        // for recycler view images Items
        recyclerView= findViewById(R.id.recyclerViewImages)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter = images_Gallery_Adapter(images)
        recyclerView.adapter = adapter

        // event when clicked button open Gallery
        openGallery_takeImages(images, adapter)

        // get images item from recycler view
        // save it into each type
        // delete recycler view ( blank when nothing exist on data or display data on each type )
        // save images list
        val imagesList_save : AppCompatButton = findViewById(R.id.saveType)

        autocompleteTV.setOnItemClickListener { parent, _, position, _ ->
            // get elements
            val selectedItem = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "You chose: $selectedItem", Toast.LENGTH_SHORT).show()

            // event for saving data into list
            when (selectedItem) {
                "Menu" -> {
                    // check data if exist -> use that or not -> use default list
                    if (menuList.isNotEmpty()) {
                        recyclerView.adapter = images_Gallery_Adapter(menuList)
                        adapter.notifyDataSetChanged()

                        imagesList_save.setOnClickListener {
                            // Lọc và thêm các phần tử mới vào danh sách tạm thời
                            val newImages = images.filter { !menuList.contains(it) }
                            menuList.addAll(newImages)

                            // print it to log
                            menuList.forEach { imagesUri ->
                                Log.d("Menu Uri Item ", imagesUri.toString())
                            }
                            // Hiển thị danh sách mới trên RecyclerView
                            recyclerView.adapter = images_Gallery_Adapter(menuList)

                            // Hiển thị thông báo thành công
                            alertDialog_OK.showCustomAlertDialog(this, "Lưu trữ thành công", "Hình ảnh đã được lưu ")
                        }
                    } else {
                        // clear temp list
                        images.clear()
                        // reset adapter
                        recyclerView.adapter = null

                        imagesList_save.setOnClickListener {
                            // Lọc và thêm các phần tử mới vào danh sách tạm thời
                            val newImages = images.filter { !menuList.contains(it) }
                            menuList.addAll(newImages)

                            // print it to log
                            menuList.forEach { imagesUri ->
                                Log.d("Menu Uri Item ", imagesUri.toString())
                            }
                            // Hiển thị danh sách mới trên RecyclerView
                            recyclerView.adapter = images_Gallery_Adapter(menuList)

                            // Hiển thị thông báo thành công
                            adapter.updateData(menuList)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                "Slide show" -> {
                    // check data if exist -> use that or not -> use default list
                    if (sliderList.isNotEmpty()) {
                        recyclerView.adapter = images_Gallery_Adapter(sliderList)
                        adapter.notifyDataSetChanged()

                        imagesList_save.setOnClickListener {
                            // Lọc và thêm các phần tử mới vào danh sách tạm thời
                            val newImages = images.filter { !sliderList.contains(it) }
                            sliderList.addAll(newImages)

                            // print it to log
                            sliderList.forEach { imagesUri ->
                                Log.d("Menu Uri Item ", imagesUri.toString())
                            }
                            // Hiển thị danh sách mới trên RecyclerView
                            recyclerView.adapter = images_Gallery_Adapter(sliderList)

                            // Hiển thị thông báo thành công
                            alertDialog_OK.showCustomAlertDialog(this, "Lưu trữ thành công", "Hình ảnh đã được lưu ")
                        }
                    } else {
                        // clear temp list
                        images.clear()
                        // reset adapter
                        recyclerView.adapter = null

                        imagesList_save.setOnClickListener {
                            // Lọc và thêm các phần tử mới vào danh sách tạm thời
                            val newImages = images.filter { !sliderList.contains(it) }
                            sliderList.addAll(newImages)

                            // print it to log
                            sliderList.forEach { imagesUri ->
                                Log.d("Menu Uri Item ", imagesUri.toString())
                            }
                            // Hiển thị danh sách mới trên RecyclerView
                            recyclerView.adapter = images_Gallery_Adapter(sliderList)

                            // Hiển thị thông báo thành công
                            adapter.updateData(sliderList)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                else -> {
                    // Đặt RecyclerView là null nếu không có loại nào được chọn
                    recyclerView.adapter = null
                }
            }
        }

        // save event
        val saveEvent: AppCompatButton = findViewById(R.id.saveEvent)
        saveEvent.setOnClickListener{
            val startDate: AppCompatTextView = findViewById(R.id.startTimeTxt)
            val endDate: AppCompatTextView = findViewById(R.id.endTimeTxt)

            val startDateString = startDate.text.toString().trim()
            val endDateString = endDate.text.toString().trim()

            if (menuList.isEmpty() || sliderList.isEmpty()) {
                // missing data images from both
                alertDialog_OK.showCustomAlertDialog(this, "Thiếu dữ liệu", "Bạn hãy thêm đầy đủ 2 phần của chương trình")
            }else if (startDateString == "01-01-1111" || endDateString == "02-02-2222"){
                // missing data date time from both
                alertDialog_OK.showCustomAlertDialog(this, "Thiếu thời gian", "Bạn hãy kiểm tra lại ngày bắt đầu và ngày kết thúc của chương trình")
            }else{
                // event: save it into json object and use shared references for it
                saveGroupDataToSharedPreferences(startDay_TimeStamp,endDay_TimeStamp,menuList,sliderList)

                // intent it
                val i = Intent(this, schedule_Food_items::class.java)
                startActivity(i)

                // event: save it into json files

            }
        }
    }

    private fun showDateTimePickerDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.activity_date_picker_events, null)
        builder.setView(dialogLayout)

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.window?.setDimAmount(0.5f)

        val startDate: AppCompatEditText = dialogLayout.findViewById(R.id.edit_date_start)
        val endDate: AppCompatEditText = dialogLayout.findViewById(R.id.edit_date_end)
        val acepted_btn: AppCompatButton = dialogLayout.findViewById(R.id.date_btn_Acepted)
        val canceled_btn: AppCompatButton = dialogLayout.findViewById(R.id.date_btn_Canceled)


        val pattern = "dd-MM-yyyy" // format date
        acepted_btn.setOnClickListener {
            val startDateStr = startDate.text.toString().trim()
            val endDateStr = endDate.text.toString().trim()

            val datestartTxt = findViewById<AppCompatTextView>(R.id.startTimeTxt)
            val dateendTxt = findViewById<AppCompatTextView>(R.id.endTimeTxt)

            // Check start-day format
            if (isValidDateFormat(startDateStr)) {
                startDay_TimeStamp = stringToTimestamp(startDateStr, pattern)
            } else {
                Toast.makeText(this, "Ngày tháng phải được đúng định dạng dd-MM-yyyy. Mời nhập lại", Toast.LENGTH_SHORT).show()
                datestartTxt.text = "01-01-1111" // Xóa nội dung EditText để người dùng nhập lại
            }

            // Check end-day format
            if (isValidDateFormat(endDateStr)) {
                endDay_TimeStamp = stringToTimestamp(endDateStr, pattern)
            } else {
                Toast.makeText(this, "Ngày tháng phải được đúng định dạng dd-MM-yyyy. Mời nhập lại", Toast.LENGTH_SHORT).show()
                dateendTxt.text = "02-02-2222"// Xóa nội dung EditText để người dùng nhập lại
            }

            // check time is correct
            if (endDay_TimeStamp >= startDay_TimeStamp) {
                // Nếu hợp lệ, lưu các timestamp và hiển thị ngày tháng
                datestartTxt.text = startDateStr
                dateendTxt.text = endDateStr
            } else {
                // Nếu không hợp lệ, hiển thị thông báo lỗi
                Toast.makeText(this, "Thời gian kết thúc phải sau thời gian bắt đầu. Mời nhập lại", Toast.LENGTH_SHORT).show()
                // Đặt lại nội dung của EditText để người dùng nhập lại
                datestartTxt.text = "01-01-1111"
                dateendTxt.text = "02-02-2222"
            }
            alertDialog.dismiss()
        }

        canceled_btn.setOnClickListener {
            // get default data
            val datestartTxt = findViewById<AppCompatTextView>(R.id.startTimeTxt)
            val dateendTxt = findViewById<AppCompatTextView>(R.id.endTimeTxt)

            datestartTxt.text = "01-01-1111"
            dateendTxt.text = "02-02-2222"

            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    fun stringToTimestamp(inputString: String, pattern: String): Long {
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val date = dateFormat.parse(inputString)
        return date?.time ?: 0
    }

    private fun openGallery_takeImages(images: MutableList<Uri>, adapter: images_Gallery_Adapter) {
        val galleryImages = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            // Thêm các hình ảnh được chọn vào danh sách và cập nhật RecyclerView
            images.addAll(uris)
            adapter.notifyDataSetChanged()
        }

        val openGallery : AppCompatButton = findViewById(R.id.openGallary)
        // Xử lý sự kiện khi người dùng nhấn nút mở thư viện
        openGallery.setOnClickListener {
            galleryImages.launch("image/*")
        }
    }


    fun isValidDateFormat(dateStr: String): Boolean {
        val regex = Regex("""\d{2}[-/]\d{2}[-/]\d{4}""")
        return regex.matches(dateStr)
    }

    fun saveGroupDataToSharedPreferences(
        startDayTimeStamp: Long,
        endDayTimeStamp: Long,
        menuList: List<Uri>,
        sliderList: List<Uri>
    ) {

        // shared references get ID
        // shared references value
        sharedPreferences = getSharedPreferences("Even_ID", MODE_PRIVATE)

        // get data from shared references
        val storageID = sharedPreferences.getString("nodeIDEvent","1")

        // get string list from
        val stringListMenu = menuList.map { it.toString() } // Chuyển đổi Uri thành String
        val stringListSlider = sliderList.map { it.toString() } // Chuyển đổi Uri thành String


        // Tạo một đối tượng JSON
        val jsonObject = JSONObject()
        jsonObject.put("storageID", storageID)
        jsonObject.put("nodeIDEvent", storageID)
        jsonObject.put("startDayTimeStamp", startDayTimeStamp)
        jsonObject.put("endDayTimeStamp", endDayTimeStamp)
        jsonObject.put("menuList", stringListMenu)
        jsonObject.put("sliderList", stringListSlider)



        // Chuyển đổi đối tượng JSON thành chuỗi
        val jsonString = jsonObject.toString()



        // use shared references to send it
        sharedPreferences = getSharedPreferences("JsonString_Final", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("Jsonstring_$storageID", jsonString) // give with storageID
        editor.apply()

        //print it to log file
        // Lấy SharedPreferences
        val sharedPreferences = getSharedPreferences("JsonString_Final", Context.MODE_PRIVATE)

// Lấy chuỗi JSON từ SharedPreferences
        val jsonString2 = sharedPreferences.getString("Jsonstring", "")

// In ra Log để kiểm tra giá trị
        Log.d("SharedPreferences", "Jsonstring value: $jsonString2")
    }
}