package kr.co.tjoeun.daily10minute_20200719.datas

import org.json.JSONObject

class Project {

    var id = 0
    var title = ""
    var imageUrl = ""
    var description = ""

    var proofMethod = ""
    var ongoingUserCount = 0

//    내 진행 상태를 표시하는 변수 : null => 참가 해본적이 없는 상태
    var myLastStatus : String? = null

    companion object {

//        적절한 JSONObject를 재료로 받아서 => Project 객체로 뽑아주는 기능

        fun getProjectFromJson(json: JSONObject) : Project {

            val p = Project()

//            json에 들어있는 데이터들을 이용해서 => p의 데이터로 대입
            p.id = json.getInt("id")
            p.title = json.getString("title")
            p.imageUrl = json.getString("img_url")
            p.description = json.getString("description")

            p.proofMethod = json.getString("proof_method")
            p.ongoingUserCount = json.getInt("ongoing_users_count")

//            내 진행상태는 null이 아닐때만 파싱하자.
            if (!json.isNull("my_last_status")) {
//                파싱 진행
                p.myLastStatus = json.getString("my_last_status")
            }

//            완성된 p를 리턴
            return p

        }

    }

}