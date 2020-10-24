package com.example.firebasetr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_notice_board.*

class ActivityNoticeBoard : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private val auth by lazy { FirebaseAuth.getInstance() }
    private var anonymousCheck: Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_board)



        fireStoreTest()

        setUpData()
        setProfileData()
        setButtonMyDataSettingClickListener()
    }

    private fun fireStoreTest() {

        //data add 항상 키 쌍 값으로 움직임.
        //랜덤 문서명으로 추가하기
        button_fireStoreDataRandomAdd.setOnClickListener {
            val user = hashMapOf(
                "first" to "Ada",
                "last" to "Lovelace",
                "born" to 1815
            )
            db.collection("lonerUsers")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    toast("생성된 아이템의 id 입니다. ${documentReference.id}")
                }.addOnFailureListener { e ->
                    Log.d("errorCheck", "$e")
                }
        }

        //문서명을 직접 지정하여 데이터 추가
        button_fireStoreDataInsert.setOnClickListener {
            val user = hashMapOf(
                "first" to "Alan",
                "middle" to "Mathison",
                "last" to "Turing",
                "born" to 1912
            )

            db.collection("lonerUsers").document("user")
                .set(user)
                .addOnSuccessListener {
                    toast("lonerUser컬렉션 안에\n" +
                            "user 문서가 생성되었습니다")
                }
                .addOnFailureListener { e ->
                    Log.d("errorCheck", "$e")
                }
        }

        //데이터 가져오기 아래는 콜렉션에 있는 모든 문서 데이터를 다 끌고옴
        button_firebaseDataReading.setOnClickListener {
            db.collection("lonerUsers")
                .get()
                .addOnSuccessListener {
                    //모든 데이터 다가져오기
                    for (document in it) {
                        toast("${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("errorCheck", "$e")
                }
        }

        //특정 문서를 찾아서 삭제
        button_fireStoreDocumentDelete.setOnClickListener {
            db.collection("lonerUsers").document("user")
                .delete()
                .addOnSuccessListener { toast("lonerUser컬렉션 안에\nuser 문서가 삭제되었습니다")}
                .addOnFailureListener { e ->
                    Log.d("errorCheck", "$e")
                }
        }


        //특정 문서안에 데이터 삭제
        button_fireStoreFliedDelete.setOnClickListener {
            val docRef = db.collection("lonerUsers").document("user")
            // 컬렉션을 생략한 경로 찾기 법
//            val allDocPath = db.document("lonerUsers/user")
            val updates = hashMapOf<String, Any>(
                "last" to FieldValue.delete()
            )

            docRef.update(updates)
                .addOnCompleteListener {
                    toast("lonerUsers콜렉션 안 user문서의\nlast 라는 키를 가진 데이터를 삭제했습니다.")
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