package com.bjit.alarmmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TimePicker
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var btnSetAlarm: Button
    lateinit var timePicker: TimePicker
    lateinit var listViewAlarmTimes: ListView
    private lateinit var alarmHelper: AlarmHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Alarm App"
        timePicker = findViewById(R.id.timePicker)
        btnSetAlarm = findViewById(R.id.buttonAlarm)
        listViewAlarmTimes = findViewById(R.id.listViewAlarmTimes)
        alarmHelper = AlarmHelper(this)

        btnSetAlarm.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                timePicker.hour,
                timePicker.minute,
                0
            )
            alarmHelper.setAlarm(calendar.timeInMillis)
            displayAlarmTimes()
        }

        displayAlarmTimes()
    }

    private fun displayAlarmTimes() {
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val alarmTimes = alarmHelper.getAlarmTimes().mapIndexed { index, timeInMilliss ->
            val calendar = Calendar.getInstance().apply { timeInMillis = timeInMillis }
            "Alarm ${index + 1}: ${dateFormat.format(calendar.time)}"
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, alarmTimes)
        listViewAlarmTimes.adapter = adapter
    }
}