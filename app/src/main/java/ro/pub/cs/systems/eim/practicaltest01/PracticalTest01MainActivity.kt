package ro.pub.cs.systems.eim.practicaltest01

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class PracticalTest01MainActivity : AppCompatActivity() {

    private var firstNumber = 0
    private var secondNumber = 0

    private lateinit var fTextView: TextView
    private lateinit var sTextView: TextView

    private val intentFilter = IntentFilter()
    private val messageBroadcastReceiver = MessageBroadcastReceiver()

    private var serviceRunning = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_main)

        fTextView = findViewById(R.id.editTextText6)
        sTextView = findViewById(R.id.editTextText7)

        fTextView.text = firstNumber.toString()
        sTextView.text = secondNumber.toString()

        // Restore the saved state if available
        if (savedInstanceState != null) {
            if(savedInstanceState.containsKey("firstNumber")){
                firstNumber = savedInstanceState.getInt("firstNumber")
                fTextView.text = firstNumber.toString()
            }else{
                fTextView.text = "0"
            }

            if(savedInstanceState.containsKey("secondNumber")){
                secondNumber = savedInstanceState.getInt("secondNumber")
                sTextView.text = secondNumber.toString()
            }else{
                sTextView.text = "0"
            }

            Log.d("PT", "onRestoreInstanceState() -> savedInstanceState != null" +
                    "\n\tfn:$firstNumber\tsn:$secondNumber")
        }else{
            Log.d("PT", "onRestoreInstanceState() -> savedInstanceState == null")
            fTextView.text = "0"
            sTextView.text = "0"
        }

        // Adding the actions in intent filter
        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01.action1")
        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01.action2")
        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01.action3")
        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01.action4")

    }

    // Save the state of the activity
    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("PT", "onSaveInstanceState()\n\tfn:$firstNumber\tsn:$secondNumber")
        super.onSaveInstanceState(outState)
        outState.putInt("firstNumber", firstNumber)
        outState.putInt("secondNumber", secondNumber)
    }

    // Restore the saved state
    @SuppressLint("SetTextI18n")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        if(savedInstanceState.containsKey("firstNumber")){
            firstNumber = savedInstanceState.getInt("firstNumber")
            fTextView.text = firstNumber.toString()
        }else{
            fTextView.text = "0"
        }

        if(savedInstanceState.containsKey("secondNumber")){
            secondNumber = savedInstanceState.getInt("secondNumber")
            sTextView.text = secondNumber.toString()
        }else{
            sTextView.text = "0"
        }
        Log.d("PT", "onRestoreInstanceState()\n\tfn:$firstNumber\tsn:$secondNumber")
    }

    @SuppressLint("SetTextI18n")
    fun btnPressMe(view: View) {
        firstNumber++
        fTextView.text = firstNumber.toString()
    }
    @SuppressLint("SetTextI18n")
    fun btnPressMeToo(view: View) {
        secondNumber++
        sTextView.text = secondNumber.toString()
    }

    fun navigateTo(view: View) {
        startService()

        val intent = Intent(
            this,
            PracticalTest01SecondaryActivity::class.java
        )
        intent.putExtra("firstNumber", firstNumber)
        intent.putExtra("secondNumber", secondNumber)
        startActivityForResult(intent, 0)
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("PT", "onActivityResult() -> requestCode: $requestCode, resultCode: $resultCode")

        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "The activity returned with OK", Toast.LENGTH_LONG).show()
        }

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "The activity returned with CANCELED", Toast.LENGTH_LONG).show()
        }
    }

    // Register the broadcast receiver
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        registerReceiver(messageBroadcastReceiver, intentFilter, RECEIVER_EXPORTED)
    }

    // Unregister the broadcast receiver
    override fun onPause() {
        unregisterReceiver(messageBroadcastReceiver)
        super.onPause()
    }

    private fun startService() {

        if(!serviceRunning and (firstNumber + secondNumber >= 0)){
            serviceRunning = true

            val intent = Intent(this, PracticalTest01Service::class.java)
            intent.putExtra("firstNumber", firstNumber)
            intent.putExtra("secondNumber", secondNumber)
            startService(intent)
        }
    }

    override fun onDestroy() {
        serviceRunning = false
        stopService(Intent(this, PracticalTest01Service::class.java))
        super.onDestroy()
    }
}