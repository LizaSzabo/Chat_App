package hu.bme.aut.android.chat_app.ui

import android.widget.EditText
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ChangePassDialogTest{

        private var dialog: ChangePassDialog? = null
        private var et: EditText? = null

        @Before
        fun setUp() {
            dialog =  ChangePassDialog()
        }

        @Test
        fun validInput() {
            val actualPasswordText = "pass"
            val editPasswordText = "pass2"
            val confirmedPassText = "pass2"
            val result = dialog?.validateInput( et, et, et, actualPasswordText, editPasswordText, confirmedPassText)
            assertEquals(true, result)
        }

        @Test
        fun notValidInput() {
            val actualPasswordText = "pass"
            val editPasswordText = "pass2"
            val confirmedPassText = "pass"
            val result = dialog?.validateInput( et, et, et, actualPasswordText, editPasswordText, confirmedPassText)
            assertEquals(false, result)
        }
    }