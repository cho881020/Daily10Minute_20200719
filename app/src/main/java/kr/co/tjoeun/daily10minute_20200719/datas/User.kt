package kr.co.tjoeun.daily10minute_20200719.datas

import org.json.JSONObject

class User {

    var id = 0
    var email = ""
    var nickName = ""
    var projectDays = 0

    companion object {

//        jsonobject를 적당히 넣어주면 => User로 변환해주는 기능

        fun getUserFromJson(json: JSONObject) : User {

            val u = User()

            u.id = json.getInt("id")
            u.email = json.getString("email")
            u.nickName = json.getString("nick_name")
            u.projectDays = json.getInt("project_days")

            return u
        }


    }

}