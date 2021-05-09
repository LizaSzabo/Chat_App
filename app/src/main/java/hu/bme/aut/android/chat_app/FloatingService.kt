package hu.bme.aut.android.chat_app

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
import androidx.core.content.ContextCompat
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chat_app.ChatApplication.Companion.messageText
import hu.bme.aut.android.chat_app.ChatApplication.Companion.update
import java.util.*

class FloatingService : Service() {

    private var enabled = true
    private var windowManager: WindowManager? = null
    private var floatingView: View? = null
    private var tvMessage: TextView? = null
    private var ivConversation: ImageView? = null

    companion object{
        private const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "ForegroundServiceChannel"

    }

    private inner class MyMessageShower : Thread() {
        override fun run() {
            val h = Handler(this@FloatingService.getMainLooper())
            while (enabled) {
                h.post {
                    if(!currentConversation?.messages.isNullOrEmpty()){
                        tvMessage?.text = currentConversation?.messages?.lastIndex?.let {
                        currentConversation?.messages?.elementAt(
                            it
                        )?.content.toString()
                    }
                        floatingView?.visibility = View.VISIBLE
                      //  updateNotification( tvMessage?.text.toString())
                }
                    else floatingView?.visibility = View.INVISIBLE
                    ivConversation?.setImageBitmap(currentConversation?.picture)

                    if(update){
                       updateNotification(messageText)
                        update = false
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

    override fun onCreate() {
        super.onCreate()

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        floatingView = (getSystemService(
            LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
            R.layout.float_layout, null)

        tvMessage = floatingView?.findViewById<TextView>(R.id.tvTime)
      //  tvMessage?.setText( currentConversation?.messages?.lastIndex.toString())

        ivConversation = floatingView?.findViewById(R.id.ivConversationImage)
        ivConversation?.setImageBitmap(currentConversation?.picture)

        floatingView?.visibility = View.INVISIBLE

        val LAYOUT_FLAG: Int
        if (Build.VERSION.SDK_INT >= VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        Log.i("flag", LAYOUT_FLAG.toString())
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            LAYOUT_FLAG,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)

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
        if(!currentConversation?.messages.isNullOrEmpty()) {
            updateNotification(currentConversation?.messages?.lastIndex?.let {
                currentConversation?.messages?.elementAt(
                    it
                )
            }?.content.toString())

        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotification(text: String): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK

        createNotificationChannel()

        val contentIntent = PendingIntent.getActivity(this,
            NOTIFICATION_ID,
            notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Chat App")
            .setContentText(text)
            .setSmallIcon(R.drawable.logo)
            .setVibrate(longArrayOf(1000, 2000, 1000))
            .setContentIntent(contentIntent)
            .build()
    }

    fun updateNotification(text: String) {
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