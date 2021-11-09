package hu.bme.aut.android.chatApp.extensions

import android.graphics.Bitmap
import kotlin.math.roundToInt

fun Bitmap.resizeByHeight(height: Int): Bitmap {
    val ratio: Float = this.height.toFloat() / this.width.toFloat()
    val width: Int = (height / ratio).roundToInt()

    return Bitmap.createScaledBitmap(
        this,
        width,
        height,
        false
    )
}

fun Bitmap.resizeByWidth(width: Int): Bitmap {
    val ratio: Float = this.width.toFloat() / this.height.toFloat()
    val height: Int = (width / ratio).roundToInt()

    return Bitmap.createScaledBitmap(
        this,
        width,
        height,
        false
    )
}