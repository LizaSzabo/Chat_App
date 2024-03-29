package hu.bme.aut.android.chatApp.ui

import hu.bme.aut.android.chatApp.ui.addConversation.AddConversationDialog
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AddConversationDialogTest
{
    private var dialog: AddConversationDialog? = null

    @Before
    fun setUp() {
        dialog = AddConversationDialog()
    }

    @Test
    fun notValidInput() {
        val emptyInput = ""
        val result = dialog?.validateInputText(emptyInput)
        assertEquals(false, result)
    }

    @Test
    fun validInput() {
        val inputData = "inputData"
        val result = dialog?.validateInputText(inputData)
        assertEquals(true, result)
    }
}