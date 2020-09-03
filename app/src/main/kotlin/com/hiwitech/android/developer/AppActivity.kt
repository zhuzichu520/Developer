package com.hiwitech.android.developer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hiwitech.android.application.ActivityMain
import com.hiwitech.android.libs.tool.startActivity

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(this, ActivityMain::class.java)
        finish()
    }
}
