package com.sereginav.android.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import com.sereginav.android.coroutinedemo.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var count: Int = 1
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var seekBar: SeekBar
    private lateinit var statusText : TextView

    suspend fun performTask(taskNumber : Int) : Deferred<String> =
        coroutineScope.async(Dispatchers.Main) {
            delay(5_000)
            return@async "FinishedCoroutine $taskNumber"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        seekBar = findViewById(R.id.seekBar)
        statusText = findViewById(R.id.statusText)
        seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                count = progress
                statusText.text = "$count coroutines"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
            }
        )
    }

    fun launchCoroutines(view : View){
        (1..count).forEach{
            statusText.text = "Started coroutine $it"
            coroutineScope.launch(Dispatchers.Main){
                statusText.text = performTask(it).await()
            }
        }
    }
}