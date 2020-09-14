package com.example.firebasetr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_notice_board.*

class ActivityNoticeBoard : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    private var anonymousCheck: Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_board)

        setUpData()
        setProfileData()
        setButtonMyDataSettingClickListener()

    }

    private fun setUpData() {
        anonymousCheck = intent.getBooleanExtra("anonymousCheck", false)
        if (anonymousCheck == true){
            button_myDataSetting.text = "회원가입 하기"
            button_anonymousToLogin.visibility = View.VISIBLE
            button_anonymousFaceBookLogin.visibility = View.VISIBLE
            button_anonymousGoogleLogin.visibility = View.VISIBLE
        }
        else
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