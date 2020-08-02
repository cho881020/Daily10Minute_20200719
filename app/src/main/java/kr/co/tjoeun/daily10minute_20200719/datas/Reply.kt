package kr.co.tjoeun.daily10minute_20200719.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Reply {

    var id = 0
    var content = ""
    lateinit var writer : User
    var likeCount = 0
    var myLike = false

//    댓글이 작성된 시간
    val createdAt = Calendar.getInstance()

    companion object {

        private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        fun getReplyFromJson(json : JSONObject) : Reply {
            val r = Reply()

            r.id = json.getInt("id")
            r.content = json.getString("content")
            r.likeCount = json.getInt("like_count")
            r.myLike = json.getBoolean("my_like")

//            user JSONObject 추출 => User 클래스로 변환 => r.writer에 대입
            r.writer = User.getUserFromJson(json.getJSONObject("user"))

//            created_at String 추출 => 날짜양식 파싱 => r.createdAt의 시간정보로 활용
            val createdAtStr = json.getString("created_at")

            r.createdAt.time = sdf.parse(createdAtStr)


            return r
        }

    }

}