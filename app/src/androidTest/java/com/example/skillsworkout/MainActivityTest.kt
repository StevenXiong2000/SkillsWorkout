package com.example.skillsworkout

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test
import com.example.skillsworkout.MainActivity

class MainActivityTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testWelcomeMessage() {
        val title = "Reset Alarms"
        composeTestRule.setContent {
            Greeting(true, action = {  })
        }

        composeTestRule.onNodeWithText(title, ignoreCase = true).assertIsDisplayed()
    }
}