package com.sereginav.android.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.sereginav.android.coroutinedemo.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var count: Int = 1
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    suspend fun performTask(taskNumber : Int) : Deferred<String> =
        coroutineScope.async(Dispatchers.Main) {
            delay(5000)
            return@async "FinishedCoroutine $taskNumber"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        binding.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                count = p1
                binding.countText.text = "$count coroutines"
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
            binding.statusText.text = "Started coroutine $it"
            coroutineScope.launch(Dispatchers.Main){
                binding.statusText.text = performTask(it).await()
            }
        }
    }
}