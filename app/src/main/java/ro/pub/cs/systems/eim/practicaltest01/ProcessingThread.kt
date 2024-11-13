package ro.pub.cs.systems.eim.practicaltest01

import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Date
import java.util.Random


class ProcessingThread(context: Context, firstNumber: Int, secondNumber: Int) : Thread() {

    private var context: Context? = context
    private var isRunning = true

    private var random: Random = Random()

    private var arithmeticMean = 0.0
    private var geometricMean = 0.0

    private val actions = arrayOf(
        "ro.pub.cs.systems.eim.practicaltest01.action1",
        "ro.pub.cs.systems.eim.practicaltest01.action2",
        "ro.pub.cs.systems.eim.practicaltest01.action3",
        "ro.pub.cs.systems.eim.practicaltest01.action4"
    )

    init {
        this.context = context

        this.arithmeticMean = (firstNumber + secondNumber) / 2.0
        this.geometricMean = Math.sqrt(firstNumber.toDouble() * secondNumber)
    }

    override fun run() {
        while (isRunning) {
            sendMessage()
            sleep()
        }
        Log.d("PROCESSING_THREAD_TAG", "Thread has stopped!")
    }

    private fun sendMessage() {
        val intent = Intent().setAction(actions[random.nextInt(actions.size)])
        intent.putExtra(
            "BROADCAST_RECEIVER_EXTRA",
            Date(System.currentTimeMillis()).toString() + " " + arithmeticMean + " " + geometricMean
        )
        context!!.sendBroadcast(intent)
    }

    private fun sleep() {
        try {
            sleep(1000)
        } catch (interruptedException: InterruptedException) {
            interruptedException.printStackTrace()
        }
    }

    fun stopThread() {
        isRunning = false
    }

}
