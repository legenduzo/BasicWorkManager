package org.evolvedigital.basicworkmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import org.evolvedigital.basicworkmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    val workManager = WorkManager.getInstance(this)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startWorkBtn.setOnClickListener {
//            val workRequest = OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
//            val data = Data.Builder()
//                .putString("WORK_MESSAGE", "Work Completed!")
//                .build()
            val data = workDataOf("WORK_MESSAGE" to "Work Completed!")
            val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>()
                .setInputData(data)
                .build()
            val periodicworkRequest = PeriodicWorkRequestBuilder<SimpleWorker>(
                5, TimeUnit.MINUTES,
            1, TimeUnit.MINUTES
            ).build()
            workManager.enqueue(workRequest)
        }

        binding.workStatusBtn.setOnClickListener {
            val toast = Toast.makeText(this, "The work status is ${WorkStatusSingleton.workMessage}", Toast.LENGTH_SHORT)
            toast.show()
        }

        binding.resetStatusBtn.setOnClickListener {
            WorkStatusSingleton.workComplete = false
        }

        binding.uiThreadBtn.setOnClickListener {
            Thread.sleep(10000)
            WorkStatusSingleton.workComplete = true
        }
    }
}