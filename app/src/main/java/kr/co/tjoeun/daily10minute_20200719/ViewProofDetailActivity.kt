package kr.co.tjoeun.daily10minute_20200719

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_proof_detail.*
import kr.co.tjoeun.daily10minute_20200719.adapters.ReplyAdapter
import kr.co.tjoeun.daily10minute_20200719.datas.Proof
import kr.co.tjoeun.daily10minute_20200719.datas.Reply
import kr.co.tjoeun.daily10minute_20200719.utils.ServerUtil
import kr.co.tjoeun.daily10minute_20200719.utils.TimeUtil
import org.json.JSONObject

class ViewProofDetailActivity : BaseActivity() {

//    목록에서 넘겨주는 인증글의 id값
    var mProofId = 0

//    이 화면에서 표시해야할 데이터들이 담긴 인증 글
    lateinit var mProof : Proof

//    서버에서 내려주는 댓글들을 담을 목록
    val mReplyList = ArrayList<Reply>()

    lateinit var mReplyAdapter : ReplyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_proof_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        postReplyBtn.setOnClickListener {

            val inputContent = replyContentEdt.text.toString()

            if (inputContent.length < 5) {
                Toast.makeText(mContext, "댓글은 최소 5자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()

//                등록 거부 : 이벤트 처리 강제 종료
                return@setOnClickListener
            }

            ServerUtil.postRequestReplyToProof(mContext, mProofId, inputContent, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

//                    인증글 데이터를 다시 받아오자. => 댓글 목록을 새로 가져오는 효과가 있다.
                    getProofDataFromServer()

                }

            })
        }
        
    }

    override fun setValues() {

        mProofId = intent.getIntExtra("proof_id", 0)

        getProofDataFromServer()

        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        replyListView.adapter = mReplyAdapter
    }

    fun getProofDataFromServer() {
//        GET - /project_proof/id값   API 호출

        ServerUtil.getRequestProofDetail(mContext, mProofId, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val proof = data.getJSONObject("project")

                mProof = Proof.getProofFromJson(proof)

//                같이 담겨오는 댓글목록을 처리 => 기존에 담겨있던 댓글들을 전부 삭제하고 처리.
                mReplyList.clear()

                val replies = proof.getJSONArray("replies")

                for (i in 0 until replies.length()) {

//                    댓글 JSONObject 추출 => Reply 형태로 변환 => mReplyList에 추가
                    mReplyList.add(Reply.getReplyFromJson(replies.getJSONObject(i)))

                }

                runOnUiThread {
                    writerNickNameTxt.text = mProof.user.nickName
                    createAtTxt.text = TimeUtil.getTimeAgoStringFromCalendar(mProof.proofTime)
                    contentTxt.text = mProof.content

//                    서버 통신 과정이 어댑터 연결보다 늦게 완료될 수 있다.
                    mReplyAdapter.notifyDataSetChanged()

                }

            }

        })

    }

}