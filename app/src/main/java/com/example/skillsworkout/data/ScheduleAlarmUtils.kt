package com.example.skillsworkout.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.skillsworkout.MyApplication
import com.example.skillsworkout.NotificationReceiver
import java.util.Calendar

object ScheduleAlarmUtils {
    const val MY_WORKOUT_INTENT_KEY = "DAY"

    fun scheduleWeeklyAlarms() {
        val context: Context = MyApplication.instance.applicationContext
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            action = "MY_WORKOUT"
        }

        for (day in Calendar.MONDAY..Calendar.FRIDAY) {
            intent.putExtra(MY_WORKOUT_INTENT_KEY, day)

            val calendar: Calendar = Calendar.getInstance().apply {
                //timeInMillis = System.currentTimeMillis()
                set(Calendar.DAY_OF_WEEK, day)
                set(Calendar.HOUR_OF_DAY, 13)
                set(Calendar.MINUTE, 50)
                set(Calendar.SECOND, 0)
                set(Calendar.SECOND, 0)
            }

            // If the selected time is already passed for this week, move to the next week
            if (System.currentTimeMillis() > calendar.timeInMillis) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                day, // Mon=2 to Fri=6 Using day as requestCode to distinguish alarms for different days
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + day * 5000L, //test case
                //calendar.timeInMillis, //real one
                pendingIntent
            )

        }
    }
}