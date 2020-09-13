package com.example.recyclerviewtraining

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.viewholder.view.*


/**
 * readMe:
 * 리사이클러뷰는 어댑터라고 따로 꽂아야되는게 있습니다.
 * 리사이클러뷰 안에 아래 데이터를 함께 포함되어있으면 괜히 무거워지고 메모리 낭비라서 일부로
 * 따로 떼놓구 합체하는거라고 합니다
 * 그래서 아래 어댑터라는 클래스를 따로 만드는겁니다.
 *
 * (엄청 간단하게 설명드린거라 자세히 파고들면 내용이 다를수 있어요)
 *
 * 이 클래스에 ReclerView.Adapter를 상속하는순간 이 클래스는 어댑터가 되는것이지요
 *
 * 4. class Adapter():RecyclerView.Adapter<Adapter.ViewHolder>() 까지 쳐봅시다.
 * 그럼 Adapter() 아래가 빨개질겁니다. 해당 컨트롤 엔터 누른후에  엔터를 한번더 누르면 작은 창이 뜨면서
 * 구현해야할 함수들이 뜹니다.
 * 모두 체크하고 엔터 누르면 3개의 함수가 자동적으로 생성됩니다.
 *
 * 그 이유는 RecyclerView.Adpater<>() 이자식이 기본적으로 추상 클래스라서 안드로이드 만든 사람들이
 * 이거 쓸거면 꼭 이 함수 불러오도록 설정을 해놓은겁니다. 강제로 쓰셈! 하고요
 *
 *5. 그후에 inner class ViewHolder(view:View): RecyclerView.ViewHolder(view) 를 적습니다.
 *
 *6. class Adapter() 여기 () 안에 var numberList:List<Int> = listOf()를 적습니다
 * 그 다음 아래에있는 7번을 참고해주세요
 *
 */


class Adapter(
    var numberList: List<Int> = listOf()
) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder, parent, false)
        )
    }

    override fun getItemCount(): Int {
      return numberList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.textView_number.text =  numberList[position].toString()
    }
    /**
     *  7.  각각 3개의 함수안에 코드를 채워줍시다.
     *
     * 7-1 onCreateViewHolder 함수는 리사이클러뷰 안에있는 아이템을 어떤걸로 쓸건지 정하는겁니다.
     * 아까 만들었던 viewholder.xml 를 가져올거라서 보시면 코드안에 R.layout.viewholder가 있습니다. 나머지 코드는
     * 나중에 이해해도 괜찮습니다.
     *
     * 7-2 getItemCount는 위에서 어떤 아이템을 쓸건지 정했자나요? 그럼 리사이클러뷰 안에 아이템을 몇개나 사용할건지 묻는겁니다.
     *
     * numberList안에 나중에 값을 채워서 그 갯수만큼 자동적으로 아이템 갯수가 생성되게끔
     * numberList.size를 넘겨줍시다.
     *
     * 7-3 onBindViewHolder는 아까 7-1에서 아이템을 어떤걸 쓸건지 선택했잔아요?
     * 여기선 그 선택한 아이템 안에 어떤 값을 줄건지 넣는거에영
     * 아까 만들었던 viewholder.xml 에 textView안에 numberList 안에있는 값들을 싸그리 넘길겁니다.
     * holder.itemView~ 뭐시기를 적어줍시다.
     * holder는 7-1에서 선택했던 ViewHolder.xml를 뜻하합니다.
     *
     * 여기서 그외 itemView나 position은 만지다보면 나중에 아실거에영 설명길어지니 이건 패스
     * 다시 메인액티비티로 넘어갑니다.
     */

}