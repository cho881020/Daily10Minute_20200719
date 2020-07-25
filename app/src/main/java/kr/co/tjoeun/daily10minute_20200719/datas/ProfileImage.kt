package kr.co.tjoeun.daily10minute_20200719.datas

import org.json.JSONObject

class ProfileImage {

    var id = 0
    var imageUrl = ""

    companion object {

//        프사 정보 파싱 기능

        fun getProfileImageFromJson(json:JSONObject) : ProfileImage {
            val pi = ProfileImage()

            pi.id = json.getInt("id")
            pi.imageUrl = json.getString("img_url")

            return pi
        }

    }

}