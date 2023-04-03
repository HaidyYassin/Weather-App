package eg.iti.sv.weather.alerts.view

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import eg.iti.sv.weather.R
import eg.iti.sv.weather.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAlarmBinding
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer()


        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
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