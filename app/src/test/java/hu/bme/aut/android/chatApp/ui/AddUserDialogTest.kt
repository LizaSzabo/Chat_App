package hu.bme.aut.android.chatApp.ui

import android.widget.EditText
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class AddUserDialogTest{

    private var dialog: AddUserDialog? = null
    private var et: EditText? = null

    @Before
    fun setUp() {
        dialog = AddUserDialog()
    }

    @Test
    fun notValidUserInput() {
        val userName = "User1"
        val code = "1"
        val result = dialog?.validateNewUser(userName, et, code)
        assertEquals(false, result)
    }
/*
    @Test
    fun notValidInput() {
        val actualPasswordText = "pass"
        val editPasswordText = "pass2"
        val confirmedPassText = "pass"
        val result = dialog?.validateInput( et, et, et, actualPasswordText, editPasswordText, confirmedPassText)
        assertEquals(false, result)
    }*/
}