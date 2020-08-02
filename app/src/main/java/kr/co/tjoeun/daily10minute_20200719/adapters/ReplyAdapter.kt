package kr.co.tjoeun.daily10minute_20200719.adapters

import android.content.Context
import android.graphics.Color
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
        val likeCountTxt = row.findViewById<TextView>(R.id.likeCountTxt)

        val data = mList[position]

        writerNickNameTxt.text = data.writer.nickName
        contentTxt.text = data.content

//        좋아요 갯수 처리 -> 0개면 숨김, 그 이상이면 보여주고 갯수 반영
        if (data.likeCount == 0) {
            likeCountTxt.visibility = View.GONE
        }
        else {
            likeCountTxt.visibility = View.VISIBLE
            likeCountTxt.text = "좋아요 ${data.likeCount}개"
        }

//        내 좋아요 여부에 따른 처리
//        myLike가 true : 빨간 테두리 + 빨간 글씨 (naverRed) + 좋아요 취소
//        반대 : 회색 테두리 + 검정 글씨 + 좋아요

        if (data.myLike) {
//            테두리 처리 => xml로 그려둔 drawable 활용
            likeBtn.setBackgroundResource(R.drawable.red_border_box)

//            빨간 글씨 => res > colors.xml 에 있는 항목 (naverRed) 가져오기
            likeBtn.setTextColor(mContext.resources.getColor(R.color.naverRed))

//            좋아요 취소로 문구 변경
            likeBtn.text = "좋아요 취소"

        }
        else {
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
//            검정 글씨 => 기본색에서 BLACK 지원 (기본 색 사용법 예시)
            likeBtn.setTextColor(Color.BLACK)
            likeBtn.text = "좋아요"
        }

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