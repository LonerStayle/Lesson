package com.example.firebasetr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetr.model.MuckProjectModel
import com.example.firebasetr.model.MuckUserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_notice_board.*

class ActivityNoticeBoard : AppCompatActivity() {
/**
 * 서버개발자가 없는 가짜개발자팀을 위한 서버 응용 방법 입니다 ㅠ
 *
 * 사용 순서 (1) 콜렉션 디비를 담을 객체를 만들어놓는다!
 */
    //파베 클라우드
    private val db = FirebaseFirestore.getInstance()
    //콜렉션 저장할 객체 1 :유저 데이터 디비 담기용
    private lateinit var db_muckUserData: CollectionReference
    //콜렉션 저장할 객체 2 :프로젝트 모델 담기용
    private lateinit var db_muckProjectModel: CollectionReference
    //아래에서 쓸꺼
    private lateinit var muckProjectModel: MuckProjectModel

    // 아래 두 객체는 무시하세요 //
    private val auth by lazy { FirebaseAuth.getInstance() }
    private var anonymousCheck: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_board)

        db_muckUserData = db.collection("MuckUserData")
        db_muckProjectModel = db.collection("MuckProjectModel")


        fireStoreTest()
        setUpData()
        setProfileData()
        setButtonMyDataSettingClickListener()
    }

    private fun fireStoreTest() {

        val userDataList = MuckUserData(0, "로너")

        /**
         * 사용 순서 (2) 프로젝트 모델 CRUD 사용할 준비 기본적으로 Project 데이터에 함께하는 유저데이터가 포함되야 해서
         * 아래 예제에서는 유저데이터 먼저 만들었습니다. 그후에 프로젝트 모델에 담았습니다
         */

        //유저 데이터 콜렉션에 MuckUserData안에 있는 userId로 도큐먼트 이름을 정하고 값을 저장.
        db_muckUserData.document(userDataList.userId!!.toString()).set(userDataList)

        //방금 저장한 유저 데이터를 파베에서 불러와서 프로젝트 모델 생성
        db_muckUserData.get().addOnSuccessListener { query ->
            for (document in query) {

                //공식 문서에 나온 document.data는 map으로 반환해주기 때문에 특정 클래스로 지정해줘야함.
                //그래서 Class 메소드만 나온 문서들 보고 찾음! document.toObject를 사용.
                //주의 할점은 변환할 원본 클래스에 값을 줘야만, 앱이 팅기지가 않음
                muckProjectModel = MuckProjectModel(
                    0,
                    "로너프로젝트",
                    memberList = listOf(document.toObject(MuckUserData::class.java) )
                )
            }
        }

        /**
         * 디비에 값넣기!!
         */
        button_fireStoreDataInsert.setOnClickListener {

            //도큐먼트명을 마찬가지로 프로젝트 id의 것으로 사용
            db_muckProjectModel.document(muckProjectModel.projectId.toString())
                .set(muckProjectModel)
                .addOnSuccessListener {
                    toast(
                        "컬렉션 안에\n" +
                                "문서가 생성되었습니다"
                    )
                }
                .addOnFailureListener { e ->
                    Log.d("errorCheck", "$e")
                }
        }


        //랜덤 도큐먼트 이름으로 추가하는 기능
        button_fireStoreDataRandomAdd.setOnClickListener {
            db_muckProjectModel
                .add(muckProjectModel)
                .addOnSuccessListener { documentReference ->
                    toast("생성된 아이템의 id 입니다. ${documentReference.id}")
                }.addOnFailureListener { e ->
                    Log.d("errorCheck", "$e")
                }
        }


        /**
         * 사용: 디비에서 값 가져오기
         */
        button_firebaseDataReading.setOnClickListener {

            db_muckProjectModel
                .get()
                .addOnSuccessListener {

                    for (document in it) {
                        toast("${document.id} => ${document.toObject(MuckProjectModel::class.java)}")
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("errorCheck", "$e")
                }
        }


        /**
         * 사용: 디비에서 도큐먼트 통째로 삭제하기
         */
        button_fireStoreDocumentDelete.setOnClickListener {

            //해당 도큐먼트 이름 검색해서 싹다 제거함
            db_muckProjectModel.document(muckProjectModel.projectId.toString())
                .delete()
                .addOnSuccessListener { toast("lonerUser컬렉션 안에\nmuckData 문서가 삭제되었습니다") }
                .addOnFailureListener { e ->
                    Log.d("errorCheck", "$e")

                }
        }


        /**
         * 디비에서 도큐먼트 안에 특정 데이터만 삭제하기
         */
        button_fireStoreFliedDelete.setOnClickListener {
            //테스트로 좋아요 갯수를 삭제해보겠습니다.
            //FieldValue.delete() 함수로 삭제할수 있습니다.
            val delete = hashMapOf<String, Any>(
                "likeCount" to FieldValue.delete()
            )

            db_muckProjectModel.document(muckProjectModel.projectId.toString())
                 .update(delete)
                .addOnCompleteListener {
                    toast("콜렉션 안 muckData\nlast 라는 키를 가진 데이터를 삭제했습니다.")
                }
                .addOnFailureListener { e ->
                    Log.d("errorCheck", "$e")

                }
        }

    }

    private fun setUpData() {
        anonymousCheck = intent.getBooleanExtra("anonymousCheck", false)
        if (anonymousCheck == true) {
            button_myDataSetting.text = "회원가입 하기"
            button_anonymousToLogin.visibility = View.VISIBLE
            button_anonymousFaceBookLogin.visibility = View.VISIBLE
            button_anonymousGoogleLogin.visibility = View.VISIBLE
        } else
            return
    }

    private fun setButtonMyDataSettingClickListener() {
        button_myDataSetting.setOnClickListener {
            if (anonymousCheck == true)
                startActivity(Intent(this, ActivityRegister::class.java))
            else
                startActivity(Intent(this, ActivityChangeUserData::class.java))
        }
    }


    private fun setProfileData() {

        auth.currentUser?.let {
            //Glide.with(this).load(it.photoUrl).centerCrop().into(imageView_userImage)
            textView_userName.text = it.displayName
            Log.d("opop", "${it.photoUrl}")
            imageView_userImage.setImageURI(it.photoUrl)
            textView_userEmail.text = it.email
        }
    }

}