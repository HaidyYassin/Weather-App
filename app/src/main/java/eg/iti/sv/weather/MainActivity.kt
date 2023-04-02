package eg.iti.sv.weather

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import eg.iti.sv.weather.utils.createAppSettings
import eg.iti.sv.weather.utils.getCustomizedSettings


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.hide()


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({
            val i = Intent(
                this@MainActivity,
                HomeActivity::class.java
            )
            startActivity(i)
            finish()
        }, 3500)



    }

}