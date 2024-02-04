package com.example.skillsworkout

// NotificationReceiver.kt
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.skillsworkout.data.MyWorkoutList
import com.example.skillsworkout.data.NotificationUtils
import com.example.skillsworkout.data.ScheduleAlarmUtils
import java.util.Calendar

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("NotificationReceiver", "NotificationReceiver::onReceive")
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            // Set the alarm here.
            //after reboot device, setup week alarms
            ScheduleAlarmUtils.scheduleWeeklyAlarms()
            return
        }
        if (context != null) {
            intent?.let {
                val day = it.getIntExtra(ScheduleAlarmUtils.MY_WORKOUT_INTENT_KEY, 0)
                val r = MyWorkoutList.getWorkoutDetails(day)
                Log.i("NotificationReceiver", "Action:${it.action}, Day:${r.first}, Deatail:${r.second}")
                NotificationUtils.showNotification(r.first, r.second
                )
                if(day == Calendar.FRIDAY) {
                    //setup next week alarms
                    ScheduleAlarmUtils.scheduleWeeklyAlarms()
                }
            }
        }
    }
}
