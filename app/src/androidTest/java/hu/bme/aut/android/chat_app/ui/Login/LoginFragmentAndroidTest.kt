package hu.bme.aut.android.chat_app.ui.Login

import android.graphics.BitmapFactory
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import hu.bme.aut.android.chat_app.ChatApplication.Companion.usersList
import hu.bme.aut.android.chat_app.MainActivity
import hu.bme.aut.android.chat_app.Model.User
import hu.bme.aut.android.chat_app.R
import hu.bme.aut.android.chat_app.ui.AddConversationDialog
import hu.bme.aut.android.chat_app.ui.Messages.MessagesFragment
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginFragmentAndroidTest{

    @get:Rule
    var fragmentScenarioRule = activityScenarioRule<MainActivity>()

    @get:Rule
    var mActivityRule: ActivityTestRule<*> = ActivityTestRule(
        MainActivity::class.java
    )

    private var fragment: MessagesFragment? = null
    private var dialog: AddConversationDialog? = null

    @Before
    fun setUp() {
       fragment = MessagesFragment()
        dialog =AddConversationDialog()
    }

    @Test
    fun layoutVisible() {
        onView(withId(R.id.loginFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun notValidLoginInput() {
        val user = "user"
        val password = "aaa"

        onView(withId(R.id.editTextLoginName)).perform(typeText(user))
        onView(withId(R.id.editTextLoginName)).perform(closeSoftKeyboard())
        onView(withId(R.id.editTextLoginPassword)).perform(typeText(password))
        onView(withId(R.id.editTextLoginPassword)).perform(closeSoftKeyboard())

        onView(withId(R.id.button_ok)).perform(click())

        onView(withText("Wrong User Name or Password")).check(matches(isDisplayed()))
    }

    @Test
    fun iterateSpinnerItems() {
        val myArray: Array<String> = mActivityRule.activity.resources
            .getStringArray(R.array.languages)

        onView(withId(R.id.spinner_languages)).perform(click())
        onData(`is`(myArray[0])).perform(click())
        onView(withId(R.id.login_title)).check(matches(withText("LOGIN")))

        onView(withId(R.id.spinner_languages)).perform(click())
        onData(`is`(myArray[1])).perform(click())
        onView(withId(R.id.login_title)).check(matches(withText("BELÉPÉS")))
    }

    @Test
    fun spinnerChoiceValidator(){
        val myArray: Array<String> = mActivityRule.activity.resources
            .getStringArray(R.array.types)

        val b = BitmapFactory.decodeResource(mActivityRule.activity.resources, R.drawable.addProfile)
        usersList.add(User("User1", "pass", b, null))
        onView(withId(R.id.editTextLoginName)).perform(typeText("User1"))
        onView(withId(R.id.editTextLoginName)).perform(closeSoftKeyboard())
        onView(withId(R.id.editTextLoginPassword)).perform(typeText("pass"))
        onView(withId(R.id.editTextLoginPassword)).perform(closeSoftKeyboard())
        onView(withId(R.id.button_ok)).perform(click())
        onView(withId(R.id.imageButtonWrite)).perform(click())
        onView(withId(R.id.editTextTypeTitle)).check(matches(isDisplayed()))

        for(i in myArray.indices) {
            onView(withId(R.id.spinner_category)).perform(click())
            onData(`is`(myArray[i])).inRoot(isPlatformPopup()).perform(click())
            onView(withId(R.id.editTextTypeTitle)).check(matches(withText(myArray[i])))
        }

    }
}