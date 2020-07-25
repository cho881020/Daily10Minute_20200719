package kr.co.tjoeun.daily10minute_20200719.datas

import org.json.JSONObject

class User {

    var id = 0
    var email = ""
    var nickName = ""
    var projectDays = 0

//    해당 사용자가 갖고 있는 프사들을 저장할 목록
    val profileImageList = ArrayList<ProfileImage>()

    companion object {

//        jsonobject를 적당히 넣어주면 => User로 변환해주는 기능

        fun getUserFromJson(json: JSONObject) : User {

            val u = User()

            u.id = json.getInt("id")
            u.email = json.getString("email")
            u.nickName = json.getString("nick_name")
            u.projectDays = json.getInt("project_days")

//            사용자가 갖고있는 프사 목록을 채우자

            val profile_images = json.getJSONArray("profile_images")
            for (i in 0 until profile_images.length()) {

//                프사 JSONObject 추출
                val profile_image = profile_images.getJSONObject(i)

//                JSONObject => ProfileImage 변환
                val profileImage = ProfileImage.getProfileImageFromJson(profile_image)

//                변환된 ProfileImage를 목록에 추가
                u.profileImageList.add(profileImage)

            }

            return u
        }


    }

}