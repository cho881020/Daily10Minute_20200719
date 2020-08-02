package kr.co.tjoeun.daily10minute_20200719

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.daily10minute_20200719.adapters.ProjectAdapter
import kr.co.tjoeun.daily10minute_20200719.datas.Project
import kr.co.tjoeun.daily10minute_20200719.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mProjectList = ArrayList<Project>()

    lateinit var mProjectAdapter : ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        각 프로젝트를 눌렀을때 상세화면으로 이동
        projectListView.setOnItemClickListener { parent, view, position, id ->

            val clickedProject = mProjectList[position]

            val myIntent = Intent(mContext, ViewProjectDetailActivity::class.java)

//            몇번 프로젝트가 눌렸는지 프로젝트의 id값만 전달
            myIntent.putExtra("projectId", clickedProject.id)

            startActivity(myIntent)

        }

    }

    override fun setValues() {

//        서버에서 받아오는 기능 실행
        getProjectListFromServer()

//        서버에서 받아오고 난 후에 어댑터 연결
        mProjectAdapter = ProjectAdapter(mContext, R.layout.project_list_item, mProjectList)
        projectListView.adapter = mProjectAdapter

    }

//    서버에서 프로젝트 목록이 어떤게 있는지 요청해서 받아주는 함수

    fun getProjectListFromServer() {

        ServerUtil.getRequestProjectList(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val projects = data.getJSONArray("projects")

//                projects JSONArray 내부의 데이터들을 추출
//                반복문 i 로 돌면서 하나하나 가져오자.

                for (i in 0 until projects.length()) {

//                    i번째 JSONObject를 추출하자
                    val projectObj = projects.getJSONObject(i)

//                    프로젝트 정보 JSONObject => Project 형태의 인스턴스로 변환 => 목록에 담아야함.

//                    JSON => Project로 변환

                    val project = Project.getProjectFromJson(projectObj)

//                    프로젝트 목록 변수에 추가
                    mProjectList.add(project)

                }

//                목록이 추가되는 시점이 => 어댑터 연결 이후 일 수도 있다.
//                어댑터가 연결되고 나서 => 내용이 추가되는 것일 수도 있다.
//                새로고침 시켜줄 필요가 있다.

                runOnUiThread {
                    mProjectAdapter.notifyDataSetChanged()
                }



            }

        })

    }

}