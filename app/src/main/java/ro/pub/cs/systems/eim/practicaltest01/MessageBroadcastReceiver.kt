package ro.pub.cs.systems.eim.practicaltest01

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MessageBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("BROADCAST_RECEIVER_TAG", p1?.getStringExtra("BROADCAST_RECEIVER_EXTRA") ?: "no data")
    }
}