package com.example.firebasetr.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//유저 모델
@Parcelize
data class MuckUserData(
    //유저 아이디
    val userId: Int?=null,
//    유저 네임
    val nickname: String?=null,
//    유저 직군
    val jobPosition: String?=null,
//    유저 이미지
    val profileImage: String?=null,
// 유저 프로필 소개
    val profileIntroduction: String?=null,
//유저가 좋아요한 프로젝트들
    val myFavoriteProjectList: List<Int>?=null,
// 유저의 랭킹 점수
    val userScore:Int? =null
):Parcelable
