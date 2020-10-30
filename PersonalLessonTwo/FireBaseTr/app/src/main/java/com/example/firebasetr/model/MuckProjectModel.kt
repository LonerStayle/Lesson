package com.example.firebasetr.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize


//프로젝트 모델
@Parcelize
data class MuckProjectModel (

    //프로젝트 id
    val projectId:Int? = null,
//    프로젝트명
    val projectTitle:String? = null,
//    프로젝트 이미지
    val projectImage:String?= null,
//    프로젝트 설명
    val projectDescription:String?=null,
//    필요한 디자이너 수
    val needDesignerCount:Int?= null,
//    필요한 기획자 수
    val needPlannerCount:Int?=null,
//    필요한 개발자 수
    val needDeveloperCount:Int?=null,
//    팀으로 소속된 멤버들
    val memberList:List<MuckUserData>? = null ,
//    참가모집 현황
    val recruitmentStatus:String?=null,
//    좋아요 갯수
    val likeCount:Int?=null,
//    댓글 갯수
    val commentsCount:List<String>?= null,

    //리더 이름
    val leaderData:List<MuckUserData>? = null,

//    프로젝트 올린 날짜
    @ServerTimestamp
    val createAt: Timestamp? = null,

    @ServerTimestamp
    val lastUpdated: Timestamp? = null,

//   태그 리스트
    val tagList:List<String>?=null,

    ):Parcelable