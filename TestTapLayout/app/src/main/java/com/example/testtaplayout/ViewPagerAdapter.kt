package com.example.testtaplayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

//FragmentPagerAdapter 는 한번 생성된 프레그먼트를 절대 메모리에서 제거하지 않음
//FragmentStatePagerAdapter 는 범위 밖에 프레그먼트를 제거해줌

@Suppress("DEPRECATION")
class ViewPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
         return  when (position) {
            0 -> FragMent_Main()
            1 -> FragMent_1()
            2 -> FragMent_2()
            3 -> FragMent_3()
             else -> FragMent_Main()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> return "one"
            1 -> return "two"
            2 -> return "three"
            3 -> return "four"
            else -> return null
        }
    }
}