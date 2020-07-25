package kr.co.tjoeun.daily10minute_20200719

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_project_detail.*
import kr.co.tjoeun.daily10minute_20200719.datas.Project
import kr.co.tjoeun.daily10minute_20200719.utils.ServerUtil
import org.json.JSONObject

class ViewProjectDetailActivity : BaseActivity() {

//    이 화면에서 보여줄 프로젝트의 id값
    var mProjectId = 0

//    이 화면에서 보여줄 프로젝트 자체 변수
    lateinit var mProject : Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        joinProjectBtn.setOnClickListener {

//            정말 참여할건지? 물어보자.

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("프로젝트 참여 신청")
            alert.setMessage("정말 프로젝트에 참여하시겠습니까?")
            alert.setPositiveButton("예", DialogInterface.OnClickListener { dialog, which ->

//                실제로 서버에 참여 신청
                ServerUtil.postRequestProjectJoin(mContext, mProjectId, object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {

//                        신청에 성공했을때만 데이터 갱신

                        val code = json.getInt("code")

                        if (code == 200) {
                            val data = json.getJSONObject("data")
                            val projectObj = data.getJSONObject("project")

//                        갱신된 프로젝트 정보를 새로 대입
                            mProject = Project.getProjectFromJson(projectObj)

//                        별도 기능으로 만들어진 Ui 데이터 세팅 기능 실행
                            setProjectDataToUI()
                        }
                        else {
                            val message = json.getString("message")

                            runOnUiThread {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }

                        }



                    }

                })

            })
            alert.setNegativeButton("아니오", null)
            alert.show()

        }

        viewOngoingUsersBtn.setOnClickListener {

//            프로젝트별 참여 명부 화면으로 이동 => 명단 확인

            val myIntent = Intent(mContext, ViewOngoingUsersActivity::class.java)

            myIntent.putExtra("projectId", mProjectId)

            startActivity(myIntent)


        }

    }

    override fun setValues() {

        mProjectId = intent.getIntExtra("projectId", 0)

        getProjectDetailFromServer()

    }

//    프로젝트 상세 정보를 불러오는 기능

    fun getProjectDetailFromServer() {

        ServerUtil.getRequestProjectDetail(mContext, mProjectId, object : ServerUtil.JsonResponseHandler {

            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")

                val projectObj = data.getJSONObject("project")

//                projectObj로 Project 형태로 변환 => 멤버변수로 저장
                mProject = Project.getProjectFromJson(projectObj)

//                데이터 화면 반영 기능 실행
                setProjectDataToUI()

            }

        })

    }

//    mProject에 세팅된 데이터를 화면에 반영하는 기능 별도 분리

    fun setProjectDataToUI() {

//                프로젝트 정보를 화면에 반영

        runOnUiThread {

            Glide.with(mContext).load(mProject.imageUrl).into(projectImg)

            projectTitleTxt.text = mProject.title
            projectDescriptionTxt.text = mProject.description

            proofMethodTxt.text = mProject.proofMethod
            challengerCountTxt.text = "${mProject.ongoingUserCount}명 도전 진행중"

        }
    }

}