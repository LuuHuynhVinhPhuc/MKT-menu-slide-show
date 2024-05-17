package choose_options

import admin_Settings.API.api_get_link
import admin_Settings.schedule_foods.showEvent.schedule_Food_items
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.menu_mkt.R
import com.example.myapp.alert.alertDialog_OK
import slideshow.autoSlider.sliderView
import slideshow.menuSlider.menuSlider

class choose_options : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_choose_options)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // sign in page
        // find cancel button
        val cancelBtn = findViewById<Button>(R.id.buttonCancel)
        // add an event for btn : turn back to sign in page
        cancelBtn.setOnClickListener {
            val i = Intent(this, menuSlider::class.java)
            startActivity(i)
        }
        // admin page
        // find button
        val adminBtn = findViewById<Button>(R.id.buttonsetting)
        // add event for btn : go to setting page
        adminBtn.setOnClickListener {
//            val i = Intent(this, api_get_link::class.java)
//            startActivity(i)

            alertDialog_OK.showCustomAlertDialog(this,"Hệ thống đang cập nhật","")

        }
        // optimize page
        // find button
        val optimizeBtn = findViewById<Button>(R.id.buttonOptimize)
        // add event for btn : go to optimize page
        optimizeBtn.setOnClickListener {
            val i = Intent(this, schedule_Food_items::class.java)
            startActivity(i)
        }
    }
}