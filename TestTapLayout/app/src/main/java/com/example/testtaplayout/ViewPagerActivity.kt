package com.example.testtaplayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_viewpager.*

class ViewPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)

        //뷰페이저 세팅
       viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
        tapLayout.setupWithViewPager(viewPager)
    }
}
