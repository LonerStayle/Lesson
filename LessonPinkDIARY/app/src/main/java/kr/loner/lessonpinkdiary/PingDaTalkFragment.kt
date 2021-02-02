package kr.loner.lessonpinkdiary

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_pingdatalk.*
import kotlinx.android.synthetic.main.fragment_pingdatalk.view.*

class PingDaTalkFragment : Fragment() {

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_pingdatalk, container, false).run {


        tb_pingDaTalk.navigationIcon = resources.getDrawable(R.drawable.ic_baseline_dehaze_24, null)
        tb_pingDaTalk.title = "핑다톡"

        tb_pingDaTalk.setNavigationOnClickListener {
            dL_sideBoard.open()
        }

        val userDataBase = FirebaseFirestore.getInstance().collection("User")
        val user= mapOf("name" to "이름")

        //서버에 값 만들기
        btn_createData.setOnClickListener {
            userDataBase.document("0").set(user)
            Toast.makeText(requireContext(), "서버에 값을 생성했습니다.",Toast.LENGTH_SHORT).show()
        }

        //서버에서 값을 가져옴
        btn_getData.setOnClickListener {
            userDataBase.document("0").get().addOnSuccessListener {
                val name = it.get("name")
                Toast.makeText(requireContext(), "서버에서 가져온값은 "+name.toString(),Toast.LENGTH_SHORT).show()
            }
        }

        //서버에 있는 값을 수정
        btn_upload.setOnClickListener {
            userDataBase.document("0").update("name","이름 수정")
        }

        //서버에 있는 값을 삭제
        btn_deleteData.setOnClickListener {
            userDataBase.document("0").delete()
        }



        setBackButton(this)
        rootView
    }

    private fun setBackButton(view:View) {
        requireActivity().onBackPressedDispatcher.addCallback(this@PingDaTalkFragment) {
            if (view.dL_sideBoard.isOpen)
                view.dL_sideBoard.close()
            else
                requireActivity().finish()
        }
    }
}