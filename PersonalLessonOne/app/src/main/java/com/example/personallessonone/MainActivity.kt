package com.example.personallessonone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button_start.setOnClickListener {

            val nameBox = editText_name.text.toString()
            val mySalary = editText_mySalary.text.toString()

            if (nameBox.isEmpty())
                Toast.makeText(this, "이름을 적어주세요", Toast.LENGTH_SHORT).show()
            else if (mySalary.isEmpty())
                Toast.makeText(this, "한달 월급을 적어주세요", Toast.LENGTH_SHORT).show()
            else {
                val intent = Intent(this, MoneyControlActivity::class.java)
                intent.putExtra("name", nameBox)
                intent.putExtra("mySalary", mySalary.toInt())
                startActivity(intent)
            }

        }
    }
}
