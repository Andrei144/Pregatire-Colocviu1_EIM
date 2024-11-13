package ro.pub.cs.systems.eim.practicaltest01

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class PracticalTest01SecondaryActivity : AppCompatActivity() {

    var fn = 0
    var sn = 0
    lateinit var textView: TextView
    val pre = "Numarul total de apasari este: "
    lateinit var okButton: Button
    lateinit var cancelButton: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_secondary)

        fn = intent.getIntExtra("firstNumber", 0)
        sn = intent.getIntExtra("secondNumber", 0)

        textView = findViewById(R.id.textView2)
        textView.text = pre + (fn + sn).toString()

        okButton = findViewById(R.id.okBtn)
        okButton.setOnClickListener {
            setResult(RESULT_OK, null)
            finish()
        }

        cancelButton = findViewById(R.id.cancelBtn)
        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED, null)
            finish()
        }
    }
}