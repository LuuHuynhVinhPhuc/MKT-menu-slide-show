package signin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.EditorInfo
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import choose_options.choose_options
import com.example.menu_mkt.R
import com.example.myapp.alert.alertDialog_OK
import slideshow.menuSlider.menuSlider

class sign_in : AppCompatActivity() {

    private val DELAY_TIME_MS: Long = 90000 // 1mins 30 seconds
    private lateinit var runnable: Runnable
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        this.setFinishOnTouchOutside(true)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        runnable = Runnable {
            val i = Intent(this, menuSlider::class.java)
            startActivity(i)
            finish()
        }

        // auto change page after 1 mins 30s
        handler.postDelayed(runnable, DELAY_TIME_MS)
        // default password
        val passName = "goldenlotus"

        // set counting for sign in 3 times error
        var countingsign_in = 3
        // event for click btn
        val btnAcept: AppCompatButton = findViewById(R.id.buttonAcp)
        val editTxt: AppCompatEditText = findViewById(R.id.editPassword)

        // set event
        btnAcept.setOnClickListener(){
            // Find EditText (redundant in this case)
            val passwordData: String = editTxt.text.toString().trim()
            if (passName == passwordData) {
                val intent = Intent(this, choose_options::class.java)
                startActivity(intent)
                handler.removeCallbacksAndMessages(null)
                finish()
            } else {
                countingsign_in -= 1
                if (countingsign_in > 0){
                    alertDialog_OK.showCustomAlertDialog(this,"Vui lòng kiểm tra lại mã quản lý của bạn", "Bạn còn $countingsign_in lần đăng nhập")
                    editTxt.setText("")
                }
                else if (countingsign_in == 0){
                    alertDialog_OK.showCustomAlertDialog(this, "Sai mã đăng nhập", "Hệ thống sẽ tự chuyển ra trang chủ")
                    countingsign_in = 3
//                    handler.postDelayed(runnable, DELAY_TIME_MS)
                    val i = Intent(this, menuSlider::class.java)
                    startActivity(i)
                }
            }
        }

        // for enter keyboard
        editTxt.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val passwordData: String = editTxt.text.toString().trim()
                if (passName == passwordData) {
                    val intent = Intent(this, choose_options::class.java)
                    startActivity(intent)
                    handler.removeCallbacksAndMessages(null)
                    finish()
                } else {
                    countingsign_in -= 1
                    if (countingsign_in > 0){
                        alertDialog_OK.showCustomAlertDialog(this,"Vui lòng kiểm tra lại mã quản lý của bạn", "Bạn còn $countingsign_in lần đăng nhập")
                        editTxt.setText("")
                    }
                    else if (countingsign_in == 0){
                        alertDialog_OK.showCustomAlertDialog(this, "Sai mã đăng nhập", "Hệ thống sẽ tự chuyển ra trang chủ")
                        countingsign_in = 3
//                    handler.postDelayed(runnable, DELAY_TIME_MS)
                        val i = Intent(this, menuSlider::class.java)
                        startActivity(i)
                    }
                }
            }
            false
        }

        // for button below
        val cancelBtn2 : AppCompatButton = findViewById(R.id.button_Cancel)
        cancelBtn2.setOnClickListener {
            val i = Intent(this, choose_options::class.java)
            startActivity(i)
            handler.removeCallbacksAndMessages(null)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }
}