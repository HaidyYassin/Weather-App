package eg.iti.sv.weather.alerts.view

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import eg.iti.sv.weather.R
import eg.iti.sv.weather.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAlarmBinding
    var mediaPlayer: MediaPlayer? = null
    private lateinit var alertMsg:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        if(AlertsFragment.weather.alerts?.get(0)?.description.toString()=="null")
            alertMsg ="No Alerts Right Now"
        else
            alertMsg=AlertsFragment.weather.alerts?.get(0)?.description.toString()

        val  msg =AlertsFragment.weather.current.weather.get(0).description.toString()+" ,"+alertMsg
        binding.alertTempTxt.text = msg

        window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.addFlags(
            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
        )
        setFinishOnTouchOutside(false)

        binding.dismissBtn.setOnClickListener(){
            mediaPlayer!!.stop()
            this.finish()
        }
        playMusic()
    }

    fun playMusic(){
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music)
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.start()
        }

    }
}