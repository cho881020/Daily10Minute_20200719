package kr.co.tjoeun.daily10minute_20200719

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_notification_list.*
import kr.co.tjoeun.daily10minute_20200719.adapters.NotificationAdapter
import kr.co.tjoeun.daily10minute_20200719.datas.Notification
import kr.co.tjoeun.daily10minute_20200719.utils.ServerUtil
import org.json.JSONObject

class NotificationListActivity : BaseActivity() {

    val mNotiList = ArrayList<Notification>()
    lateinit var mNotiAdapter : NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mNotiAdapter = NotificationAdapter(mContext, R.layout.notification_list_item, mNotiList)
        notificationListView.adapter = mNotiAdapter

        getNotificationListFromServer()
    }

    fun getNotificationListFromServer() {
        ServerUtil.getRequestNotificationList(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val notifications = data.getJSONArray("notifications")

                for (i in 0 until notifications.length()) {

//                    배열 내부의 JSONArrary 추출 => Notification 형태로 변환 => 알림목록에 추가
                    mNotiList.add(Notification.getNotificationFromJson(notifications.getJSONObject(i)))

                }

//                배열에 목록이 모두 추가되면 => 새로고침
                mNotiAdapter.notifyDataSetChanged()

            }

        })
    }

}