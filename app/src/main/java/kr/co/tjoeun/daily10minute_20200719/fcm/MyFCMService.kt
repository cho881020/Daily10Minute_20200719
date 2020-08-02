package kr.co.tjoeun.daily10minute_20200719.fcm

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFCMService : FirebaseMessagingService() {

//    앱이 화면에 나와있을때 푸시알림을 받으면 실행되는 코드
//    앱이 꺼져있는 상태면 => 자동으로 푸시알림이 뜬다.

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)

        Log.d("푸시알림수신", p0?.notification?.title)

    }

}
