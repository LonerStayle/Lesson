package com.example.firebasetr

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.gun0912.tedpermission.TedPermissionActivity.startActivity
import kotlinx.android.synthetic.main.activity_change_userdata.*
import kotlinx.android.synthetic.main.activity_notice_board.*
import kotlinx.android.synthetic.main.dialog_change.*
import kotlinx.android.synthetic.main.dialog_re_auth.*

class ActivityChangeUserData : AppCompatActivity() {
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val dialogChange by lazy { Dialog(this, R.style.Theme_AppCompat_DayNight_Dialog) }
    private val dialogReAuth by lazy { Dialog(this, R.style.Theme_AppCompat_DayNight_Dialog) }
    private val user by lazy { auth.currentUser }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_userdata)

        setButtonPasswordCheck()
        setButtonEmailChangeClcikListener()
        setButtonLogOutClickListener()
        setButtonPasswordChangeClickListener()
        setButtonUserDataDeleteClickListener()

    }

    private fun setButtonPasswordCheck() {
        button_passwordCheck.setOnClickListener {
            val passwordCheck =  editText_passwordCheck.text.toString()
            val credential = EmailAuthProvider
                .getCredential(user!!.email!!, passwordCheck)

            user?.reauthenticate(credential)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        toast("인증 되었습니다.")
                        for (i in 0..3) {
                            layout_changeUserData.getChildAt(i).visibility = View.VISIBLE
                        }
                        layout_reAuth.visibility = View.GONE
                    } else
                        toast("비밀번호를 다시 확인해주세요")
                }
        }

    }


    private fun setButtonUserDataDeleteClickListener() {
        button_userDataDelete.setOnClickListener {
            dialogReAuth.setContentView(R.layout.dialog_re_auth)
            dialogReAuth.show()
            dialogReAuth.button_reAuth.setOnClickListener {
                setUserDataDelete()
            }

        }
    }

    private fun setUserDataDelete() {
        val newPassword = dialogReAuth.editText_reAuthToPassword.text.toString()
        val credential = EmailAuthProvider
            .getCredential(user!!.email!!, newPassword)

        user?.reauthenticate(credential)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setTitle("계정삭제")
                    dialog.setMessage("한번 삭제하면 다시 복구 할 수 없습니다.\n그래도 삭제하시겠습니까?")
                    dialog.setPositiveButton("네 삭제하겠습니다.") { dialogInterface: DialogInterface, _: Int ->
                        auth.currentUser?.delete()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    toast("계정이 삭제되었습니다.")
                                    startActivity(Intent(this, MainActivity::class.java))
                                }else {
                                    toast("현재 상황에서 삭제할 수 없습니다.")
                                    Log.d("firebaseError", "계정삭제 ${task.exception}")
                                }
                            }
                        dialogInterface.dismiss()
                    }
                    dialog.setNegativeButton("아니요") { dialogInterface: DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }

                    dialog.create()
                    dialog.show()
                    dialogReAuth.dismiss()
                }else
                    toast("비밀번호를 다시 확인해주세요")
            }
    }

    private fun setButtonPasswordChangeClickListener() {
        button_passwordChange.setOnClickListener {
            dialogChange.setContentView(R.layout.dialog_change)
            dialogChange.show()
            dialogChange.button_changeValue.setOnClickListener {
                val changeValue = dialogChange.editText_changeValue.text.toString()
                user?.updatePassword(changeValue)
                    ?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            toast("비밀번호가 변경되었습니다.")
                            dialogReAuth.dismiss()
                        } else
                            toast("개인정보 재인증을 하셔야 \n개인정보 변경이 가능합니다.")

                    }
            }
        }
    }


    private fun setButtonEmailChangeClcikListener() {
        button_emailChange.setOnClickListener {


            dialogChange.setContentView(R.layout.dialog_change)
            dialogChange.show()
            dialogChange.button_changeValue.setOnClickListener {
                val changeValue = dialogChange.editText_changeValue.text.toString()
                user?.updateEmail(changeValue)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            textView_userEmail.text = changeValue
                            toast("이메일이 변경되었습니다.")
                            dialogChange.dismiss()
                        } else {
                            toast("개인정보 재인증을 하셔야 \n개인정보 변경이 가능합니다.")

                        }
                    }
            }
        }

    }

    private fun setButtonLogOutClickListener() {
        button_logOut.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}