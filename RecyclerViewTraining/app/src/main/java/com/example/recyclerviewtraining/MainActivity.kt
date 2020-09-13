package com.example.recyclerviewtraining

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * read Me: 숫자보면서 순서 대로 따라해보세여
         *
         * 0. 새로운 프로젝트를 만듭시다. 이 프로젝트를 보면서 따라해볼거에요
         * 우선 여기 MainActivity 코드란에 절대 아무것도 적지마세요
         *
         * 1. activity_main.xml 으로 넘어가서 리사이클러뷰를 우선 만든다. 아무생각하지말고 그냥 리사이클러뷰를
         * 레이아웃안에 드래그해서 만든다.
         *
         * 2. viewholder.xml를 따라 만듭니다.
         *
         * 3. Adapter라는 클래스를 만듭니다 그럼 저 클래스를 한번봐보세요
         * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
         *
         * 8. **Adapter ** 보고오신후 ++
         * 아까 numberList에 값을 넣을 val list = listOf<Int>(~~) 를 만들어 봅시다.
         * 아까 Adapter에서 numberList.size 쓴거 기억나나요? 그거 덕분에
         * 뭘 적든간에 아이템들이 list안에 있는 갯수에 맞춰서 생성됩니다.
         *
         * 9. recyclerView.adapter = Adapter(list)
         * 이것은 리사이클러뷰의 아까말한 어댑터 장착입니다.
         *
         * 10. recyclerView.layoutManager 여기서 레몬님의 쇼핑이 시작됩니다.
         * GridLayoutManager(), LinearLayoutManager(), StaggeredGridLayoutManager()
         * 세가지중 하나 골라서 써도 되는데 3가지다 매력이 좀 다릅니다 구글링하면 쉽게나와요우
         *
         */
        val list = listOf<Int>(1, 2, 3, 4, 5, 6, 7)
        recyclerView.adapter = Adapter(list)
        recyclerView.layoutManager = GridLayoutManager(this,3)


    }
}