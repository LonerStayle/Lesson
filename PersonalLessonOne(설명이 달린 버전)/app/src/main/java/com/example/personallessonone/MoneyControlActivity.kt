package com.example.personallessonone


import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_moneycontrol.*
import java.text.SimpleDateFormat
import java.util.*


class MoneyControlActivity : AppCompatActivity() {

    // 차후에 사용할 변수들을 미리 선언함
    private val yearList: MutableList<String> = mutableListOf()
    private val monthList: MutableList<String> = mutableListOf()
    private val dayList: MutableList<String> = mutableListOf()

    //null은 우선적으로 비어진 상태라고 이해하시면 됨, (더 정확히는 빈곳에 null 값을 임시로 채워놓은거긴 함)
    private var mySalaryYear: Int? = null
    private var mySalaryMonth: Int? = null
    private var mySalaryDay: Int? = null

    //사용한 금액들을 모두 더할 사용 최종 돈.
    private var totalMoney = 0

    //함수 기능을 담은 변수
   private val numberPickerSetting = {
        for (i in 2020..2030) {
            yearList.add("${i}년")
        }
        for (i in 1..12) {
            monthList.add("${i}월")
        }
        for (i in 1..31) {
            dayList.add("${i}일")
        }

        numberPicker_year.apply {
            minValue = 1
            maxValue = 10
            displayedValues = yearList.toTypedArray()
        }

        numberPicker_month.apply {
            minValue = 1; maxValue = 12
            displayedValues = monthList.toTypedArray()
        }
        numberPicker_day.apply {
            minValue = 1; maxValue = 31
            displayedValues = dayList.toTypedArray()
        }

    }

       //onCreate위주로 보시면 됨.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moneycontrol)

        val mySalary = setData()
        numberPickerSetting()
        setButtonSaveMoneyClickListener()
        buttonCalculationClcikListener(mySalary)
    }


    private fun buttonCalculationClcikListener(mySalary: Int) {
        //게산하기 버튼 클릭 이벤트
        button_calculation.setOnClickListener {

            //넘버피커의 값을 이용한 로직 실행
            numberPickerValueSelect()

            //다이얼로그 실행
            dialog(mySalary)
        }
    }

   // 코틀린에서 함수 옆에 :Type 이 적혀있다면 이 구문안에 Type의 최종 값을 이 함수를 호출 했을때 사용하겠다는 의미
    //항상 :Type이 적혀있다면 반환하는 return값이 있음.
    private fun setData(): Int {

        // 불러가져온 인텐트의 값을 변수에 넣음
        val name = intent.getStringExtra("name")
        val mySalary = intent.getIntExtra("mySalary", 0)
        textView_user.text = name

       //위에 Type에 보낼 값
        return mySalary
    }

    private fun dialog(mySalary: Int) {

        //다이얼로그 사용
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("${mySalaryYear}년 ${mySalaryMonth}월 ${mySalaryDay}일 순이익")
        dialog.setMessage("${mySalary - totalMoney}원")

        //setPositiveButton 다이얼로그 화면안에 버튼을 클릭했을때의 이벤트를 지정
        dialog.setPositiveButton("확인했습니다.") { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
        dialog.create()
        dialog.show()

    }

    @SuppressLint("SetTextI18n")
    private fun setButtonSaveMoneyClickListener() {
        button_saveMoney.setOnClickListener {
             //사용한 금액 에딧 텍스트에 적은 글귀를 변수에 담음
            val amountUsed = editText_amountUsed.text.toString()

            /**
             * 비었다면 토스트메시지 띄운후 리턴, onCreate로 다시 올라가고 onCreate안에 이 함수 다음 구문을
            바로 프로그램이 읽음
             */
            if (amountUsed.isEmpty()) {
                Toast.makeText(baseContext, "금액을 적어주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //시스템의 한국 시간을 찾는 클래스
            val time = SimpleDateFormat("MM월 dd일 사용:", Locale.KOREA).format(Date())
            //코드상으로 텍스트뷰를 생성
            val textView = TextView(this)

            /**
             *  &{구문} 혹은 &구문은 문자열안에서 그 구문을 읽어 최종값을 문자열로 뛰우게함
             *  코드로 만든 텍스트뷰 안에 텍스트에 아래 글자를 집어넣어줌.
             */

            textView.text = "$time${amountUsed}원"

            //미리 만들어놓은 속성이 담긴 텍스트 뷰를 사용한 값을 표시할 리니어레이아웃 안에 생성함
            linearLayout_Accounting.addView(textView, 0)

            // 맨 위에 미리 지정해둔 변수 값에 넣어둠 +=를 사용할 경우 계속 변수안에 값이 플러스되어 쌓임
            // totalMoney= totalMoney+amountUsed.toInt() 와 같다고 보시면 됩니다.
            totalMoney += amountUsed.toInt()

            editText_amountUsed.setText("")
        }
    }


    //넘버피커에서 값을 선택했을 때 어떤 값을 보여줄지 짠 로직
    private fun numberPickerValueSelect() {

        var yearCheck = (2020 - 1)
        for (i in 1..10) {
            ++yearCheck
            when (numberPicker_year.value) {
                i -> mySalaryYear = yearCheck
            }
        }

        for (i in 1..12) {
            when (numberPicker_month.value) {
                i -> mySalaryMonth = i
            }
        }

        for (i in 1..31) {
            when (numberPicker_day.value) {
                i -> mySalaryDay = i
            }
        }
    }

}
