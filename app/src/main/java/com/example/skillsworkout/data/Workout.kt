package com.example.skillsworkout.data

data class Workout(
    val workoutName: String,
    val exercises: List<Exercise>
) {
    fun getWorkoutDetails(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(workoutName).append("\n")
        exercises.forEach {
            stringBuilder.append("${it.exercise} ${it.duration} minutes").append("\n")
        }
        return stringBuilder.toString()
    }
}