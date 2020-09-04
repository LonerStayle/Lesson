package com.example.personallessonone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

//메인 액티비티
class MainActivity : AppCompatActivity() {
    //생명주기에 따라 onCrete로 시작
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
                intent.putExtra("mySalary",mySalary.toInt())
                startActivity(intent)
            }
        }

//        Toast.makeText(this,"온 크레이트 상태입니다.",Toast.LENGTH_SHORT).show()
val setList:Map<Int,Int> = mapOf(1 to 1, 2 to 2 )
    }

    override fun onStart() {
//        Toast.makeText(this,"온 스타트 상태입니다.",Toast.LENGTH_SHORT).show()
        super.onStart()
    }

    override fun onResume() {
//        Toast.makeText(this,"온 리슘 상태입니다.",Toast.LENGTH_SHORT).show()
        super.onResume()
    }

    override fun onPause() {
//        Toast.makeText(this,"온 퍼즈 상태입니다.",Toast.LENGTH_SHORT).show()
        super.onPause()
    }

    override fun onStop() {
//        Toast.makeText(this,"온 스탑 상태입니다.",Toast.LENGTH_SHORT).show()
        super.onStop()
    }

    override fun onDestroy() {
//        Toast.makeText(this,"온 데스토리 상태입니다.",Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }
}
