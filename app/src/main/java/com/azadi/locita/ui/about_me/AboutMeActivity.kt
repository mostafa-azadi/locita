package com.azadi.locita.ui.about_me

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.azadi.locita.R
import kotlinx.android.synthetic.main.activity_about_me.*

class AboutMeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)

        title = "About Me"
        txtAboutMe.text =
            "Fullname :   Motstafa Azadi \n\n" +
                    "Email :    mostafa.azadi@live.com \n\n" +
                    "Stackoverflow :    user:9710197 \n\n" +
                    "github :       mostafa-azadi"
    }
}
