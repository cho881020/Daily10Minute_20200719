package kr.co.tjoeun.daily10minute_20200719.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kr.co.tjoeun.daily10minute_20200719.R
import kr.co.tjoeun.daily10minute_20200719.datas.Notification
import kr.co.tjoeun.daily10minute_20200719.datas.Project
import kr.co.tjoeun.daily10minute_20200719.utils.TimeUtil

class NotificationAdapter(
    val mContext:Context,
    resId:Int,
    val mList:List<Notification>) : ArrayAdapter<Notification>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = inf.inflate(R.layout.notification_list_item, null)
        }

        val row = tempRow!!

        val userProfileImg = row.findViewById<ImageView>(R.id.userProfileImg)
        val notiTitleTxt = row.findViewById<TextView>(R.id.notiTitleTxt)
        val notiTimeTxt = row.findViewById<TextView>(R.id.notiTimeTxt)

        val data = mList[position]

        notiTitleTxt.text = data.title
        notiTimeTxt.text = TimeUtil.getTimeAgoStringFromCalendar(data.createdAt)

        Glide.with(mContext).load(data.actUser.profileImageList[0].imageUrl).into(userProfileImg)

        return row
    }


}