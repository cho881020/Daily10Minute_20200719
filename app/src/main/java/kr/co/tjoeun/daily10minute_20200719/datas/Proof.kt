package kr.co.tjoeun.daily10minute_20200719.datas

import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class Proof {

    var id = 0
    var content = ""
    val proofTime = Calendar.getInstance() // 인증 일시를 분석해서 세팅 예정

    val imageUrlList = ArrayList<String>() // 이미지의 주소만 따서 목록으로 저장

    lateinit var user : User // 인증을 올린 사람에 대한 정보


    companion object {

//        적당한 json을 넣으면 => Proof 로 변환해주는 기능

        fun getProofFromJson(json: JSONObject) : Proof {
            val p = Proof()

//            재료로 들어오는 json 을 가지고 => p의 멤버변수들에 내용을 채워주자
            p.id = json.getInt("id")
            p.content = json.getString("content")

            return p
        }


    }


}