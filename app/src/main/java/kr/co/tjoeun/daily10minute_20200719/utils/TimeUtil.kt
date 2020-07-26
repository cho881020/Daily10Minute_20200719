package kr.co.tjoeun.daily10minute_20200719.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtil {

    companion object {

//        시간을 재료로 넣으면 시차를 보정해서 => 상황에 맞는 문구로 알려주는 기능

        fun getTimeAgoStringFromCalendar(cal : Calendar) : String {

//            cal 을 => 핸드폰에 설정된 시간에 맞는 시차 계산
//            cal 내부에는, 어느 시간대 (서울, 영국, 뉴욕) 인지 정보가 저장되어 있다.
//            시간 값만 서버에서 준 그대로 받아적어둔 상태.

//            핸드폰의 시간대와 => 표준시간대의 시차를 구해서
//            cal 의 시간을 보정해주자.

            val myPhoneTimeZone = cal.timeZone // 한국 폰: 한국 시간대, 영국 : 영국 시간대

//            표준 시간대 (UTC / GMT +00:00) 과의 몇시간 차이가 나는지 계산
//            rawOffset : 시차를 밀리초 단위까지 기록. => 시간단위로 변환
            val timeOffset = myPhoneTimeZone.rawOffset / 1000 / 60 / 60

//            재료로 들어온 cal 에게 시간값을 (시차 만큼) 증가시키자.
            cal.add(Calendar.HOUR, timeOffset)


//            양식을 가공해서 String으로 리턴

            val sdf = SimpleDateFormat("yyyy년 M월 d일 a h시 m분")

            return sdf.format(cal.time)

        }

    }
}