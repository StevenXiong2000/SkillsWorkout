package com.example.skillsworkout.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.Objects

object MyWorkoutList {
    val workoutSchedule = mapOf(
        Calendar.MONDAY to Workout("Cardio and Core Strength", listOf(
            Exercise("cardio(running, cycling, or swimming)",30),
            Exercise("core exercises(planks, crunches, leg raises)",15),
            Exercise("stretching and cool down",15)
        )),
        Calendar.TUESDAY to Workout("Upper Body Strength Training", listOf(
            Exercise("upper body weightlifting (bench press, shoulder press, bicepcurls)",20),
            Exercise("bodyweight exercises (push-ups, pull-ups, dips)",20),
            Exercise("stretching and cool down",20)
        )),
        Calendar.WEDNESDAY to Workout("Yoga and Flexibility", listOf(
            Exercise("yoga, focusing on flexibility and balance",60)
        )),
        Calendar.THURSDAY to Workout("Lower Body Strength Training", listOf(
            Exercise("lower body weightlifting (squats, deadlifts, lunges)",20),
            Exercise("bodyweight exercises (squats, lunges, calf raises)",20),
            Exercise("stretching and cool down",20)
        )),
        Calendar.FRIDAY to Workout("Cardio Intervals", listOf(
            Exercise("cardio",30),
            Exercise("core strengthening exercises",15),
            Exercise("stretching and cool down",15)
        ))
    )

    fun getWorkoutDetails(day: Int): Pair<String, String> {
        val workout: Workout? = workoutSchedule.get(day)
        val workoutDetail: String = workout?.let {
            it.getWorkoutDetails()
        } ?: "Empty"
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, day)
        // Use SimpleDateFormat to get the full name of the day of the week
        val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
        val dayName = dateFormat.format(calendar.time)
        return Pair(dayName, workoutDetail)
    }
}