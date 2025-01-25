package com.bjit.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class SnoozeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmHelper = AlarmHelper(context)
        val calendar = Calendar.getInstance().apply {
            add(Calendar.MINUTE, 10) // Snooze for 10 minutes
        }
        alarmHelper.setSnoozeAlarm(calendar.timeInMillis)
    }
}