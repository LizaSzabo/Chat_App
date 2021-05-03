package hu.bme.aut.android.chat_app.ui.Login

import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import hu.bme.aut.android.chat_app.MainActivity
import hu.bme.aut.android.chat_app.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginFragmentAndroidTest{

    @get:Rule
    var fragmentScenarioRule = activityScenarioRule<MainActivity>()


    @Test
    fun layoutVisible() {
        onView(withId(R.id.loginFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun notValidLoginInput() {
        val user = "user"
        val password = ""

        onView(withId(R.id.editTextLoginName)).perform(typeText(user))
        onView(withId(R.id.editTextLoginPassword)).perform(typeText(password))

        onView(withId(R.id.button_ok)).perform(click())

        onView(withText("@strings/pass_required")).check(matches(isDisplayed()))
    }
}