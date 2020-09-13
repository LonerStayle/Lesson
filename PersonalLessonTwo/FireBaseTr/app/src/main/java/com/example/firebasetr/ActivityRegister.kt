package com.example.firebasetr



import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasetr.RequestCode.REQUEST_IMAGE_CODE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.android.synthetic.main.activity_notice_board.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.button_createId

class ActivityRegister : AppCompatActivity() {





    private var getImage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setButtonNewIdCreate()
        setImageProfileClickListener()


    }

    private fun setProgressBar(layoutVisible: Int, progressVisible: Int) {
        for (i in 0 until layout.childCount) {
            layout.getChildAt(i).visibility = layoutVisible
            progressBar_register.visibility = progressVisible
        }
    }



    private fun setButtonNewIdCreate() {
        button_createId.setOnClickListener {
            val email = editText_newEmail.text.toString()
            val passWord = editText_newPassWord.text.toString()
            val passWordCheck = editText_newPassWordCheck.text.toString()
            val name = editText_name.text.toString()

            if (email.isEmpty() || passWord.isEmpty() || passWordCheck.isEmpty() || name.isEmpty()
                || getImage == null
            ) {

                when {
                    passWord != passWordCheck ->
                        baseContext.toast("비밀번호와 비밀번호 재확인이 서로 다릅니다.")

                    email.isNotEmpty() && passWord.isNotEmpty() && passWordCheck.isNotEmpty() && name.isNotEmpty()
                            && getImage == null ->
                        baseContext.toast("프로필로 사용할 이미지를 선택해주세요")

                    else -> baseContext.toast("양식에 맞는 글을 작성해주세요")
                }
            } else {
                createEmailId()
            }
        }
    }

    private fun createEmailId() {
        button_createId.isEnabled = false
        setProgressBar(View.GONE, View.VISIBLE)
        val email = editText_newEmail.text.toString()
        val name = editText_name.text.toString()
        val passWord = editText_newPassWordCheck.text.toString()
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, passWord).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(Uri.parse(getImage))
                    .build()

                auth.currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        textView_userName.text = name
                        imageView_userImage.setImageURI(Uri.parse(getImage))
                        textView_userEmail.text = email

                        getImage = null
                        startActivity(Intent(this, ActivityNoticeBoard::class.java))
                        finish()
                    }
                }
            } else {
                baseContext.toast(
                    "이미 가입된 이메일이거나 \n " +
                            "이메일 형식과 비밀번호 최소 6자리 이상이어야 합니다."
                )

            }
            button_createId.isEnabled = true
            setProgressBar(View.VISIBLE, View.GONE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CODE && resultCode == Activity.RESULT_OK) {
            val imageData = data?.data
            imageView_profileImageSelect.setImageURI(imageData)
            getImage = imageData.toString()
        }

    }

    private fun setImageProfileClickListener() {
        imageView_profileImageSelect.setOnClickListener {

            setImagePermission(){
                startActivityForResult(Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                REQUEST_IMAGE_CODE)
            }
        }
    }



}