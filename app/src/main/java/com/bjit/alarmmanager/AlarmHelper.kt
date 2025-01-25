package com.bjit.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast

class AlarmHelper(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("AlarmPrefs", Context.MODE_PRIVATE)


    /**
     * Sets a repeating alarm at the specified time.
     *
     * @param timeInMillis The time in milliseconds at which the alarm should be set.
    */
    fun setAlarm(timeInMillis: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setRepeating(
            AlarmManager.RTC,
            timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        saveAlarmTime(timeInMillis)
        Toast.makeText(context, "Alarm is set", Toast.LENGTH_SHORT).show()
    }

    fun saveAlarmTime(timeInMillis: Long) {
        val editor = sharedPreferences.edit()
        val alarmTimes = getAlarmTimes().toMutableList()
        alarmTimes.add(timeInMillis)
        editor.putStringSet("alarmTimes", alarmTimes.map { it.toString() }.toSet())
        editor.apply()
    }

    fun getAlarmTimes(): List<Long> {
        val alarmTimes = sharedPreferences.getStringSet("alarmTimes", emptySet())
        return alarmTimes?.map { it.toLong() } ?: emptyList()
    }

    fun setSnoozeAlarm(timeInMillis: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            timeInMillis,
            pendingIntent
        )
        Toast.makeText(context, "Snooze is set", Toast.LENGTH_SHORT).show()
    }
}