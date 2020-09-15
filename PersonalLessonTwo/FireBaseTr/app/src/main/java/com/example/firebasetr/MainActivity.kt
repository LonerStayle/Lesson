package com.example.firebasetr


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val auth by lazy { FirebaseAuth.getInstance() }
    private val faceBookCallbackManager = CallbackManager.Factory.create()
    private val faceBookLoginCallback = FaceBookLoginCallback(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setButtonGotoRegister()
        setButtonLoginClickListener()
        setButtonAnonymousClickListener()
        setButtonFaceBookClickListener()
    }




    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            val intent = Intent(this, ActivityNoticeBoard::class.java)
            startActivity(intent)
            finish()
        }
    }


    private fun setButtonGotoRegister() {
        button_goToRegister.setOnClickListener {
            val intent = Intent(this, ActivityRegister::class.java)
            startActivity(intent)
        }
    }


    private fun setButtonLoginClickListener() {

        button_login.setOnClickListener {
            button_login.isEnabled = false


            if (editText_passWord.text.isEmpty() || editText_id.text.isEmpty()) {

                when {
                    editText_id.text.isEmpty() && editText_passWord.text.isNotEmpty() ->
                        baseContext.toast("이메일 적어주세요")

                    editText_passWord.text.isEmpty() && editText_id.text.isNotEmpty() ->
                        baseContext.toast("비밀번호를 적어주세요")

                    editText_passWord.text.isEmpty() && editText_id.text.isEmpty() ->
                        baseContext.toast("이메일과 비밀번호를 적어주세요")
                }
                return@setOnClickListener
            }
            login()
        }
    }


    private fun login() {
        setProgressBar(View.GONE, View.VISIBLE)
        val email = editText_id.text.toString()
        val passWord = editText_passWord.text.toString()

        auth.signInWithEmailAndPassword(email, passWord).addOnCompleteListener {
            if (it.isSuccessful) {
                setImagePermission() {
                    val intent = Intent(this, ActivityNoticeBoard::class.java)

                    intent.putExtra("anonymousCheck", false)
                    startActivity(intent)

                    finish()
                    setProgressBar(View.VISIBLE, View.GONE)
                    button_login.isEnabled = true
                }

            } else
                baseContext.toast("이메일 혹은 패스워드를 다시 확인해보세요")
            setProgressBar(View.VISIBLE, View.GONE)
            button_login.isEnabled = true

        }

    }

    private fun setButtonAnonymousClickListener() {
        button_anonymousStart.setOnClickListener {
            auth.signInAnonymously().addOnCompleteListener {
                if (it.isSuccessful) {
                    setImagePermission {
                        val intent = Intent(this, ActivityNoticeBoard::class.java)
                        intent.putExtra("anonymousCheck", true)
                        startActivity(intent)
                        finish()
                        setProgressBar(View.VISIBLE, View.GONE)
                        button_login.isEnabled = true
                    }
                } else
                    toast("익명 로그인에 실패했습니다.")
            }
        }
    }

    private fun setButtonFaceBookClickListener() {
        button_faceBookLogin.setReadPermissions(arrayListOf("public_profile", "email"))
        button_faceBookLogin.registerCallback(faceBookCallbackManager, faceBookLoginCallback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        faceBookCallbackManager.onActivityResult(requestCode, requestCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setProgressBar(viewVisible: Int, progressVisible: Int) {
        editText_id.visibility = viewVisible
        editText_passWord.visibility = viewVisible
        button_login.visibility = viewVisible
        button_goToRegister.visibility = viewVisible
        button_anonymousStart.visibility = viewVisible
        progressBar_main.visibility = progressVisible
    }

}