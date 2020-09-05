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

        //버튼 클릭리스너
        button_start.setOnClickListener {
            //에딧 텍스트의 문자를 변수에 담음
            val nameBox = editText_name.text.toString()
            val mySalary = editText_mySalary.text.toString()

            //이름이 적힌 에딧 텍스트가 비어있다면 이 토스트메세지를 실행
            if (nameBox.isEmpty())
                Toast.makeText(this, "이름을 적어주세요", Toast.LENGTH_SHORT).show()
            //월급이 적힌 에딧 텍스트가 비어있다면 이 토스트메세지를 실행
            else if (mySalary.isEmpty())
                Toast.makeText(this, "한달 월급을 적어주세요", Toast.LENGTH_SHORT).show()
            //위에 두가지 해당사항이 아니라면 아래 구문 실행

            else {
                // 인텐트에 현재 장소, 이동할 장소를 () 안에 넣음
                val intent = Intent(this, MoneyControlActivity::class.java)

                // 옮기면서 같이 보낼 값을 담음
                intent.putExtra("name", nameBox)
                intent.putExtra("mySalary",mySalary.toInt())

                // 설정한 인텐트를 실행
                startActivity(intent)
            }
        }

    }


}
