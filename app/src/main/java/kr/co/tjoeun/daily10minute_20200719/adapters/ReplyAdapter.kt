package kr.co.tjoeun.daily10minute_20200719.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import kr.co.tjoeun.daily10minute_20200719.R
import kr.co.tjoeun.daily10minute_20200719.datas.Project
import kr.co.tjoeun.daily10minute_20200719.datas.Reply
import kr.co.tjoeun.daily10minute_20200719.utils.ServerUtil
import org.json.JSONObject

class ReplyAdapter(
    val mContext:Context,
    resId:Int,
    val mList:List<Reply>) : ArrayAdapter<Reply>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = inf.inflate(R.layout.reply_list_item, null)
        }

        val row = tempRow!!

        val writerNickNameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)

        val data = mList[position]

        writerNickNameTxt.text = data.writer.nickName
        contentTxt.text = data.content

        likeBtn.setOnClickListener {

            ServerUtil.postRequestLikeReply(mContext, data.id, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    서버가 주는 메세지를 토스트로 출력

                    val message = json.getString("message")

                    val uiHandler = Handler(Looper.getMainLooper())

                    uiHandler.post {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

//                        어댑터 새로고침
                        notifyDataSetChanged()
                    }

                }

            })

        }

        return row
    }


}