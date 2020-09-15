package com.example.firebasetr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.facebook.AccessToken
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult


open class FaceBookLoginCallback(var context: Context) : FacebookCallback<LoginResult> {

    override fun onSuccess(result: LoginResult?) {
        requestMe(result?.accessToken)
        context.startActivity(Intent(context,ActivityNoticeBoard::class.java))
    }

    override fun onCancel() {

    }

    override fun onError(error: FacebookException?) {

    }

    // 사용자 정보 요청
    private fun requestMe(token: AccessToken?) {
        val graphRequest = GraphRequest.newMeRequest(token) {
                `object`, _ -> Log.e("result", `object`.toString()) }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,email")
        graphRequest.parameters = parameters
        graphRequest.executeAsync()

    }
}