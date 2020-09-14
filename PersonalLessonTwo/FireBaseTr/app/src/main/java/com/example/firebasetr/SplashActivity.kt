package com.example.firebasetr

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class SplashActivity : AppCompatActivity() {
    private val handler = Handler(Looper.myLooper()!!)
private val TAG = "keyHash"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
//        ST0FQ6GHZF3HQCfm93DR1ZJFlv4=
//        printHashKey(this)

        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000L)


    }

//
//    @SuppressLint("PackageManagerGetSignatures")
//    fun printHashKey(pContext: Context) {
//        try {
//            val info: PackageInfo = pContext.packageManager
//                .getPackageInfo(pContext.packageName, PackageManager.GET_SIGNATURES)
//            for (signature in info.signatures) {
//                val md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                val hashKey = String(Base64.encode(md.digest(), 0))
//                Log.i(TAG, "printHashKey() Hash Key: $hashKey")
//            }
//        } catch (e: NoSuchAlgorithmException) {
//            Log.e(TAG, "printHashKey()", e)
//        } catch (e: Exception) {
//            Log.e(TAG, "printHashKey()", e)
//        }
//    }


    override fun onStop() {
        super.onStop()
        handler.removeCallbacksAndMessages(null)
    }



}