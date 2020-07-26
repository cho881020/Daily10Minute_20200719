package kr.co.tjoeun.daily10minute_20200719

import android.app.DatePickerDialog
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_view_project_proof_list.*
import kr.co.tjoeun.daily10minute_20200719.adapters.ProofAdapter
import kr.co.tjoeun.daily10minute_20200719.datas.Project
import kr.co.tjoeun.daily10minute_20200719.datas.Proof
import kr.co.tjoeun.daily10minute_20200719.utils.ServerUtil
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ViewProjectProofListActivity : BaseActivity() {

//    몇번 프로젝트에 대한 인증 목록인지
    var mProjectId = 0

//    보고 있는 프로젝트가 어떤 프로젝트인지
    lateinit var mProject : Project

//    인증 게시글들이 담길 목록
    val mProofList = ArrayList<Proof>()

//    인증글 뿌려주는 어댑터
    lateinit var mProofAdapter : ProofAdapter

//    인증을 확인할 날짜를 저장해주는 변수
//    proofDate => 기본값이 현재 시간으로 저장됨.
    val mProofDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project_proof_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        날짜 변경 버튼을 누르면 proofDate에 저장된 날짜를 변경
        changeProofDateBtn.setOnClickListener {

//            날짜 선택하는 팝업창을 띄우자.
            val datePickerDialog = DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

//                인증 날짜를 선택된 날짜로 변경
                mProofDate.set(Calendar.YEAR, year)
                mProofDate.set(Calendar.MONTH, month)
                mProofDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)


//                변경된 인증 날짜를 화면에 반영
                val sdf = SimpleDateFormat("yyyy년 M월 d일")
                proofDateTxt.text = sdf.format(mProofDate.time)

//                변경된 날짜의 인증 내역 가져오기
                getProofListFromServer()



            }, mProofDate.get(Calendar.YEAR), mProofDate.get(Calendar.MONTH), mProofDate.get(Calendar.DAY_OF_MONTH))

            datePickerDialog.show()

        }

    }

    override fun setValues() {

//        이전 화면에서 넘겨준 프로젝트 id값을 멤버변수에 저장
        mProjectId = intent.getIntExtra("projectId", 0)


//        proofDate에 담긴 일시 (Calendar) 를 => 2020년 6월 9일 형태로 문구 (String) 출력.
//        SimpleDateFormat의 기능중 format 사용

        val sdf = SimpleDateFormat("yyyy년 M월 d일")

        val proofDateStr = sdf.format(mProofDate.time)

        proofDateTxt.text = proofDateStr


//        오늘 날짜의 인증 내역 가져오기
        getProofListFromServer()


//        어댑터와 / 리스트뷰 연결
        mProofAdapter = ProofAdapter(mContext, R.layout.proof_list_item, mProofList)
        proofListView.adapter = mProofAdapter

    }

//    서버에서 이 프로젝트의 날짜별 인증 내역을 가져오는 기능

    fun getProofListFromServer() {

//        선택해둔 날짜를 => 2020-06-08 양식의 String으로 가공
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateStr = sdf.format(mProofDate.time)

        ServerUtil.getRequestProjectDetailWithProofList(mContext, mProjectId, dateStr, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

//                날짜가 바뀌어서 인증글 목록을 새로 받아오면,
//                기존에 보여주던 인증글들은 모두 날려주고, 다시 불러오자.

                mProofList.clear()

                val data = json.getJSONObject("data")

                val projectObj = data.getJSONObject("project")

//                이 화면에서 보는 프로젝트 정보 대입
                mProject = Project.getProjectFromJson(projectObj)

//                인증글 목록을 파싱해서 => 멤버 목록에 반영

                val proofs = projectObj.getJSONArray("proofs")

                for (i in 0 until proofs.length()) {
                    val proofObj = proofs.getJSONObject(i)
//                    JSONObject => Proof로 변환 (우리가 직접 만든 클래스 고유 기능 사용)
                    val proof = Proof.getProofFromJson(proofObj)
//                    뽑힌 Proof 타입의 변수를 목록에 추가
                    mProofList.add(proof)
                }

//                프로젝트 제목 등 UI 반영 작업
                runOnUiThread {

                    projectTitleTxt.text = mProject.title

//                  리스트뷰의 내용 반영 => UI에 영향을 주는 행위
                    mProofAdapter.notifyDataSetChanged()

                }

            }

        })

    }

}