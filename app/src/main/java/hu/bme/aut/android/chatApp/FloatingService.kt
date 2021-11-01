package hu.bme.aut.android.chatApp

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import hu.bme.aut.android.chatApp.ChatApplication.Companion.newMessage
import hu.bme.aut.android.chatApp.ChatApplication.Companion.newMessagePicture
import hu.bme.aut.android.chatApp.Network.observeConversations
import hu.bme.aut.android.chatApp.Network.observeNewMessages
import java.util.*

class FloatingService : Service() {

    private var enabled = true
    private var windowManager: WindowManager? = null
    private var floatingView: View? = null
    private var tvMessage: TextView? = null
    private var ivConversation: ImageView? = null
    private var actualContent: String = "..."

    companion object {
        private const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "ForegroundServiceChannel"

    }

    private inner class MyMessageShower : Thread() {
        override fun run() {
            val h = Handler(this@FloatingService.mainLooper)
            while (enabled) {
                h.post {
                    if (newMessage != actualContent) {
                        floatingView?.visibility = View.VISIBLE
                        updateNotification(newMessage)
                        actualContent = newMessage
                    }
                }
                try {
                    sleep(5000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        floatingView = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.float_layout, null)

        tvMessage = floatingView?.findViewById(R.id.tvMessage)
        ivConversation = floatingView?.findViewById(R.id.ivConversationImage)
        floatingView?.visibility = View.INVISIBLE

        val layoutFlag: Int = if (Build.VERSION.SDK_INT >= VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            WindowManager.LayoutParams.TYPE_PHONE;
        }

        Log.i("flag", layoutFlag.toString())
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.LEFT
        params.x = 0
        params.y = 100

        windowManager?.addView(floatingView, params)

        floatingView?.setOnTouchListener(object : View.OnTouchListener {
            private var initialX: Int = 0
            private var initialY: Int = 0
            private var initialTouchX: Float = 0.toFloat()
            private var initialTouchY: Float = 0.toFloat()

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                    }
                    MotionEvent.ACTION_UP -> {
                        if (params.x < 50 && params.y < 50) floatingView?.visibility = View.INVISIBLE
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()
                        windowManager!!.updateViewLayout(floatingView, params)
                    }
                }
                return false
            }
        })

        //observeNewMessages()
        //observeConversations()
    }

    override fun onDestroy() {
        enabled = false
        if (floatingView != null) windowManager?.removeView(floatingView)
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        enabled = true
        startForeground(NOTIFICATION_ID, createNotification("Waiting for messages..."))
        MyMessageShower().start()
        updateNotification(newMessage)

        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotification(text: String): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK

        createNotificationChannel()

        val contentIntent = PendingIntent.getActivity(
            this,
            NOTIFICATION_ID,
            notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Chat App")
            .setContentText(text)
            .setSmallIcon(R.drawable.logo)
            .setVibrate(longArrayOf(1000, 2000, 1000))
            .setContentIntent(contentIntent)
            .build()
    }

    fun updateNotification(text: String) {
        tvMessage?.text = newMessage
        ivConversation?.setImageBitmap(newMessagePicture)
        val notification = createNotification(text)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }


}