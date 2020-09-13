package com.example.firebasetr

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.android.synthetic.main.activity_notice_board.*
import kotlinx.android.synthetic.main.dialog_change.*
import kotlinx.android.synthetic.main.dialog_re_auth.*

class ActivityNoticeBoard : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_board)

        setProfileData()

        button_myDataSetting.setOnClickListener {
            startActivity(Intent(this,ActivityChangeUserData::class.java))
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