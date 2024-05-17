package com.example.myapp.alert

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.menu_mkt.R
import slideshow.menuSlider.menuSlider

object alertDialog_OK {
    fun showCustomAlertDialog(context: Context, title: String, message: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.alert_ok, null)

        val dialogTitle = dialogView.findViewById<TextView>(R.id.alert_title)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.alert_message)
        dialogTitle.text = title
        dialogMessage.text = message

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(false) // Ngăn người dùng đóng AlertDialog bằng cách chạm ra ngoài
            .show()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // Đặt nền trong suốt cho dialog

        val customButton = dialogView.findViewById<AppCompatButton>(R.id.btn_AceptedOk)
        customButton.setOnClickListener {
            // Xử lý sự kiện khi button được nhấn
            dialog.dismiss() // Đóng AlertDialog
        }
    }
}