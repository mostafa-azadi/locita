package com.azadi.locita.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.azadi.locita.R
import com.azadi.locita.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long=3000 // 3 sec


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)

    }
}
