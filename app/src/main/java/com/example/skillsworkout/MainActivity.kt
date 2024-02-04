package com.example.skillsworkout

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import androidx.activity.viewModels
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.skillsworkout.ui.theme.SkillsWorkoutTheme
import android.net.Uri
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.lifecycle.Observer
import com.example.skillsworkout.data.ScheduleAlarmUtils
import java.util.Calendar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    private lateinit var ctx: Context
    private lateinit var notifManger: NotificationManagerCompat
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ctx = this
        notifManger = NotificationManagerCompat.from(this)

        setContent {
            val granted by viewModel.permissionGranted.observeAsState()
            SkillsWorkoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val hasPermission = granted ?: false
                    if(hasPermission) {
                        Greeting(hasPermission, action = { resetAlarms() })
                    } else {
                        Greeting(hasPermission, action = { requestPermission() })
                    }
                }
            }
        }
        requestPermission()
    }

    private val notificationPermissionCode = 123 // Choose any code you like
    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED ) {
            viewModel.updatePermission(true)
        } else {
            // Request the permissions
            // Check if the user has denied the permission previously
            viewModel.updatePermission(false)
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                // Show an explanation to the user as to why the permission is needed
                // You can customize the message based on your app's requirements
                // For example, you can show a dialog explaining why the permission is necessary
                showPermissionRationaleDialog()
            } else {
                // No explanation needed, request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    notificationPermissionCode
                )
            }
        }
    }

    private fun showPermissionRationaleDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        // Set the dialog title and message
        alertDialogBuilder.setTitle("SkillsWorkout")
        alertDialogBuilder.setMessage("Need notification permission")

        // Set positive button and its click listener
        alertDialogBuilder.setPositiveButton("OK") { dialog, which ->
            // Do something when the positive button is clicked
            // For example, you can perform an action or dismiss the dialog
            dialog.dismiss()
            openAppNotificationSetting()
        }

        // Set negative button and its click listener (optional)
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->
            // Do something when the negative button is clicked (optional)
            // For example, you can perform an action or dismiss the dialog
            dialog.dismiss()
        }

        // Create and show the AlertDialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun openAppNotificationSetting() {
        val intent = Intent()

        // For newer Android versions, open the app settings using the package name
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.action = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.data = Uri.fromParts("package", packageName, null)
        } else {
            // For older Android versions, open the general system settings
            intent.action = android.provider.Settings.ACTION_APPLICATION_SETTINGS
        }

        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == notificationPermissionCode) {
            // Check if the permissions were granted
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // Permissions granted, proceed with notification-related tasks
                viewModel.updatePermission(true)
            } else {
                // Permissions denied, handle accordingly (e.g., inform the user)
                viewModel.updatePermission(false)
            }
        }
    }

    // Function to show a notification
    fun resetAlarms() {
        ScheduleAlarmUtils.scheduleWeeklyAlarms()
    }
}

@Composable
fun Greeting(permissionGranted: Boolean, action: () -> Unit, modifier: Modifier = Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(23.dp, 0.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Button(onClick = {
            action()
        }) {
            Text(
                text = if(permissionGranted) "Reset Alarms" else "Request Permission",
                fontSize = 20.sp,
                modifier = modifier
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SkillsWorkoutTheme {
        Greeting(true, action = {  })
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    SkillsWorkoutTheme {
        Greeting(false, action = {  })
    }
}