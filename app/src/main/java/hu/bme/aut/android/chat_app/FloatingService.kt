package hu.bme.aut.android.chat_app

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import hu.bme.aut.android.chat_app.ChatApplication.Companion.currentConversation
import java.util.*

class FloatingService : Service() {

    private var enabled = true
    private var windowManager: WindowManager? = null
    private var floatingView: View? = null
    private var tvMessage: TextView? = null
    private var ivConversation: ImageView? = null

    private inner class MyTimeShower : Thread() {
        override fun run() {
            val h = Handler(this@FloatingService.getMainLooper())
            while (enabled) {
                h.post { tvMessage?.text = currentConversation?.messages?.lastIndex?.let {
                    currentConversation?.messages?.elementAt(
                        it
                    )?.content.toString()
                }
                    ivConversation?.setImageBitmap(currentConversation?.picture)
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
        tvMessage?.setText( currentConversation?.messages?.lastIndex.toString())

        ivConversation = floatingView?.findViewById(R.id.ivConversationImage)
        ivConversation?.setImageBitmap(currentConversation?.picture)

        val LAYOUT_FLAG: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE
        }

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
        MyTimeShower().start()
        return super.onStartCommand(intent, flags, startId)
    }
}