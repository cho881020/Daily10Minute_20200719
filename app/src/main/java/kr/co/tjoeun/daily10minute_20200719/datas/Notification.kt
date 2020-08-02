package kr.co.tjoeun.daily10minute_20200719.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Notification {

    var id = 0
    var title = ""

    val createdAt = Calendar.getInstance()
    lateinit var actUser : User

    companion object {

        private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        fun getNotificationFromJson(json: JSONObject) : Notification {
            val n = Notification()

            n.id = json.getInt("id")
            n.title = json.getString("title")

//            언제 생긴 알림인지
            n.createdAt.time = sdf.parse(json.getString("created_at"))

//            어떤 사람이 발생시킨 알림인지
//            act_user JSONObject => User클래스 변환 => n.actUser에 대입
            n.actUser = User.getUserFromJson(json.getJSONObject("act_user"))


            return n
        }

    }

}