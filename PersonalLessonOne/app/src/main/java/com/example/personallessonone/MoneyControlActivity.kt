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


    private val yearList: MutableList<String> = mutableListOf()
    private val monthList: MutableList<String> = mutableListOf()
    private val dayList: MutableList<String> = mutableListOf()


    private var mySalaryYear: Int? = null
    private var mySalaryMonth: Int? = null
    private var mySalaryDay: Int? = null


    private var totalMoney = 0


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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moneycontrol)

        val mySalary = setData()
        numberPickerSetting()
        setButtonSaveMoneyClickListener()
        buttonCalculationClcikListener(mySalary)
    }


    private fun buttonCalculationClcikListener(mySalary: Int) {
        button_calculation.setOnClickListener {
            numberPickerValueSelect()
            dialog(mySalary)
        }
    }


    private fun setData(): Int {


        val name = intent.getStringExtra("name")
        val mySalary = intent.getIntExtra("mySalary", 0)
        textView_user.text = name
        return mySalary
    }

    private fun dialog(mySalary: Int) {


        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("${mySalaryYear}년 ${mySalaryMonth}월 ${mySalaryDay}일 순이익")
        dialog.setMessage("${mySalary - totalMoney}원")
        dialog.setPositiveButton("확인했습니다.") { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
        dialog.create()
        dialog.show()

    }

    @SuppressLint("SetTextI18n")
    private fun setButtonSaveMoneyClickListener() {
        button_saveMoney.setOnClickListener {

            val amountUsed = editText_amountUsed.text.toString()
            if (amountUsed.isEmpty()) {
                Toast.makeText(baseContext, "금액을 적어주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val time = SimpleDateFormat("MM월 dd일 사용:", Locale.KOREA).format(Date())
            val textView = TextView(this)
            textView.text = "$time${amountUsed}원"

            linearLayout_Accounting.addView(textView, 0)
            totalMoney += amountUsed.toInt()
            editText_amountUsed.setText("")
        }
    }



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
